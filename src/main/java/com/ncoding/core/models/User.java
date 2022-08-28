package com.ncoding.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneOffset;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private UserId userId;
    private String name;
    private String tag;
    private ZoneOffset offset;
}
