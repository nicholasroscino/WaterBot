package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.core.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

import java.time.ZoneOffset;

@AllArgsConstructor
public class BanAction implements Action {
    public static final String CODE = "/ban";

    private final UserRepository repository;
    private final WaterBotMessage message;

    @Override
    public void execute() {
        var userId = message.getUserId();
        System.out.println("Do I get here?");
        var usr = this.repository.getOne(userId);

        if(usr != null) {
            System.out.println("Oh yess!!");
            usr.setHasBlockedBot(true);
            this.repository.update(usr);
        }
    }
}
