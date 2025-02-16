package ru.avito.internship.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfers")
public class Transfer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_id_seq")
    @SequenceGenerator(name = "transfer_id_seq", sequenceName = "transfer_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "sender", nullable = false)
    private Long sender;

    @Column(name = "recipient", nullable = false)
    private Long recipient;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
