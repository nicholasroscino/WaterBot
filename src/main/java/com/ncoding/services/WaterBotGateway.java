package com.ncoding.services;

import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.ui.TelegramBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WaterBotGateway implements IWaterBotGateway {
    private TelegramBot bot;
    private IActionFactory actionFactory;

    @Override
    public void sendMessage(WaterBotMessage message) {
        bot.sendMessage(message.getUserId().getValue(), message.getMessage());
    }

    @Override
    public void onUpdates(WaterBotMessage message) {
        var action = this.actionFactory.createAction(message, this);
        if(action != null) action.execute();
    }
}
