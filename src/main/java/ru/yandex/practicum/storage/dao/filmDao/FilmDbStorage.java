package ru.yandex.practicum.storage.dao.filmDao;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.FilmNotFoundException;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.models.Genre;
import ru.yandex.practicum.models.RatingMPA;
import ru.yandex.practicum.services.GenreService;
import ru.yandex.practicum.services.RatingMPAService;
import ru.yandex.practicum.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final RatingMPAService ratingMPAService;
    private final GenreService genreService;
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(RatingMPAService ratingMPAService, GenreService genreService, JdbcTemplate jdbcTemplate) {
        this.ratingMPAService = ratingMPAService;
        this.genreService = genreService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Film> findAllFilms() {
        String sqlQuery = "select * from films";
        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    @Override
    public Optional<Film> findFilmById(long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);
        return getFilm(filmRows);
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into films (name, description, duration, film_rating_MPA_id, release_date) " +
                "values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getReleaseDate());
        saveFilmGenres(film);
        log.trace("Сохранен объект: {}", film);

        return findFilmByName(film.getName()).orElseThrow(() -> new FilmNotFoundException(film.getId()));
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set " +
                "name = ?, description = ?, duration = ?, release_date = ?, film_rating_MPA_id = ? "
                + "where id = ?";

        ratingMPAService.findRatingMPAById(film.getMpa().getId());
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                film.getReleaseDate(),
                film.getMpa().getId(),
                film.getId());
        deleteFilmGenresFromTable(film);
        saveFilmGenres(film);
        log.trace("Обновлен объект: {}.", film);
        return findFilmById(film.getId()).orElseThrow(() -> new FilmNotFoundException(film.getId()));
    }

    @Override
    public boolean deleteFilmById(long id) {
        Film film = findFilmById(id).orElseThrow(() -> new FilmNotFoundException(id));
        String sqlQuery = "delete from films where id = ?";

        deleteFilmGenresFromTable(film);
        deleteFilmLikesFromTable(film);
        log.trace("Удален объект: {}.", film);
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    @Override
    public void saveLikeToTable(long filmId, long userId) {
        String sqlQuery = "insert into film_likes (film_id, user_id) " +
                "values (?, ?)";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteLikeFromTable (long filmId, long userId) {
        String sqlQuery = "delete from film_likes where film_id = ? and user_id = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        RatingMPA mpa = ratingMPAService.findRatingMPAById(resultSet.getInt("film_rating_MPA_id"));
        Film film = new Film(resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getString("description"), resultSet.getInt("duration"),
                resultSet.getDate("release_date").toLocalDate(), mpa);

        loadFilmGenres(film);
        loadFilmLikes(film);
        return film;
    }

    private void saveFilmGenres(Film film) {
        String sqlQuery = "insert into film_genres (film_id, genre_id) " +
                "values (?, ?)";
        Film newFilm = findFilmByName(film.getName()).orElseThrow(() -> new FilmNotFoundException(film.getId()));

        if (!film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                genreService.findGenreById(genre.getId());
                jdbcTemplate.update(sqlQuery, newFilm.getId(), genre.getId());
            }
        }
    }

    private void loadFilmGenres(Film film) {
        Collection<Integer> genresIds = getFilmGenresIdFromTable(film);

        if (!genresIds.isEmpty()) {
            for (Integer id : genresIds) {
                film.getGenres().add(genreService.findGenreById(id));
            }
        }
    }

    private Collection<Integer> getFilmGenresIdFromTable(Film film) {
        String sqlQuery = "select genre_id from film_genres where film_id = ?";
        return jdbcTemplate.query(sqlQuery, this::getGenreIdFromTableFilmGenres, film.getId());
    }

    private int getGenreIdFromTableFilmGenres(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("genre_id");
    }

    private void loadFilmLikes(Film film) {
        Collection<Long> filmLikes = getFilmLikesFromTable(film);

        if (!filmLikes.isEmpty()) {
            for (Long id : filmLikes) {
                film.getLikes().add(id);
            }
        }
    }

    private Collection<Long> getFilmLikesFromTable(Film film) {
        String sqlQuery = "select user_id from film_likes where film_id = ?";
        return jdbcTemplate.query(sqlQuery, this::getUserIdFromTableFilmLikes, film.getId());
    }

    private long getUserIdFromTableFilmLikes(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getLong("user_id");
    }

    private void deleteFilmLikesFromTable(Film film) {
        String sqlQuery = "delete from film_likes where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    private void deleteFilmGenresFromTable(Film film) {
        String sqlQuery = "delete from film_genres where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    private Optional<Film> findFilmByName(String name) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films where name = ?", name);
        return getFilm(filmRows);
    }

    @NotNull
    private Optional<Film> getFilm(SqlRowSet filmRows) {
        if(filmRows.next()) {
            Film film = new Film(
                    filmRows.getLong("id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    filmRows.getInt("duration"),
                    Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate(),
                    ratingMPAService.findRatingMPAById(filmRows.getInt("film_rating_MPA_id")));

            loadFilmLikes(film);
            loadFilmGenres(film);
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }
}