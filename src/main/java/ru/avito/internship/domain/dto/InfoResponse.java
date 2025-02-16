package ru.avito.internship.domain.dto;

import java.util.List;

public record InfoResponse(
        Integer coins,
        List<InventoryElement> inventory,
        TransfersHistory coinHistory
) { }
