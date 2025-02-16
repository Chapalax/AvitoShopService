package ru.avito.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avito.internship.domain.model.Inventory;
import ru.avito.internship.domain.model.InventoryKey;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, InventoryKey> {
    List<Inventory> findAllById_UserId(Long userId);
}
