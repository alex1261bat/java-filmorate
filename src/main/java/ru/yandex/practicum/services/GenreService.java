package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.GenreNotFoundException;
import ru.yandex.practicum.models.Genre;
import ru.yandex.practicum.storage.dao.genreDao.GenreDao;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Collection<Genre> findAllGenres() {
        return genreDao.findAllGenres();
    }

    public Genre findGenreById(int genreId) {
        return genreDao.findGenreById(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    public Genre createGenre(Genre genre) {
        return genreDao.createGenre(genre);
    }

    public Genre updateGenre(Genre genre) {
        findGenreById(genre.getId());
        return genreDao.updateGenre(genre);
    }

    public boolean deleteGenreById(int id) {
        return genreDao.deleteGenreById(id);
    }
}