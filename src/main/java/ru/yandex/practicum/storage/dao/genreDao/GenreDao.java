package ru.yandex.practicum.storage.dao.genreDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.GenreNotFoundException;
import ru.yandex.practicum.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component("genreDao")
@Slf4j
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Genre> findAllGenres() {
        String sqlQuery = "select id, name from genres";
        return jdbcTemplate.query(sqlQuery, this::makeGenres);
    }

    public Optional<Genre> findGenreById(int genreId) {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genres where id = ?", genreId);

        if (genreRows.next()) {
            Genre genre = new Genre(
                    genreRows.getInt("id"),
                    genreRows.getString("name")
            );
            return Optional.of(genre);
        } else {
            return Optional.empty();
        }
    }

    public Genre createGenre(Genre genre) {
        String sqlQuery = "insert into genres (name) " +
                "values (?)";
        jdbcTemplate.update(sqlQuery,
                genre.getName());
        log.trace("Сохранен объект: {}", genre);
        return genre;
    }

    public Genre updateGenre(Genre genre) {
        String sqlQuery = "update genres set " +
                "name = ?" + "where id = ?";

        jdbcTemplate.update(sqlQuery,
                genre.getName(),
                genre.getId());
        log.trace("Обновлен объект: {}.", genre);
        return genre;
    }

    public boolean deleteGenreById(int id) {
        Genre genre = findGenreById(id).orElseThrow(() -> new GenreNotFoundException(id));
        String sqlQuery = "delete from genres where id = ?";

        log.trace("Удален объект: {}.", genre);
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private Genre makeGenres(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(resultSet.getInt("id"), resultSet.getString("name"));
    }
}