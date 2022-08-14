package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.models.FriendStatus;
import ru.yandex.practicum.services.FriendStatusService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/friendStatus")
public class FriendStatusController {
    private final FriendStatusService friendStatusService;

    @Autowired
    public FriendStatusController(FriendStatusService friendStatusService) {
        this.friendStatusService = friendStatusService;
    }

    @GetMapping
    public Collection<FriendStatus> findAllFriendStatus() {
        return friendStatusService.findAllFriendStatus();
    }

    @GetMapping("/{friendStatusId}")
    public FriendStatus findFriendStatusById(@PathVariable int friendStatusId) {
        return friendStatusService.findFriendStatusById(friendStatusId);
    }

    @PostMapping
    public FriendStatus createFriendStatus(@Valid @RequestBody FriendStatus friendStatus) {
        return friendStatusService.createFriendStatus(friendStatus);
    }

    @PutMapping
    public FriendStatus updateFriendStatus(@Valid @RequestBody FriendStatus friendStatus) {
        return friendStatusService.updateFriendStatus(friendStatus);
    }

    @DeleteMapping("/{friendStatusId}")
    public boolean deleteFriendStatusById(@PathVariable int friendStatusId) {
        return friendStatusService.deleteFriendStatusById(friendStatusId);
    }
}