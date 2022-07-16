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
    void shouldAddToFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User otherUser = new User(LocalDate.of(2001, 11, 1), "otherUser@mail.ru",
                "otherUserLogin", "otherUser");

        userStorage.createUser(user);
        userStorage.createUser(otherUser);

        assertEquals(0, userService.findAllFriends(user.getId()).size());
        assertEquals(0, userService.findAllFriends(otherUser.getId()).size());

        userService.addToFriends(user.getId(), otherUser.getId());

        assertEquals(1, userService.findAllFriends(user.getId()).size());
        assertEquals(1, userService.findAllFriends(otherUser.getId()).size());
    }

    @Test
    void shouldDeleteFromFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User otherUser = new User(LocalDate.of(2001, 11, 1), "otherUser@mail.ru",
                "otherUserLogin", "otherUser");

        userStorage.createUser(user);
        userStorage.createUser(otherUser);

        assertEquals(0, userService.findAllFriends(user.getId()).size());
        assertEquals(0, userService.findAllFriends(otherUser.getId()).size());

        userService.addToFriends(user.getId(), otherUser.getId());

        assertEquals(1, userService.findAllFriends(user.getId()).size());
        assertEquals(1, userService.findAllFriends(otherUser.getId()).size());

        userService.deleteFromFriends(user.getId(), otherUser.getId());

        assertEquals(0, userService.findAllFriends(user.getId()).size());
        assertEquals(0, userService.findAllFriends(otherUser.getId()).size());
    }

    @Test
    void shouldReturnEmptyListIfNoFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        userStorage.createUser(user);
        assertEquals(0, userService.findAllFriends(user.getId()).size());
    }

    @Test
    void shouldReturnListOfFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User otherUser = new User(LocalDate.of(2001, 11, 1), "otherUser@mail.ru",
                "otherUserLogin", "otherUser");
        User friendUser = new User(LocalDate.of(2002, 11, 1), "friendUser@mail.ru",
                "friendUserLogin", "friendUser");

        userStorage.createUser(user);
        userStorage.createUser(otherUser);
        userStorage.createUser(friendUser);

        assertEquals(0, userService.findAllFriends(user.getId()).size());

        userService.addToFriends(user.getId(), otherUser.getId());
        userService.addToFriends(user.getId(), friendUser.getId());

        assertEquals(2, userService.findAllFriends(user.getId()).size());


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

    @Test
    void shouldReturnNoCommonFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User otherUser = new User(LocalDate.of(2001, 11, 1), "otherUser@mail.ru",
                "otherUserLogin", "otherUser");
        User friendUser = new User(LocalDate.of(2002, 11, 1), "friendUser@mail.ru",
                "friendUserLogin", "friendUser");

        userStorage.createUser(user);
        userStorage.createUser(otherUser);
        userStorage.createUser(friendUser);
        userService.addToFriends(user.getId(), friendUser.getId());
        userService.addToFriends(otherUser.getId(), friendUser.getId());
        Collection<User> commonFriends = userService.findCommonFriends(user.getId(), otherUser.getId());

        assertEquals(1, commonFriends.size());
    }
}