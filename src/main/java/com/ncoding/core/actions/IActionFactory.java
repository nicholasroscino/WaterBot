package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.com.services.IWaterBotGateway;

public interface IActionFactory {
    Action createAction(WaterBotMessage message, IWaterBotGateway gateway);
}
