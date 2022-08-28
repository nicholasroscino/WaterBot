package com.ncoding.core.models;

import lombok.*;

import java.time.ZoneOffset;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private UserId userId;
    private String name;
    private String tag;
    private ZoneOffset offset;
}
