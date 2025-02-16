package integration;

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
public class UserRepositoryTest extends IntegrationEnvironment{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void create_UserTest() {
        var user = new User();
        user.setUsername("test");
        user.setPassword("test");

        assertThat(userRepository.saveAndFlush(user).getId()).isNotNull();
        assertThat(jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> rs.getLong("id")))
                .hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void findByUsername_UserExistsTest() {
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
    void findByUsername_UserNotFoundTest() {
        var result = userRepository.findByUsername("missing");
        assertThat(result).isNotPresent();
    }

    @Test
    @Transactional
    @Rollback
    void existsByUsername_TrueTest() {
        var user = new User();
        user.setUsername("duplicate");
        user.setPassword("pwd");
        userRepository.save(user);

        assertThat(userRepository.existsByUsername("duplicate")).isTrue();
    }

    @Test
    void existsByUsername_FalseTest() {
        assertThat(userRepository.existsByUsername("unknown")).isFalse();
    }
}
