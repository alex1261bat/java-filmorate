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
        findUserById(user.getId());
        return userStorage.updateUser(user);
    }

    public boolean deleteUserById(long id) {
        findUserById(id);
        return userStorage.deleteUserById(id);
    }

    public User addToFriends(long userId, long friendId) { // метод добавления пользователя в друзья
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        friend.getFriends().add(userId);
        userStorage.saveFriendToTable(userId, friendId);
        return friend;
    }

    public User deleteFromFriends(long userId, long friendId) { // метод удаления пользователя из друзей
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        userStorage.deleteFriendFromTable(userId, friendId);
        return user;
    }

    public List<User> findAllFriends(long userId) { // метод получения списка друзей
        User user = findUserById(userId);
        List<User> friends = new ArrayList<>();

        for (Long id : user.getFriends()) {
            friends.add(findUserById(id));
        }

        return friends;
    }

    public List<User> findCommonFriends(long userId, long otherUserId) { // метод получения списка общих друзей
        User user = findUserById(userId);
        User otherUser = findUserById(otherUserId);
        List<User> commonFriends = new ArrayList<>();

        if (!user.getFriends().isEmpty() && !otherUser.getFriends().isEmpty()) {
            for (Long id : user.getFriends()) {
                if (otherUser.getFriends().contains(id)) {
                    commonFriends.add(findUserById(id));
                }
            }
        } else {
            return Collections.emptyList();
        }

        return commonFriends;
    }
}