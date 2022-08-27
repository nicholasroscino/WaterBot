package com.ncoding.ui;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotClient;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TelegramMessageAdapterUnitTest {

    @Test
    public void toTelegramMessage() {
        TelegramMessageAdapter adapter = new TelegramMessageAdapter();
        var msg = new SendMessage();
        msg.setText("msg");
        msg.setChatId(1L);

        var sendMessage = adapter.toTelegramMessage(TestUtils.buildWaterBotMessage("TG-1", "msg"));

        assertThat(msg,is(equalTo(sendMessage)));
    }

    @Test
    public void toWaterBotMessage() {
        TelegramMessageAdapter adapter = new TelegramMessageAdapter();
        var msg = new Message();
        msg.setText("msg");
        var chat = new Chat();
        chat.setId(1L);
        msg.setChat(chat);

        var waterBotMessage = adapter.toWaterBotMessage(msg);

        assertThat(waterBotMessage, is(equalTo(TestUtils.buildWaterBotMessage("TG-1", "msg"))));
    }

    private Update buildTestUpdate(WaterBotMessage wbMsg) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        chat.setId(Long.parseLong(wbMsg.getUserId().getValue()));
        message.setChat(chat);
        message.setText(wbMsg.getMessage());

        update.setMessage(message);

        return update;
    }

}
