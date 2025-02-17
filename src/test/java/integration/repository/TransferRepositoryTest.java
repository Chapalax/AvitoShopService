package integration.repository;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.model.Transfer;
import ru.avito.internship.domain.model.User;
import ru.avito.internship.repository.TransferRepository;
import ru.avito.internship.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AvitoShopApplication.class)
public class TransferRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    void saveAndFindBySenderTest() {
        User user1 = new User();
        user1.setUsername("test1");
        user1.setPassword("test");
        user1.setBalance(1000);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("test2");
        user2.setPassword("test");
        user2.setBalance(1000);
        userRepository.save(user2);

        Transfer transfer = new Transfer();
        transfer.setSender(user1.getId());
        transfer.setRecipient(user2.getId());
        transfer.setAmount(200);

        Transfer saved = transferRepository.saveAndFlush(transfer);
        assertThat(saved.getId()).isNotNull();

        var bySender = transferRepository.findAllBySender(user1.getId());
        assertThat(bySender).isNotEmpty();
        assertThat(bySender.get(0).getAmount()).isEqualTo(200);
    }

    @Test
    @Transactional
    @Rollback
    void findByRecipientTest() {
        User user1 = new User();
        user1.setUsername("test1");
        user1.setPassword("test");
        user1.setBalance(1000);
        userRepository.saveAndFlush(user1);

        User user2 = new User();
        user2.setUsername("test2");
        user2.setPassword("test");
        user2.setBalance(1000);
        userRepository.saveAndFlush(user2);

        Transfer transfer = new Transfer();
        transfer.setSender(user1.getId());
        transfer.setRecipient(user2.getId());
        transfer.setAmount(300);

        Transfer saved = transferRepository.saveAndFlush(transfer);
        assertThat(saved.getId()).isNotNull();

        var byRecipient = transferRepository.findAllByRecipient(user2.getId());
        assertThat(byRecipient).hasSize(1);
        assertThat(byRecipient.get(0).getSender()).isEqualTo(user1.getId());
    }
}
