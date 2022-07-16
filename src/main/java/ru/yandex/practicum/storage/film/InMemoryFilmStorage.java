package ru.yandex.practicum.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private static final LocalDate FIRST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAllFilms() { // метод получения списка всех фильмов
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findFilmById(long id) { // метод получения фильма по id
        if (!films.containsKey(id)) {
            log.warn("Такой фильм отсутствует.");
            throw new NoSuchElementException("Такой фильм отсутствует.");
        }
        return films.get(id);
    }

    @Override
    public Film createFilm(Film film) { // метод создания фильма
        validateFilm(film);
        log.trace("Сохранен объект: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) { // метод обновления фильма
        if (!films.containsKey(film.getId())) {
            log.warn("Такой фильм отсутствует.");
            throw new NoSuchElementException("Такой фильм отсутствует.");
        }

        validateFilm(film);
        log.trace("Сохранен объект: {}.", film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film deleteFilmById(long id) { // метод удаления фильма по id
        if (!films.containsKey(id)) {
            log.warn("Такой фильм отсутствует.");
            throw new NoSuchElementException("Такой фильм отсутствует.");
        }

        Film film = films.get(id);
        log.trace("Удален объект: {}.", film);
        films.remove(id);
        return film;
    }

    @Override
    public void validateFilm(Film film) { // метод валидации фильма
        if (film.getName() == null || film.getName().equals("")) {
            log.warn("Название фильма не может быть пустым.");
            throw new ValidationException("Название фильма не может быть пустым.");
        } else if (film.getDescription() == null || film.getDescription().length() > 200) {
            log.warn("Длина описания должна быть не боле 200 символов и не должна быть null.");
            throw new ValidationException("Длина описания должна быть не боле 200 символов и не должна быть null.");
        } else if (film.getReleaseDate().isBefore(FIRST_RELEASE_DATE)) {
            log.warn("Дата релиза должна быть не ранее 28 декабря 1985 года.");
            throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1985 года.");
        } else if (film.getDuration() < 1) {
            log.warn("Продолжительность фильма должна быть положительной.");
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }  else if (film.getId() < 1) {
            log.warn("Неверный id.");
            throw new ValidationException("Неверный id.");
        }
    }
}