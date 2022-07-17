package ru.yandex.practicum.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.models.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAllFilms() { // метод получения списка всех фильмов
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> findFilmById(long id) { // метод получения фильма по id
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film createFilm(Film film) { // метод создания фильма
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) { // метод обновления фильма
        log.trace("Сохранен объект: {}.", film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film deleteFilmById(long id) { // метод удаления фильма по id
        Film film = films.get(id);

        log.trace("Удален объект: {}.", film);
        films.remove(id);
        return film;
    }
}