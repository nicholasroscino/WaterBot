package com.ncoding.ui;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TelegramMessageAdapter {
    WaterBotMessage toWaterBotMessage(Message msg) {
        return new WaterBotMessage(new UserId(TelegramBot.CLIENT_PREFIX + "-" + msg.getChatId().toString()), msg.getText());
    }

    SendMessage toTelegramMessage(WaterBotMessage msg) {
        SendMessage message = new SendMessage();

        var id = msg.getUserId().getValue().substring(3);

        message.setChatId(Long.parseLong(id));
        message.setText(msg.getMessage());

        return message;
    }
}
