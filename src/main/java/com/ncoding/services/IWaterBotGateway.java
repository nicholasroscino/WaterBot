package com.ncoding.services;

import com.ncoding.core.models.WaterBotMessage;

public interface IWaterBotGateway {
    void sendMessage(WaterBotMessage message);
    void onUpdates(WaterBotMessage message);
}
