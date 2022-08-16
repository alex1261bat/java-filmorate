package ru.yandex.practicum.storage.dao.filmDao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.models.RatingMPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;

    @Test
    void findAllFilms() {
        filmDbStorage.createFilm(new Film(0, "name1", "111", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));
        filmDbStorage.createFilm(new Film(0, "name2", "222", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));

        ArrayList<Film> films = (ArrayList<Film>) filmDbStorage.findAllFilms();

        assertThat(films)
                .isNotEmpty()
                .hasSize(6);
    }

    @Test
    void findFilmById() {
        filmDbStorage.createFilm(new Film(0, "name1", "111", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));

        Optional<Film> filmOptional = filmDbStorage.findFilmById(1);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    void createFilm() {
        filmDbStorage.createFilm(new Film(0, "name3", "111", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));

        Optional<Film> filmOptional = filmDbStorage.findFilmById(3);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "name3")
                );
    }

    @Test
    void updateFilm() {
        filmDbStorage.createFilm(new Film(0, "name1", "111", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));

        Optional<Film> filmOptional = filmDbStorage.findFilmById(2);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 2L)
                );

        filmDbStorage.updateFilm(new Film(filmOptional.get().getId(), "name2", "111", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));

        Optional<Film> filmOptional1 = filmDbStorage.findFilmById(2);

        assertThat(filmOptional1)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "name2")
                );
    }

    @Test
    void deleteFilmById() {
        filmDbStorage.createFilm(new Film(0, "name1", "111", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));
        filmDbStorage.createFilm(new Film(0, "name2", "222", 10,
                LocalDate.of(2000, 11, 11), new RatingMPA(1)));

        ArrayList<Film> films = (ArrayList<Film>) filmDbStorage.findAllFilms();

        assertThat(films)
                .isNotEmpty()
                .hasSize(5);

        filmDbStorage.deleteFilmById(1);

        ArrayList<Film> films1 = (ArrayList<Film>) filmDbStorage.findAllFilms();

        assertThat(films1)
                .isNotEmpty()
                .hasSize(4);
    }
}