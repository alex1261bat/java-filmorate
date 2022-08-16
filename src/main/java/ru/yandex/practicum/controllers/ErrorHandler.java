package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.exceptions.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(final ValidationException validationException) {
        return validationException.getMessage();
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAnnotationValidationException(final RuntimeException runtimeException) {
        return runtimeException.getMessage();
    }

    @ExceptionHandler({FilmNotFoundException.class, UserNotFoundException.class, FriendStatusNotFoundException.class,
        GenreNotFoundException.class, RatingMPANotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFilmUserNotFoundException(final RuntimeException runtimeException) {
        return runtimeException.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleThrowable(final Throwable throwable) {
        return "Произошла непредвиденная ошибка.";
    }
}