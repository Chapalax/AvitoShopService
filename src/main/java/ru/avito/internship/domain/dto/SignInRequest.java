package ru.avito.internship.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "Имя пользователя не может быть пустыми")
        String username,

        @NotBlank(message = "Пароль не может быть пустыми")
        String password
) { }
