package com.ncoding.ui;

import com.ncoding.core.actions.BanAction;
import com.ncoding.core.actions.UnbanAction;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TelegramMessageAdapter {
    public WaterBotMessage toWaterBotMessage(Message msg) {
        var chat = msg.getChat();

        return new WaterBotMessage(
                new UserId(TelegramBot.CLIENT_PREFIX + "-" + msg.getChatId().toString()),
                chat.getFirstName(),
                chat.getUserName(),
                msg.getText()
        );
    }

    public WaterBotMessage toWaterBotMessage(ChatMemberUpdated chatMemberUpdated) {
        var user = chatMemberUpdated.getFrom();
        var value = chatMemberUpdated.getNewChatMember().getStatus();

        return new WaterBotMessage(
                new UserId(TelegramBot.CLIENT_PREFIX + "-" + user.getId()),
                user.getFirstName(),
                user.getUserName(),
                value.equals("kicked") ? BanAction.CODE : UnbanAction.CODE
        );
    }



    public SendMessage toTelegramMessage(WaterBotMessageResponse msg) {
        SendMessage message = new SendMessage();

        var id = msg.getUserId().getValue().substring(3);

        message.setChatId(Long.parseLong(id));
        message.setText(msg.getMessage());

        return message;
    }
}
