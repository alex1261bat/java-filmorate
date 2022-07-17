package ru.yandex.practicum.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(long id) {
        super("Фильм с id=" + id + " не существует.");
    }
}