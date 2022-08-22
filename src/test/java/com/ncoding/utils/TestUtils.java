package com.ncoding.utils;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;

public class TestUtils {
    public static WaterBotMessage buildWaterBotMessage(Long id, String message) {
        return new WaterBotMessage(UserId.fromLong(id), message);
    }
}
