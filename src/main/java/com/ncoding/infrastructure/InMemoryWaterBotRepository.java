package com.ncoding.infrastructure;

import com.ncoding.core.models.UserId;
import com.ncoding.core.ports.WaterBotRepository;

import java.util.HashSet;

public class InMemoryWaterBotRepository implements WaterBotRepository {
    private final HashSet<UserId> userIds = new HashSet<>();

    @Override
    public void registerUser(UserId userId) {
        userIds.add(userId);
    }

    @Override
    public HashSet<UserId> getUsers() {
        return userIds;
    }
}
