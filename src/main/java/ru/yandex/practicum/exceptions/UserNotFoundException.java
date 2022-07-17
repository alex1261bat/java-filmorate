package ru.yandex.practicum.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("Пользователь с  id=" + id + " не существует.");
    }
}