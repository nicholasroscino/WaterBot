package com.ncoding.ui;

import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.WaterBotClient;
import com.ncoding.core.services.IWaterBotGateway;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot implements WaterBotClient {
    private final String token;
    private final String username;
    private final TelegramMessageAdapter messageAdapter;
    private IWaterBotGateway gateway;
    public final static String CLIENT_PREFIX = "TG";

    public TelegramBot(String token, String username, TelegramMessageAdapter messageAdapter) {
        this.token = token;
        this.username = username;
        this.messageAdapter = messageAdapter;
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
        if(msg != null) {
            var wbMessage = this.messageAdapter.toWaterBotMessage(msg);
            this.gateway.onUpdates(wbMessage);
        } else {
            var wbMessage = this.messageAdapter.toWaterBotMessage(update.getMyChatMember());
            this.gateway.onChatMemberUpdated(wbMessage);
        }
    }

    public void sendMessage(WaterBotMessageResponse message) {
        var msg = this.messageAdapter.toTelegramMessage(message);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean canHandle(WaterBotMessageResponse message) {
        var client = message.getUserId().getValue().split("-");

        return client[0].equals(CLIENT_PREFIX);
    }

    public void setGateway(IWaterBotGateway gateway) {
        this.gateway = gateway;
    }
}

