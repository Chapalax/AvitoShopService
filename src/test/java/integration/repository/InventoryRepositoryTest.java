package integration.repository;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.model.Inventory;
import ru.avito.internship.domain.model.InventoryKey;
import ru.avito.internship.domain.model.User;
import ru.avito.internship.repository.InventoryRepository;
import ru.avito.internship.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AvitoShopApplication.class)
public class InventoryRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    void saveAndFindInventoryTest() {
        User user = new User();
        user.setUsername("test1");
        user.setPassword("test");
        user.setBalance(1000);
        userRepository.saveAndFlush(user);

        InventoryKey key = new InventoryKey(user.getId(), 2L);
        Inventory inventory = new Inventory(key, 10);

        Inventory savedInventory = inventoryRepository.saveAndFlush(inventory);
        assertThat(savedInventory.getId()).isEqualTo(key);

        var foundInventory = inventoryRepository.findById(key);
        assertThat(foundInventory).isPresent();
        assertThat(foundInventory.get().getAmount()).isEqualTo(10);
    }

    @Test
    void notFoundInventoryTest() {
        InventoryKey key = new InventoryKey(999L, 999L);
        var result = inventoryRepository.findById(key);
        assertThat(result).isNotPresent();
    }
}