package com.ncoding.core.actions;

import com.ncoding.core.models.User;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.ports.MessagePicker;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class DrinkAlertAction implements Action {
    private final IWaterBotGateway waterBotGateway;
    private final MessagePicker messagePicker;
    private final UserRepository repository;
    private final List<Integer> triggerTimes;
    private final Clock clock;

    public void execute() {
        HashSet<User> users = repository.getAll();

        users.forEach(curr -> {
            int hour = clock.getCurrentTimeZoneHour(curr.getOffset());

            if(triggerTimes.contains(hour)) {
                var message = messagePicker.getMessage();
                var wbMessage = new WaterBotMessageResponse(curr.getUserId(), message);
                this.waterBotGateway.sendMessage(wbMessage);
            }
        });
    }
}
