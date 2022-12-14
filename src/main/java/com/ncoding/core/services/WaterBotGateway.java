package com.ncoding.core.services;

import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.WaterBotClient;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WaterBotGateway implements IWaterBotGateway {
    private List<WaterBotClient> bots;
    private IActionFactory actionFactory;

    @Override
    public void sendMessage(WaterBotMessageResponse message) {
        bots.stream()
                .filter(curr -> curr.canHandle(message))
                .findFirst()
                .ifPresentOrElse(waterBotClient -> waterBotClient.sendMessage(message),
                        () -> System.out.println("Error while finding the correct client to send the message with")
                );
    }

    @Override
    public void onUpdates(WaterBotMessage message) {
        var action = this.actionFactory.createAction(message, this);
        if (action != null) action.execute();
    }

    public void onChatMemberUpdated(WaterBotMessage message) {
        var action = this.actionFactory.createChatMemberUpdatedAction(message);
        if(action != null) action.execute();
    }
}
