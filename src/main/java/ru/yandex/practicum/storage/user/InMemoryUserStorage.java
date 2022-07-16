package ru.yandex.practicum.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

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
    public User findUserById(long id) { // метод получения пользователя по id
        if (!users.containsKey(id)) {
            log.warn("Такой пользователь отсутствует.");
            throw new NoSuchElementException("Такой пользователь отсутствует.");
        }

        return users.get(id);
    }

    @Override
    public User createUser(User user) { // метод создания пользователя
        user.setId(++id);
        validateUser(user);

        log.trace("Сохранен объект: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) { // метод обновления пользователя
        if (!users.containsKey(user.getId())) {
            log.warn("Такой пользователь отсутствует.");
            throw new NoSuchElementException("Такой пользователь отсутствует.");
        }

        validateUser(user);
        log.trace("Сохранен объект: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User deleteUserById(long id) { // метод удаления пользователя по id
        if (!users.containsKey(id)) {
            log.warn("Такой пользователь отсутствует.");
            throw new NoSuchElementException("Такой пользователь отсутствует.");
        }

        User user = users.get(id);
        log.trace("Удален объект: {}", user);
        users.remove(id);
        return user;
    }

    @Override
    public void validateUser(User user) { // метод валидации пользователя
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            this.id -= 1;
            log.warn("Электронная почта не может быть пустой и должна содержать символ @.");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        } else if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            this.id -= 1;
            log.warn("Логин не может быть пустым и содержать пробелы.");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            this.id -= 1;
            log.warn("Дата рождения не может быть в будущем.");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        } else if (user.getId() < 1) {
            log.warn("Неверный id.");
            throw new ValidationException("Неверный id.");
        }
    }
}