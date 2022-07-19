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

    User deleteUserById(long id); // метод удаления пользователя по id
}