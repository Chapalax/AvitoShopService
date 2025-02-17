package integration.repository;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.model.User;
import ru.avito.internship.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AvitoShopApplication.class)
public class UserRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void saveUserTest() {
        var user = new User();
        user.setUsername("user");
        user.setPassword("secret");

        assertThat(userRepository.saveAndFlush(user).getId()).isNotNull();
        assertThat(jdbcTemplate.query("SELECT * FROM users WHERE username = 'user'", (rs, rowNum) -> rs.getLong("id")))
                .hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void findByUsernameUserExistsTest() {
        var user = new User();
        user.setUsername("existing");
        user.setPassword("pwd");
        userRepository.save(user);

        var foundUser = userRepository.findByUsername("existing");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("existing");
    }

    @Test
    @Transactional
    @Rollback
    void findByUsernameUserNotFoundTest() {
        var result = userRepository.findByUsername("missing");
        assertThat(result).isNotPresent();
    }

    @Test
    @Transactional
    @Rollback
    void existsByUsernameTrueTest() {
        var user = new User();
        user.setUsername("duplicate");
        user.setPassword("pwd");
        userRepository.save(user);

        assertThat(userRepository.existsByUsername("duplicate")).isTrue();
    }

    @Test
    void existsByUsernameFalseTest() {
        assertThat(userRepository.existsByUsername("unknown")).isFalse();
    }
}
