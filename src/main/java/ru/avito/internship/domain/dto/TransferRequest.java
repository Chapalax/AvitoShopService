package ru.avito.internship.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
        @NotNull
        @NotBlank(message = "Имя пользователя не может быть пустыми")
        String toUser,

        @NotNull
        Integer amount
) { }
