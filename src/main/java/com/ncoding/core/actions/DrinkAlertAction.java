package com.ncoding.core.actions;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.services.IWaterBotGateway;
import com.ncoding.core.ports.MessagePicker;
import lombok.AllArgsConstructor;

import java.util.HashSet;

@AllArgsConstructor
public class DrinkAlertAction implements Action {
    private final IWaterBotGateway waterBotGateway;
    private final MessagePicker messagePicker;
    private final WaterBotRepository repository;

    public void execute() {
        var message = messagePicker.getMessage();

        HashSet<UserId> users = repository.getUsers();

        users.forEach(curr -> {
            var wbMessage = new WaterBotMessage(curr, message);
            this.waterBotGateway.sendMessage(wbMessage);
        });

    }
}
