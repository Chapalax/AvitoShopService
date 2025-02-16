package ru.avito.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avito.internship.domain.model.Merch;

import java.util.Optional;

@Repository
public interface MerchRepository extends JpaRepository<Merch, Long> {
    Optional<Merch> findByItem(String item);
}
