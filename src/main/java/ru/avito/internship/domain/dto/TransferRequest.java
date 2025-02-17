package ru.avito.internship.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
        @NotNull
        @NotBlank(message = "The username cannot be empty")
        String toUser,

        @NotNull
        Integer amount
) { }
