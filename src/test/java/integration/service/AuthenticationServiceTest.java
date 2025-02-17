package integration.service;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.dto.JwtAuthenticationResponse;
import ru.avito.internship.domain.dto.SignInRequest;
import ru.avito.internship.domain.model.User;
import ru.avito.internship.service.AuthenticationService;
import ru.avito.internship.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = AvitoShopApplication.class)
public class AuthenticationServiceTest extends IntegrationEnvironment {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    @Rollback
    void signInNewUserTest() {
        SignInRequest request = new SignInRequest("newuser", "password");
        JwtAuthenticationResponse response = authenticationService.signIn(request);

        User savedUser = userService.getByUsername("newuser");
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
        assertThat(response.token()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void signInWithWrongPasswordTest() {
        User user = new User();
        user.setUsername("someuser");
        user.setPassword("password");
        userService.save(user);

        SignInRequest request = new SignInRequest("someuser", "wrongpassword");
        assertThatThrownBy(() -> authenticationService.signIn(request))
                .isInstanceOf(BadCredentialsException.class);
    }
}