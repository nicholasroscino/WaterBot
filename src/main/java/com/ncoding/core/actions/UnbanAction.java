package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.core.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

import java.time.ZoneOffset;

@AllArgsConstructor
public class UnbanAction implements Action {
    public static final String CODE = "/unban";

    private final UserRepository repository;
    private final WaterBotMessage message;

    @Override
    public void execute() {
        var userId = message.getUserId();

        var usr = this.repository.getOne(userId);

        if(usr != null) {
            usr.setHasBlockedBot(false);
            this.repository.update(usr);
        }
    }
}
