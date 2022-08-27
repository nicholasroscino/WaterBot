package com.ncoding.services;

import com.ncoding.core.actions.Action;
import com.ncoding.core.actions.DrinkAlertAction;
import com.ncoding.core.ports.JobScheduler;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.WaterBotRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WaterBotScheduler implements IWaterBotScheduler {
    private JobScheduler jobScheduler;
    private IWaterBotGateway waterBotGateway;
    private MessagePicker messagePicker;
    private WaterBotRepository repository;

    public void runScheduler() {
        Action action = new DrinkAlertAction(waterBotGateway, messagePicker, repository);

        jobScheduler.scheduleEveryHour(action::execute);
    }
}
