package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.services.UserService;

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
        return userService.getUserStorage().findAllUsers();
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable long userId) { // получение пользователя по id
        return userService.getUserStorage().findUserById(userId);
    }

    @PostMapping
    public User createUser(@RequestBody User user) { // создание пользователя
        return userService.getUserStorage().createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) { // обновление пользователя
        return userService.getUserStorage().updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public User deleteUserById(@PathVariable long userId) { // удаление пользователя по id
        return userService.getUserStorage().deleteUserById(userId);
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
}