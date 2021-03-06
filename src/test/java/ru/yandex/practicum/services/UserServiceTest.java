package ru.yandex.practicum.services;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.UserNotFoundException;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage);

    @Test
    void shouldThrowUserNotFoundExceptionIfNoSuchUser() {
        String message = null;

        try {
            userService.findUserById(0);
        } catch (UserNotFoundException userNotFoundException) {
            message = userNotFoundException.getMessage();
        }

        assertEquals("Пользователь с  id=" + 0 + " не существует.", message);
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(0));
    }

    @Test
    void shouldAddToFriends() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User otherUser = new User(LocalDate.of(2001, 11, 1), "otherUser@mail.ru",
                "otherUserLogin", "otherUser");

        userService.createUser(user);
        userService.createUser(otherUser);

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

        userService.createUser(user);
        userService.createUser(otherUser);

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

        userService.createUser(user);
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

        userService.createUser(user);
        userService.createUser(otherUser);
        userService.createUser(friendUser);

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

        userService.createUser(user);
        userService.createUser(otherUser);
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

        userService.createUser(user);
        userService.createUser(otherUser);
        userService.createUser(friendUser);
        userService.addToFriends(user.getId(), friendUser.getId());
        userService.addToFriends(otherUser.getId(), friendUser.getId());
        Collection<User> commonFriends = userService.findCommonFriends(user.getId(), otherUser.getId());

        assertEquals(1, commonFriends.size());
    }
}