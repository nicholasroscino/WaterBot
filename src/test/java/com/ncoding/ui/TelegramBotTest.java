package com.ncoding.ui;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.services.WaterBotGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TelegramBotTest {

    @Test
    public void updateMessageGetsDeliveredCorrectly(){
        TelegramBot tgBot = new TelegramBot("asdsd","dasd");
        var tgGateway = Mockito.mock(WaterBotGateway.class);
        ArgumentCaptor<WaterBotMessage> waterBotMessageArgumentCaptor = ArgumentCaptor.forClass(WaterBotMessage.class);
        var expectedMessage = new WaterBotMessage(UserId.fromLong(1L), "ciao");
        var update = buildTestUpdate(expectedMessage);
        tgBot.setGateway(tgGateway);

        tgBot.onUpdateReceived(update);

        Mockito.verify(tgGateway).onUpdates(waterBotMessageArgumentCaptor.capture());
        var message = waterBotMessageArgumentCaptor.getValue();
        assertThat(message, is(equalTo(expectedMessage)));
    }

    private Update buildTestUpdate(WaterBotMessage wbMsg) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        chat.setId(wbMsg.getUserId().getValue());
        message.setChat(chat);
        message.setText(wbMsg.getMessage());

        update.setMessage(message);

        return update;
    }

}
