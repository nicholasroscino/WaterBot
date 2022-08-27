package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.com.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EchoAction implements Action {
    public static final String CODE = "/echo";

    private final IWaterBotGateway waterBotGateway;
    private final WaterBotMessage receivedMessage;

    @Override
    public void execute() {
        this.waterBotGateway.sendMessage(receivedMessage);
    }
}
