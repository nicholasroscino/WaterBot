package com.ncoding.infrastructure;

import com.ncoding.core.models.User;
import com.ncoding.core.ports.UserRepository;

import java.util.HashSet;

public class InMemoryWaterBotRepository implements UserRepository {
    private final HashSet<User> userIds = new HashSet<>();

    @Override
    public void register(User userId) {
        userIds.add(userId);
    }

    @Override
    public HashSet<User> getAll() {
        return userIds;
    }
}
