package com.ncoding.com.services;

import com.ncoding.core.actions.Action;
import com.ncoding.core.actions.DrinkAlertAction;
import com.ncoding.core.ports.JobScheduler;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.WaterBotRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WaterBotScheduler implements IWaterBotScheduler {
    private final JobScheduler jobScheduler;
    private final IWaterBotGateway waterBotGateway;
    private final MessagePicker messagePicker;
    private final WaterBotRepository repository;
    private final List<Integer> triggerTimes;

    public void runScheduler() {
        Action action = new DrinkAlertAction(waterBotGateway, messagePicker, repository, triggerTimes);

        jobScheduler.scheduleEveryHour(action::execute);
    }
}
