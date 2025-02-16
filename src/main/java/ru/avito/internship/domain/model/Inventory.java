package ru.avito.internship.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory {
    @EmbeddedId
    private InventoryKey id;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
