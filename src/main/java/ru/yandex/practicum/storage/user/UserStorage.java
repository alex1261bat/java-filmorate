package ru.yandex.practicum.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.models.User;

import java.util.Collection;
import java.util.Optional;

@Component
public interface UserStorage {

    Collection<User> findAllUsers(); // метод получения списка всех пользователей

    Optional<User> findUserById(long id); // метод получения пользователя по id

    User createUser(User user); // метод создания пользователя

    User updateUser(User user); // метод обновления пользователя

    boolean deleteUserById(long id); // метод удаления пользователя по id

    void saveFriendToTable(long userId, long friendId); // сохранение друга в DB

    void deleteFriendFromTable(long userId, long friendId); // удаление друга из DB
}