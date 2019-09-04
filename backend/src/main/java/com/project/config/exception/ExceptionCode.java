package com.project.config.exception;

public enum ExceptionCode {

    WRONG_PASSWORD("[101] Podano błędne hasło."),

    USER_NOT_FOUND("[201] Nie znaleziono takiego użytkownika."),
    USER_IS_BANNED("[202] Użytkownik został zbanowany."),
    USER_IS_NOT_ADMIN("[203] Użytkownik nie posiada roli \"ADMIN\".");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
