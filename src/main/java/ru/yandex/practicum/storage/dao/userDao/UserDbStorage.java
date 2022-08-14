package ru.yandex.practicum.storage.dao.userDao;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.UserNotFoundException;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> findAllUsers() {
        String sqlQuery = "select * from users";
        return jdbcTemplate.query(sqlQuery, this::makeUser);
    }

    @Override
    public Optional<User> findUserById(long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", id);
        return getUser(userRows);
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users (name, login, email, birthday) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday());

        log.trace("Сохранен объект: {}", user);
        return findUserByLogin(user.getLogin()).orElseThrow(() -> new UserNotFoundException(user.getId()));
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set " +
                "name = ?, login = ?, email = ?, birthday = ? "
                + "where id = ?";

        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        log.trace("Обновлен объект: {}.", user);
        return user;
    }

    @Override
    public boolean deleteUserById(long id) {
        User user = findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        String sqlQuery = "delete from users where id = ?";

        deleteUserFriendsFromTable(user);
        log.trace("Удален объект: {}.", user);
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private User makeUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getString("login"), resultSet.getString("email"),
                resultSet.getDate("birthday").toLocalDate());

        loadUserFriends(user);
        return user;
    }

    private void loadUserFriends(User user) {
        if (!getUserFriendsFromTable(user).isEmpty()) {
            for (Long id : getUserFriendsFromTable(user)) {
                user.getFriends().add(id);
            }
        }
    }

    private Collection<Long> getUserFriendsFromTable(User user) {
        String sqlQuery = "select other_user_id from user_friends where user_id = ?";
        return jdbcTemplate.query(sqlQuery, this::getFriendIdFromTableUserFriends, user.getId());
    }

    private Long getFriendIdFromTableUserFriends(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getLong("other_user_id");
    }

    private void deleteUserFriendsFromTable(User user) {
        String sqlQuery = "delete from user_friends where user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    private Optional<User> findUserByLogin(String login) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where login = ?", login);
        return getUser(userRows);
    }

    @NotNull
    private Optional<User> getUser(SqlRowSet userRows) {
        if(userRows.next()) {
            User user = new User(
                    userRows.getLong("id"),
                    userRows.getString("name"),
                    userRows.getString("login"),
                    userRows.getString("email"),
                    Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate()
            );

            loadUserFriends(user);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}
