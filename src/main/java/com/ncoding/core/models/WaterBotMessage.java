package com.ncoding.core.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class WaterBotMessage {
    private UserId userId;
    private String userName;
    private String userTag;
    private String message;
}
