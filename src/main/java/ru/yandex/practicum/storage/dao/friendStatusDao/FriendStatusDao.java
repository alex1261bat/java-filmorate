package ru.yandex.practicum.storage.dao.friendStatusDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.FriendStatusNotFoundException;
import ru.yandex.practicum.models.FriendStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component("friendStatusDao")
@Slf4j
public class FriendStatusDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<FriendStatus> findAllFriendStatus() {
        String sqlQuery = "select id, name from friend_status";
        return jdbcTemplate.query(sqlQuery, this::makeFriendStatus);
    }

    public Optional<FriendStatus> findFriendStatusById(int friendStatusId) {
        SqlRowSet friendStatusRows = jdbcTemplate.queryForRowSet("select * from friend_status where id = ?",
                friendStatusId);

        if (friendStatusRows.next()) {
            FriendStatus friendStatus = new FriendStatus(
                    friendStatusRows.getInt("id"),
                    friendStatusRows.getString("name")
            );
            return Optional.of(friendStatus);
        } else {
            return Optional.empty();
        }
    }

    public FriendStatus createFriendStatus(FriendStatus friendStatus) {
        String sqlQuery = "insert into friend_status (name) " +
                "values (?)";
        jdbcTemplate.update(sqlQuery,
                friendStatus.getName());
        log.trace("Сохранен объект: {}", friendStatus);
        return friendStatus;
    }

    public FriendStatus updateFriendStatus(FriendStatus friendStatus) {
        String sqlQuery = "update friend_status set " +
                "name = ?" + "where id = ?";

        jdbcTemplate.update(sqlQuery,
                friendStatus.getName(),
                friendStatus.getId());
        log.trace("Обновлен объект: {}.", friendStatus);
        return friendStatus;
    }

    public boolean deleteFriendStatusById(int id) {
        FriendStatus friendStatus = findFriendStatusById(id).orElseThrow(() -> new FriendStatusNotFoundException(id));
        String sqlQuery = "delete from friend_status where id = ?";

        log.trace("Удален объект: {}.", friendStatus);
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private FriendStatus makeFriendStatus(ResultSet resultSet, int rowNum) throws SQLException {
        return new FriendStatus(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
