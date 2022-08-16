package ru.yandex.practicum.storage.dao.userDao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userStorage;

    @Test
    void findAllUsers() {
        userStorage.createUser(new User(0, "name1", "login1", "name1@mail.com",
                LocalDate.of(2000, 12, 12)));
        userStorage.createUser(new User(0, "name2", "login2", "name2@mail.com",
                LocalDate.of(2000, 12, 12)));

        ArrayList<User> users = (ArrayList<User>) userStorage.findAllUsers();

        assertThat(users)
                .isNotEmpty()
                .hasSize(5);
    }

    @Test
    void findUserById() {
        userStorage.createUser(new User(0, "name1", "login1", "name1@mail.com",
                LocalDate.of(2000, 12, 12)));
        Optional<User> userOptional = userStorage.findUserById(2);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 2L)
                );
    }

    @Test
    void createUser() {
        userStorage.createUser(new User(0, "name3", "login1", "name1@mail.com",
                LocalDate.of(2000, 12, 12)));
        Optional<User> userOptional = userStorage.findUserById(3);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "name3")
                );
    }

    @Test
    void updateUser() {
        userStorage.createUser(new User(0, "name1", "login1", "name1@mail.com",
                LocalDate.of(2000, 12, 12)));
        Optional<User> userOptional = userStorage.findUserById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );

        userStorage.updateUser(new User(userOptional.get().getId(), "name2", "login1", "name1@mail.com",
                LocalDate.of(2000, 12, 12)));
        Optional<User> userOptional1 = userStorage.findUserById(1);

        assertThat(userOptional1)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "name2")
                );
    }

    @Test
    void deleteUserById() {
        userStorage.createUser(new User(0, "name1", "login1", "name1@mail.com",
                LocalDate.of(2000, 12, 12)));
        userStorage.createUser(new User(0, "name2", "login2", "name2@mail.com",
                LocalDate.of(2000, 12, 12)));

        ArrayList<User> users = (ArrayList<User>) userStorage.findAllUsers();

        assertThat(users)
                .isNotEmpty()
                .hasSize(7);

        userStorage.deleteUserById(1);

        ArrayList<User> users1 = (ArrayList<User>) userStorage.findAllUsers();

        assertThat(users1)
                .isNotEmpty()
                .hasSize(6);
    }
}