package ru.avito.internship.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class InventoryKey implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "merch_id", nullable = false)
    private Long merchId;
}
