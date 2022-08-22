package com.ncoding.core.ports;

import com.ncoding.core.models.UserId;

import java.util.HashSet;

public interface WaterBotRepository {
    void registerUser(UserId userId);
    HashSet<UserId> getUsers();
}
