package com.ncoding.core.actions;

import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.models.Report;
import com.ncoding.core.models.User;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import com.ncoding.core.ports.UserRepository;
import lombok.AllArgsConstructor;

import java.time.ZoneOffset;

@AllArgsConstructor
public class SetTimeZoneAction implements Action {
    public static final String CODE = "/timezone";

    private final UserRepository repository;
    private final WaterBotMessage message;
    private final IWaterBotGateway gateway;

    @Override
    public void execute() {
        var timezone = message.getMessage().substring(CODE.length()).trim();

        if (!timezone.matches("(([+\\-])[0-9]([0-3])?)|Z")) {
            gateway.sendMessage(new WaterBotMessageResponse(message.getUserId(),
                    "Wrong Timezone. /help to receive tips."));
        } else {
            ZoneOffset zoneOffset = ZoneOffset.of(timezone);

            var user = repository.getOne(message.getUserId());

            user.setOffset(zoneOffset);
            repository.update(user);

            gateway.sendMessage(new WaterBotMessageResponse(message.getUserId(),
                    "The timezone has been updated"));
        }
    }
}
