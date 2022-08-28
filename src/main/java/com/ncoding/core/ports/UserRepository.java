package com.ncoding.core.ports;

import com.ncoding.core.models.User;

import java.util.HashSet;

public interface UserRepository {
    void register(User userId);
    HashSet<User> getAll();
}
