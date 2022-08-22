package com.ncoding.core.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class WaterBotMessage {
    private UserId userId;
    private String message;
}
