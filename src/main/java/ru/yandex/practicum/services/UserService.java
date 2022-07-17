package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.UserNotFoundException;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User findUserById(long id) {
        return userStorage.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        userStorage.findUserById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
        return userStorage.updateUser(user);
    }

    public User deleteUserById(long id) {
        userStorage.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userStorage.deleteUserById(id);
    }

    public User addToFriends(long userId, long friendId) { // метод добавления пользователя в друзья
        User user = userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User friend = userStorage.findUserById(friendId).orElseThrow(() -> new UserNotFoundException(friendId));

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        return friend;
    }

    public User deleteFromFriends(long userId, long friendId) { // метод удаления пользователя из друзей
        User user = userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User friend = userStorage.findUserById(friendId).orElseThrow(() -> new UserNotFoundException(friendId));

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        return user;
    }

    public List<User> findAllFriends(long userId) { // метод получения списка друзей
        User user = userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<User> friends = new ArrayList<>();

        for (Long id : user.getFriends()) {
            friends.add(userStorage.findUserById(id).orElseThrow(() -> new UserNotFoundException(id)));
        }

        return friends;
    }

    public List<User> findCommonFriends(long userId, long otherUserId) { // метод получения списка общих друзей
        User user = userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User otherUser = userStorage.findUserById(otherUserId).orElseThrow(() -> new UserNotFoundException(otherUserId));
        List<User> commonFriends = new ArrayList<>();

        if (!user.getFriends().isEmpty() && !otherUser.getFriends().isEmpty()) {
            for (Long id : user.getFriends()) {
                if (otherUser.getFriends().contains(id)) {
                    commonFriends.add(userStorage.findUserById(id).orElseThrow(() -> new UserNotFoundException(id)));
                }
            }
        } else {
            return Collections.emptyList();
        }

        return commonFriends;
    }
}