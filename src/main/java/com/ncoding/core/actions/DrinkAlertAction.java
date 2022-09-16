package com.ncoding.core.actions;

import com.ncoding.core.models.User;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.core.services.IWaterBotGateway;
import com.ncoding.core.services.MessagePicker;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class DrinkAlertAction implements Action {
    private final IWaterBotGateway waterBotGateway;
    private final MessagePicker messagePicker;
    private final UserRepository repository;
    private final Set<Integer> triggerTimes;
    private final Clock clock;

    public void execute() {
        HashSet<User> users = repository.getAll();

        users.stream()
                .filter(curr -> !curr.isHasBlockedBot())
                .forEach(curr -> {
            int hour = clock.getCurrentTimeZoneHour(curr.getOffset());

            if(triggerTimes.contains(hour)) {
                var message = messagePicker.getMessage(hour);
                var wbMessage = new WaterBotMessageResponse(curr.getUserId(), message);
                this.waterBotGateway.sendMessage(wbMessage);
            }
        });
    }
}
