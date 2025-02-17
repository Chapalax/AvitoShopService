package integration.service;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.model.User;
import ru.avito.internship.service.UserService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = AvitoShopApplication.class)
public class UserServiceTest extends IntegrationEnvironment {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    @Rollback
    void saveUserTest() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");

        User savedUser = userService.save(user);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
    }

    @Test
    @Transactional
    @Rollback
    void getByUsernameUserExistsTest() {
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password");
        userService.save(user);

        User foundUser = userService.getByUsername("existinguser");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("existinguser");
    }

    @Test
    @Transactional
    @Rollback
    void getByUsernameUserNotFoundTest() {
        assertThatThrownBy(() -> userService.getByUsername("nonexistent"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Пользователь не найден");
    }

    @Test
    @Transactional
    @Rollback
    void getByIdUserExistsTest() {
        User user = new User();
        user.setUsername("userbyid");
        user.setPassword("password");
        User savedUser = userService.save(user);

        User foundUser = userService.getById(savedUser.getId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("userbyid");
    }

    @Test
    void getByIdUserNotFoundTest() {
        assertThatThrownBy(() -> userService.getById(999L))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Пользователь не найден");
    }

    @Test
    @Transactional
    @Rollback
    void existsByUsernameTrueTest() {
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password");
        userService.save(user);

        assertThat(userService.existsByUsername("existinguser")).isTrue();
    }

    @Test
    void existsByUsernameFalseTest() {
        assertThat(userService.existsByUsername("nonexistent")).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    void getCurrentUserTest() {
        User user = new User();
        user.setUsername("currentuser");
        user.setPassword("password");
        userService.save(user);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("currentuser", null, new ArrayList<>())
        );

        User currentUser = userService.getCurrentUser();
        assertThat(currentUser).isNotNull();
        assertThat(currentUser.getUsername()).isEqualTo("currentuser");
    }
}