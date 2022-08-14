package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.models.RatingMPA;
import ru.yandex.practicum.services.RatingMPAService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/mpa")
public class RatingMpaController {
    private final RatingMPAService ratingMPAService;

    @Autowired
    public RatingMpaController(RatingMPAService ratingMPAService) {
        this.ratingMPAService = ratingMPAService;
    }

    @GetMapping
    public Collection<RatingMPA> findAllRatingsMPA() {
        return ratingMPAService.findAllRatingsMPA();
    }

    @GetMapping("/{id}")
    public RatingMPA findRatingMPAById(@PathVariable int id) {
        return ratingMPAService.findRatingMPAById(id);
    }

    @PostMapping
    public RatingMPA createRatingMPA(@Valid @RequestBody RatingMPA ratingMPA) {
        return ratingMPAService.createRatingMPA(ratingMPA);
    }

    @PutMapping
    public RatingMPA updateRatingMPA(@Valid @RequestBody RatingMPA ratingMPA) {
        return ratingMPAService.updateRatingMPA(ratingMPA);
    }

    @DeleteMapping("/{id}")
    public boolean deleteRatingMPAById(@PathVariable int id) {
        return ratingMPAService.deleteRatingMPAById(id);
    }
}