package com.ncoding.utils;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;

public class TestUtils {
    public static WaterBotMessage buildWaterBotMessage(String id, String nick, String tag, String message) {
        return new WaterBotMessage(new UserId(id), nick, tag, message);
    }

    public static WaterBotMessageResponse buildWaterBotMessageResponse(String id, String message) {
        return new WaterBotMessageResponse(new UserId(id), message);
    }
}
