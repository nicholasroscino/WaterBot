package com.ncoding.com.services;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;

public interface IWaterBotGateway {
    void sendMessage(WaterBotMessageResponse message);
    void onUpdates(WaterBotMessage message);
}
