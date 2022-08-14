package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.RatingMPANotFoundException;
import ru.yandex.practicum.models.RatingMPA;
import ru.yandex.practicum.storage.dao.ratingMPADao.RatingMPADao;

import java.util.Collection;

@Service
public class RatingMPAService {
    private final RatingMPADao ratingMPADao;

    @Autowired
    public RatingMPAService(RatingMPADao ratingMPADao) {
        this.ratingMPADao = ratingMPADao;
    }

    public Collection<RatingMPA> findAllRatingsMPA() {
        return ratingMPADao.findAllRatingsMPA();
    }

    public RatingMPA findRatingMPAById(int ratingMPAId) {
        return ratingMPADao.findRatingMPAById(ratingMPAId)
                .orElseThrow(() -> new RatingMPANotFoundException(ratingMPAId));
    }

    public RatingMPA createRatingMPA(RatingMPA ratingMPA) {
        return ratingMPADao.createRatingMPA(ratingMPA);
    }

    public RatingMPA updateRatingMPA(RatingMPA ratingMPA) {
        findRatingMPAById(ratingMPA.getId());
        return ratingMPADao.updateRatingMPA(ratingMPA);
    }

    public boolean deleteRatingMPAById(int id) {
        return ratingMPADao.deleteRatingMPAById(id);
    }
}