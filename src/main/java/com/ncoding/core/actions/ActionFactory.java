package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActionFactory implements IActionFactory {
    private WaterBotRepository repository;

    public Action createAction(WaterBotMessage waterBotMessage, IWaterBotGateway waterBotGateway) {
        return switch (waterBotMessage.getMessage()) {
            case EchoAction.CODE -> new EchoAction(waterBotGateway,waterBotMessage);
            case RegisterUserAction.CODE -> new RegisterUserAction(waterBotMessage, waterBotGateway, repository);
            default -> null;
        };
    }
}
