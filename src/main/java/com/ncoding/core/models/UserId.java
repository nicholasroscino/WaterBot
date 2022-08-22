package com.ncoding.core.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserId {
    private final Long value;

    private UserId(Long value) {
        this.value = value;
    }

    public static UserId fromLong(Long value) {
        return new UserId(value);
    }
}
