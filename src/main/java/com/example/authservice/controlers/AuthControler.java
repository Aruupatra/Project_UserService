package com.example.authservice.controlers;

import com.example.authservice.Exceptions.TokenExpiredException;
import com.example.authservice.Exceptions.TokenNotValidException;
import com.example.authservice.Exceptions.UserAlreadyExist;
import com.example.authservice.Exceptions.UserNotFound;
import com.example.authservice.dtos.*;
import com.example.authservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthControler {

    private AuthService authService;

    public AuthControler(AuthService authService)
    {
        this.authService=authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExist {

       UserDto userDto=authService.signUp(signUpRequestDto.getEmail(),signUpRequestDto.getPassword());

       return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFound {


         ResponseEntity<UserDto> userDtoResponseEntity=authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        return userDtoResponseEntity;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto)
    {

        ResponseEntity<Void> responseEntity=authService.logout(logoutRequestDto.getToken(),logoutRequestDto.getUserId());

        return responseEntity;
    }

    @PostMapping("/validate")
    public ResponseEntity<UserDto> validate(@RequestBody ValidateRequestDto validateRequestDto) throws TokenExpiredException {


        try {
            ResponseEntity<UserDto> responseEntity = authService.validate(validateRequestDto.getToken(),validateRequestDto.getUserId());
            return responseEntity;
        } catch (TokenExpiredException e) {
            throw new RuntimeException(e);
        } catch (TokenNotValidException e) {
            throw new RuntimeException(e);
        }


    }

}
