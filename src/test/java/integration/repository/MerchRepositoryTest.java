package integration.repository;

import integration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.AvitoShopApplication;
import ru.avito.internship.domain.model.Merch;
import ru.avito.internship.repository.MerchRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AvitoShopApplication.class)
public class MerchRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private MerchRepository merchRepository;

    @Test
    @Transactional
    @Rollback
    void saveAndFindByItemTest() {
        Merch merch = new Merch();
        merch.setItem("NewItem");
        merch.setPrice(50);

        Merch saved = merchRepository.saveAndFlush(merch);
        assertThat(saved.getId()).isNotNull();

        var found = merchRepository.findByItem("NewItem");
        assertThat(found).isPresent();
        assertThat(found.get().getPrice()).isEqualTo(50);
    }

    @Test
    void itemNotFoundTest() {
        var result = merchRepository.findByItem("MissingItem");
        assertThat(result).isNotPresent();
    }
}