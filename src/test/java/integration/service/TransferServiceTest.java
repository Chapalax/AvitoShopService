package integration.service;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.dto.InventoryElement;
import ru.avito.internship.domain.dto.TransfersHistory;
import ru.avito.internship.domain.exception.InsufficientFundsException;
import ru.avito.internship.domain.exception.UserNotFoundException;
import ru.avito.internship.domain.model.User;
import ru.avito.internship.service.TransferService;
import ru.avito.internship.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = AvitoShopApplication.class)
public class TransferServiceTest extends IntegrationEnvironment {

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    @Rollback
    void buyMerchTest() {
        User user = new User();
        user.setUsername("buyer");
        user.setPassword("password");
        user.setBalance(2000);
        userService.save(user);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("buyer", null, List.of())
        );

        transferService.buyMerch("umbrella");

        User updatedUser = userService.getByUsername("buyer");
        assertThat(updatedUser.getBalance()).isEqualTo(1800);

        List<InventoryElement> inventory = transferService.getUserInventory(updatedUser.getId());
        assertThat(inventory).hasSize(1);
        assertThat(inventory.get(0).quantity()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void buyMerchInsufficientFundsTest() {
        User user = new User();
        user.setUsername("buyer");
        user.setPassword("password");
        user.setBalance(50);
        userService.save(user);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("buyer", null, List.of())
        );

        assertThatThrownBy(() -> transferService.buyMerch("umbrella"))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds in the account");
    }

    @Test
    @Transactional
    @Rollback
    void makeTransferTest() {
        User sender = new User();
        sender.setUsername("sender");
        sender.setPassword("password");
        sender.setBalance(2000);
        userService.save(sender);

        User receiver = new User();
        receiver.setUsername("receiver");
        receiver.setPassword("password");
        receiver.setBalance(1000);
        userService.save(receiver);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("sender", null, List.of())
        );

        transferService.makeTransfer("receiver", 500);

        User updatedSender = userService.getByUsername("sender");
        User updatedReceiver = userService.getByUsername("receiver");

        assertThat(updatedSender.getBalance()).isEqualTo(1500);
        assertThat(updatedReceiver.getBalance()).isEqualTo(1500);
    }

    @Test
    @Transactional
    @Rollback
    void makeTransferInsufficientFundsTest() {
        User sender = new User();
        sender.setUsername("sender");
        sender.setPassword("password");
        sender.setBalance(200);
        userService.save(sender);

        User receiver = new User();
        receiver.setUsername("receiver");
        receiver.setPassword("password");
        receiver.setBalance(1000);
        userService.save(receiver);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("sender", null, List.of())
        );

        assertThatThrownBy(() -> transferService.makeTransfer("receiver", 500))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds in the account");
    }

    @Test
    @Transactional
    @Rollback
    void makeTransferUserNotFoundTest() {
        User sender = new User();
        sender.setUsername("sender");
        sender.setPassword("password");
        sender.setBalance(2000);
        userService.save(sender);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("sender", null, List.of())
        );

        assertThatThrownBy(() -> transferService.makeTransfer("nonexistent", 500))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    @Transactional
    @Rollback
    void getUserTransfersHistoryTest() {
        User sender = new User();
        sender.setUsername("sender");
        sender.setPassword("password");
        sender.setBalance(2000);
        userService.save(sender);

        User receiver = new User();
        receiver.setUsername("receiver");
        receiver.setPassword("password");
        receiver.setBalance(1000);
        userService.save(receiver);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("sender", null, List.of())
        );

        transferService.makeTransfer("receiver", 500);

        TransfersHistory history = transferService.getUserTransfersHistory(sender.getId());
        assertThat(history.sent()).hasSize(1);
        assertThat(history.sent().get(0).amount()).isEqualTo(500);
        assertThat(history.sent().get(0).toUser()).isEqualTo("receiver");

        history = transferService.getUserTransfersHistory(receiver.getId());
        assertThat(history.received()).hasSize(1);
        assertThat(history.received().get(0).amount()).isEqualTo(500);
        assertThat(history.received().get(0).fromUser()).isEqualTo("sender");
    }
}