package com.ncoding.core.actions;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.ports.MessagePicker;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class DrinkAlertAction implements Action {
    private final IWaterBotGateway waterBotGateway;
    private final MessagePicker messagePicker;
    private final WaterBotRepository repository;
    private final List<Integer> triggerTimes;

    public void execute() {
        Instant i = Instant.now();
        var utcOffset = i.atOffset(ZoneOffset.UTC);

        if(triggerTimes.contains(utcOffset.getHour())) {
            var message = messagePicker.getMessage();

            HashSet<UserId> users = repository.getUsers();

            users.forEach(curr -> {
                var wbMessage = new WaterBotMessage(curr, message);
                this.waterBotGateway.sendMessage(wbMessage);
            });
        }
    }
}
