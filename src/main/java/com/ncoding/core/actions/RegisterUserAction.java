package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterUserAction implements Action {
    public static final String CODE = "/start";
    private WaterBotMessage message;
    private IWaterBotGateway gateway;
    private WaterBotRepository repository;

    @Override
    public void execute() {
        this.repository.registerUser(this.message.getUserId());

        var retMessage = new WaterBotMessage(this.message.getUserId(), "User registered successfully");

        this.gateway.sendMessage(retMessage);
    }
}
