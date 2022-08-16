package ru.yandex.practicum.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RatingMPA {
    private int id;
    @NotBlank
    private String name;

    public RatingMPA(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonCreator
    public RatingMPA(int id) {
        this.id = id;
    }

    public RatingMPA() {}
}
