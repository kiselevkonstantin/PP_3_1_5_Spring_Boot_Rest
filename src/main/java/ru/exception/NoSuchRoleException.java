package ru.exception;

public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException(String message) {
        super(message);
    }
}
