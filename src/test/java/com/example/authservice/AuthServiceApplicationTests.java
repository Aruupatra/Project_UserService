package com.example.authservice;

import com.example.authservice.Exceptions.UserAlreadyExist;
import com.example.authservice.controlers.AuthControler;
import com.example.authservice.dtos.UserDto;
import com.example.authservice.models.Role;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.security.repositories.JpaRegisteredClientRepository;
import com.example.authservice.services.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AuthServiceApplicationTests {

@Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;
@Autowired
    private PasswordEncoder passwordEncoder;

//    public AuthServiceApplicationTests(JpaRegisteredClientRepository jpaRegisteredClientRepository,PasswordEncoder passwordEncoder)
//    {
//        this.jpaRegisteredClientRepository=jpaRegisteredClientRepository;
//        this.passwordEncoder=passwordEncoder;
//    }
//    @Autowired
//    private AuthService authService;
//    @Test
//    void contextLoads() throws UserAlreadyExist {
////        Role role=new Role();
////        role.setRole("ADMIN");
////        roleRepository.save(role);
//
//
//        UserDto userDto=authService.signUp("arunscaler@gmail.com","arunscaler");
//
//    }

   @Test
    void addClient()
    {
                RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("productservice")
                .clientSecret(passwordEncoder.encode("productservice"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/browser-callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

                jpaRegisteredClientRepository.save(oidcClient);


    }

}
