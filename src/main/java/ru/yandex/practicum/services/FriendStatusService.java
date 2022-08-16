package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.FriendStatusNotFoundException;
import ru.yandex.practicum.models.FriendStatus;
import ru.yandex.practicum.storage.dao.friendStatusDao.FriendStatusDao;

import java.util.Collection;

@Service
public class FriendStatusService {
    private final FriendStatusDao friendStatusDao;

    @Autowired
    public FriendStatusService(FriendStatusDao friendStatusDao) {
        this.friendStatusDao = friendStatusDao;
    }

    public Collection<FriendStatus> findAllFriendStatus() {
        return friendStatusDao.findAllFriendStatus();
    }

    public FriendStatus findFriendStatusById(int friendStatusId) {
        return friendStatusDao.findFriendStatusById(friendStatusId)
                .orElseThrow(() -> new FriendStatusNotFoundException(friendStatusId));
    }

    public FriendStatus createFriendStatus(FriendStatus friendStatus) {
        return friendStatusDao.createFriendStatus(friendStatus);
    }

    public FriendStatus updateFriendStatus(FriendStatus friendStatus) {
        findFriendStatusById(friendStatus.getId());
        return friendStatusDao.updateFriendStatus(friendStatus);
    }

    public boolean deleteFriendStatusById(int id) {
        return friendStatusDao.deleteFriendStatusById(id);
    }
}