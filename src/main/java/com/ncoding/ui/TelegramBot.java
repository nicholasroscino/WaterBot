package com.ncoding.ui;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.UserId;
import com.ncoding.services.IWaterBotGateway;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private final String token;
    private final String username;
    private IWaterBotGateway gateway;

    public TelegramBot(String token, String username) {
        this.token = token;
        this.username = username;
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();

        if(this.gateway == null) {
            System.out.println("INFO: No gateway set");
            return;
        }

        this.gateway.onUpdates(new WaterBotMessage(UserId.fromLong(msg.getChatId()), msg.getText()));
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage msg = new SendMessage();

        msg.setChatId(chatId);
        msg.setText(message);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setGateway(IWaterBotGateway gateway) {
        this.gateway = gateway;
    }
}

