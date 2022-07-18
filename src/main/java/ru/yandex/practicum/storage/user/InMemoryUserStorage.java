package ru.yandex.practicum.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private long id;

    @Override
    public Collection<User> findAllUsers() { // метод получения списка всех пользователей
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findUserById(long id) { // метод получения пользователя по id
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User createUser(User user) { // метод создания пользователя
        user.setId(++id);
        users.put(user.getId(), user);
        log.trace("Сохранен объект: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) { // метод обновления пользователя
        users.put(user.getId(), user);
        log.trace("Обновлен объект: {}.", user);
        return user;
    }

    @Override
    public User deleteUserById(long id) { // метод удаления пользователя по id
        User user = users.get(id);

        users.remove(id);
        log.trace("Удален объект: {}", user);
        return user;
    }
}