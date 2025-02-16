package ru.avito.internship.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merch")
public class Merch {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merch_id_seq")
    @SequenceGenerator(name = "merch_id_seq", sequenceName = "merch_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "item", nullable = false)
    private String item;

    @Column(name = "price", nullable = false)
    private Integer price;
}
