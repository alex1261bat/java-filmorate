package ru.yandex.practicum.exceptions;

public class FriendStatusNotFoundException extends RuntimeException {
    public FriendStatusNotFoundException(int id) {
        super("FriendStatus с id=" + id + " не существует.");
    }
}
