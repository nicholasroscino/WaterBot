package com.ncoding.core.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Report {
    private UserId userId;
    private String message;
    private String timestamp;
}
