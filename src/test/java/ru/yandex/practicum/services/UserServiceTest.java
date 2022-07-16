package ru.yandex.practicum.services;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage);

    @Test
    void addToFriends() {
    }

    @Test
    void deleteFromFriends() {
    }

    @Test
    void findAllFriends() {
    }

    @Test
    void shouldReturnEmptyListIfNoCommonFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User otherUser = new User(LocalDate.of(2001, 11, 1), "otherUser@mail.ru",
                "otherUserLogin", "otherUser");

        userStorage.createUser(user);
        userStorage.createUser(otherUser);
        Collection<User> commonFriends = userService.findCommonFriends(user.getId(), otherUser.getId());

        assertTrue(commonFriends.isEmpty());
    }
}