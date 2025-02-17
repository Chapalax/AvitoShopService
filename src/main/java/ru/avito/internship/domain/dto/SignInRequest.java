package ru.avito.internship.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(
        @NotNull
        @NotBlank(message = "The username cannot be empty")
        String username,

        @NotNull
        @NotBlank(message = "The password cannot be empty")
        String password
) { }
