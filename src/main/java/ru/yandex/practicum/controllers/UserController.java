package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAllUsers() { // получение списка всех пользователей
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable long userId) { // получение пользователя по id
        return userService.findUserById(userId);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) { // создание пользователя
        validateUser(user);
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) { // обновление пользователя
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUserById(@PathVariable long userId) { // удаление пользователя по id
        return userService.deleteUserById(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addToFriends(@PathVariable long id, @PathVariable long friendId) { // добавление пользователя в друзья
        return userService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFromFriends(@PathVariable long id, @PathVariable long friendId) { // удаление пользователя из друзей
        return userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> findAllFriends(@PathVariable long id) { // получение списка друзей
        return userService.findAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> findCommonFriends(@PathVariable long id, @PathVariable long otherId) { // получение списка общих друзей
        return userService.findCommonFriends(id, otherId);
    }

    private void validateUser(User user) { // метод валидации пользователя
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы.");
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}