package com.ncoding.core.actions;

import com.ncoding.core.models.User;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.core.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

import java.time.ZoneOffset;

@AllArgsConstructor
public class RegisterUserAction implements Action {
    public static final String CODE = "/start";
    private WaterBotMessage message;
    private IWaterBotGateway gateway;
    private UserRepository repository;

    @Override
    public void execute() {
        var user = new User(this.message.getUserId(),this.message.getUserName(), this.message.getUserTag(), ZoneOffset.UTC);
        var existAlready = this.repository.getOne(this.message.getUserId());

        WaterBotMessageResponse retMessage;

        if(existAlready == null) {
            this.repository.register(user);
            retMessage = new WaterBotMessageResponse(this.message.getUserId(), "User registered successfully");
        } else {
            retMessage = new WaterBotMessageResponse(this.message.getUserId(), "User already registered");
        }

        this.gateway.sendMessage(retMessage);
    }
}
