package com.ncoding.core.ports;

import com.ncoding.core.services.IWaterBotGateway;
import com.ncoding.core.models.WaterBotMessageResponse;

public interface WaterBotClient {
    boolean canHandle(WaterBotMessageResponse message);
    void sendMessage(WaterBotMessageResponse message);
    void setGateway(IWaterBotGateway waterBotGateway);
}
