package ru.yandex.practicum.storage.dao.ratingMPADao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.RatingMPANotFoundException;
import ru.yandex.practicum.models.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component("ratingMPADao")
@Slf4j
public class RatingMPADao {
    private final JdbcTemplate jdbcTemplate;

    public RatingMPADao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<RatingMPA> findAllRatingsMPA() {
        String sqlQuery = "select id, name from rating_MPA";
        return jdbcTemplate.query(sqlQuery, this::makeRatingMPA);
    }

    public Optional<RatingMPA> findRatingMPAById(int ratingMPAId) {
        SqlRowSet ratingMPARows = jdbcTemplate.queryForRowSet("select * from rating_MPA where id = ?", ratingMPAId);

        if (ratingMPARows.next()) {
            RatingMPA ratingMPA = new RatingMPA(
                    ratingMPARows.getInt("id"),
                    ratingMPARows.getString("name")
            );
            return Optional.of(ratingMPA);
        } else {
            return Optional.empty();
        }
    }

    public RatingMPA createRatingMPA(RatingMPA ratingMPA) {
        String sqlQuery = "insert into rating_MPA (name) " +
                "values (?)";

        jdbcTemplate.update(sqlQuery,
                ratingMPA.getName());
        log.trace("Сохранен объект: {}", ratingMPA);
        return ratingMPA;
    }

    public RatingMPA updateRatingMPA(RatingMPA ratingMPA) {
        String sqlQuery = "update rating_MPA set " +
                "name = ?" + "where id = ?";

        jdbcTemplate.update(sqlQuery,
                ratingMPA.getName(),
                ratingMPA.getId());
        log.trace("Обновлен объект: {}.", ratingMPA);
        return ratingMPA;
    }

    public boolean deleteRatingMPAById(int id) {
        RatingMPA ratingMPA = findRatingMPAById(id).orElseThrow(() -> new RatingMPANotFoundException(id));
        String sqlQuery = "delete from rating_MPA where id = ?";

        log.trace("Удален объект: {}.", ratingMPA);
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private RatingMPA makeRatingMPA(ResultSet resultSet, int rowNum) throws SQLException {
        return new RatingMPA(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
