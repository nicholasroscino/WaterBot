package com.ncoding.infrastructure;

import com.ncoding.core.models.User;
import com.ncoding.core.models.UserId;
import com.ncoding.core.ports.UserRepository;

import java.util.HashSet;

public class InMemoryWaterBotRepository implements UserRepository {
    private final HashSet<User> users = new HashSet<>();

    @Override
    public void register(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        users.removeIf(curr -> curr.getUserId() == user.getUserId());
        users.add(user);
    }

    @Override
    public User getOne(UserId userId) {
        return users.stream()
                .filter(curr -> curr.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public HashSet<User> getAll() {
        return users;
    }
}
