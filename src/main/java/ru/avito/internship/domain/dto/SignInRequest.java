package ru.avito.internship.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(
        @NotNull
        @NotBlank(message = "Имя пользователя не может быть пустым")
        String username,

        @NotNull
        @NotBlank(message = "Пароль не может быть пустым")
        String password
) { }
