package ru.yandex.practicum.exceptions;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(int id) {
        super("Жанр с id=" + id + " не существует.");
    }
}
