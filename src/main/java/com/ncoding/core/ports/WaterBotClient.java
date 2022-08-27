package com.ncoding.core.ports;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.services.IWaterBotGateway;
import com.ncoding.services.WaterBotGateway;

public interface WaterBotClient {
    boolean canHandle(WaterBotMessage message);
    void sendMessage(WaterBotMessage message);
    void setGateway(IWaterBotGateway waterBotGateway);
}
