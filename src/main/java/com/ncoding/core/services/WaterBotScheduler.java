package com.ncoding.core.services;

import com.ncoding.core.actions.Action;
import com.ncoding.core.actions.DrinkAlertAction;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.JobScheduler;
import com.ncoding.core.ports.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Set;


@AllArgsConstructor
public class WaterBotScheduler implements IWaterBotScheduler {
    private final JobScheduler jobScheduler;
    private final IWaterBotGateway waterBotGateway;
    private final MessagePicker messagePicker;
    private final UserRepository repository;
    private final Set<Integer> triggerTimes;
    private final Clock clock;

    public void runScheduler() {
        Action action = new DrinkAlertAction(waterBotGateway, messagePicker, repository, triggerTimes, clock);

        jobScheduler.scheduleEveryHour(action::execute);
    }
}
