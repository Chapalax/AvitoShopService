package ru.avito.internship.domain.exception;

public class MerchNotFoundException extends RuntimeException {
    public MerchNotFoundException(String message) {
        super(message);
    }
}
