package ru.yandex.practicum.exceptions;

public class RatingMPANotFoundException extends RuntimeException {
    public RatingMPANotFoundException(int id) {
        super("RatingMPA с id=" + id + " не существует.");
    }
}
