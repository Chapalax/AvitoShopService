package ru.avito.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avito.internship.domain.model.Transfer;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllBySender(Long sender);
    List<Transfer> findAllByRecipient(Long recipient);
}
