package com.example.authservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authservice.Exceptions.TokenNotValidException;
import com.example.authservice.Exceptions.UserAlreadyExist;
import com.example.authservice.Exceptions.UserNotFound;
import com.example.authservice.Exceptions.TokenExpiredException;
import com.example.authservice.dtos.UserDto;
import com.example.authservice.models.Session;
import com.example.authservice.models.SessionStatus;
import com.example.authservice.models.User;
import com.example.authservice.repositories.SessionRepository;
import com.example.authservice.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;




    public AuthService(UserRepository userRepository,SessionRepository sessionRepository)
    {
        this.userRepository=userRepository;
        this.sessionRepository=sessionRepository;
        bCryptPasswordEncoder=new BCryptPasswordEncoder();

    }


    public UserDto signUp(String email,String password) throws UserAlreadyExist {
        Optional<User> userOptional=userRepository.findUserByEmail(email);

        if(!userOptional.isEmpty())
        {
            throw new UserAlreadyExist("User Already Exist with this email");
        }

        User user=new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User user1=userRepository.save(user);
        return UserDto.from(user);

    }

    public ResponseEntity<UserDto> login(String email,String password) throws UserNotFound {
       Optional<User> optionalUser=userRepository.findUserByEmail(email);

       if(optionalUser.isEmpty())
       {
           throw new UserNotFound("User with email: " + email + " doesn't exist.");
       }
       User user=optionalUser.get();
       if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
       {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

//        String token= RandomStringUtils.randomAscii(20);
//


        //let's create JWT token
        //

        Algorithm algorithm = Algorithm.HMAC256("Arun");
        String token= JWT.create().
                       withSubject(user.getEmail()).
                       withClaim("userId",user.getId()).
                       withIssuedAt(new Date()).
                       sign(algorithm);

          MultiValueMap<String,String> header=new LinkedMultiValueMap<>();
          header.add("AUTH_TOKEN",token);


        Session session=new Session();
        session.setTokens(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        ResponseEntity<UserDto> responseEntity=new ResponseEntity<>(UserDto.from(user),header,HttpStatus.OK);

        return  responseEntity;




    }

    public ResponseEntity<Void> logout(String token,Long userId)
    {

        try {
            DecodedJWT jwt = JWT.decode(token);

            // Get the algorithm from the decoded JWT
            Algorithm algorithm = Algorithm.HMAC256("Arun");

            // Create a verifier using the same algorithm and secret key
            JWTVerifier verifier = JWT.require(algorithm)

                    .build();

            // Verify the token
            verifier.verify(token);

            Optional<Session> sessionOptional = sessionRepository.findByTokensAndUser_Id(token, userId);

            if (sessionOptional.isEmpty()) {
                return null;
            }

            Session session = sessionOptional.get();
            session.setSessionStatus(SessionStatus.LOGGED_OUT);
            sessionRepository.save(session);

            System.out.println(session);
            return ResponseEntity.ok().build();
        }
        catch (JWTVerificationException exception){
            // Token verification failed
            // Handle the exception accordingly
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<UserDto> validate(String token,Long userId) throws TokenExpiredException, TokenNotValidException {
        try {
            DecodedJWT jwt = JWT.decode(token);

            // Get the algorithm from the decoded JWT
            Algorithm algorithm = Algorithm.HMAC256("Arun");

            // Create a verifier using the same algorithm and secret key
            JWTVerifier verifier = JWT.require(algorithm)

                    .build();

            // Verify the token
            verifier.verify(token);

            Optional<Session> sessionOptional = sessionRepository.findByTokensAndUser_Id(token, userId);

            if (sessionOptional.isEmpty()) {
                throw new TokenNotValidException("Token Not Valid");
            }



            Session session = sessionOptional.get();

            if(!session.getSessionStatus().equals(SessionStatus.ACTIVE))
            {
               throw new TokenExpiredException("Token Expired");
            }

           User user= userRepository.findById(userId);
            return new ResponseEntity<>(UserDto.from(user),HttpStatus.OK);

        }
        catch (JWTVerificationException exception){
            // Token verification failed
            // Handle the exception accordingly
            throw  exception;
        }
    }
}
