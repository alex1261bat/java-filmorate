package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.models.Genre;
import ru.yandex.practicum.services.GenreService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Collection<Genre> findAllGenres() {
        return genreService.findAllGenres();
    }

    @GetMapping("/{id}")
    public Genre findGenreById(@PathVariable int id) {
        return genreService.findGenreById(id);
    }

    @PostMapping
    public Genre createGenre(@Valid @RequestBody Genre genre) {
        return genreService.createGenre(genre);
    }

    @PutMapping
    public Genre updateGenre(@Valid @RequestBody Genre genre) {
        return genreService.updateGenre(genre);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGenreById(@PathVariable int id) {
        return genreService.deleteGenreById(id);
    }
}