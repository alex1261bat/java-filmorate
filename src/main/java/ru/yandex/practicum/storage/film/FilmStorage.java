package ru.yandex.practicum.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.models.Film;

import java.util.Collection;

@Component
public interface FilmStorage {

    Collection<Film> findAllFilms(); // метод получения списка всех фильмов

    Film findFilmById(long id); // метод получения фильма по id

    Film createFilm(Film film); // метод создания фильма

    Film updateFilm(Film film); // метод обновления фильма

    Film deleteFilmById(long id); // метод удаления фильма по id

    void validateFilm(Film film); // метод валидации фильма
}