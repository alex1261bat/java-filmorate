package ru.yandex.practicum.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FriendStatus {
    private int id;
    @NotBlank
    private String name;

    public FriendStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}