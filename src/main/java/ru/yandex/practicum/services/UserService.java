package ru.yandex.practicum.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Data
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addToFriends(long userId, long friendId) { // метод добавления пользователя в друзья
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        return friend;
    }

    public User deleteFromFriends(long userId, long friendId) { // метод удаления пользователя из друзей
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        return user;
    }

    public List<User> findAllFriends(long userId) { // метод получения списка друзей
        User user = userStorage.findUserById(userId);
        List<User> friends = new ArrayList<>();

        for (Long id : user.getFriends()) {
            friends.add(userStorage.findUserById(id));
        }

        return friends;
    }

    public List<User> findCommonFriends(long userId, long otherUserId) { // метод получения списка общих друзей
        User user = userStorage.findUserById(userId);
        User otherUser = userStorage.findUserById(otherUserId);
        List<User> commonFriends = new ArrayList<>();

        if (!user.getFriends().isEmpty() && !otherUser.getFriends().isEmpty()) {
            for (Long id : user.getFriends()) {
                if (otherUser.getFriends().contains(id)) {
                    commonFriends.add(userStorage.findUserById(id));
                }
            }
        } else {
            return Collections.emptyList();
        }

        return commonFriends;
    }
}