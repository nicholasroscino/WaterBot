package com.ncoding.core.ports;

import com.ncoding.core.models.User;
import com.ncoding.core.models.UserId;

import java.util.HashSet;

public interface UserRepository {
    void register(User user);
    void update(User user);
    User getOne(UserId userId);
    HashSet<User> getAll();
}
