package com.ncoding.utils;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;

public class TestUtils {
    public static WaterBotMessage buildWaterBotMessage(String id, String message) {
        return new WaterBotMessage(new UserId(id), message);
    }
}
