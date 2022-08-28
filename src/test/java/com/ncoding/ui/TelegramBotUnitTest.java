package com.ncoding.ui;

import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotClient;
import com.ncoding.com.services.WaterBotGateway;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TelegramBotUnitTest {

    @Test
    public void updateMessageGetsDeliveredCorrectly() {
        var expectedId = "1";
        var expectedMessage = "ciao";
        TelegramBot tgBot = new TelegramBot("asdsd", "dasd", new TelegramMessageAdapter());
        var tgGateway = Mockito.mock(IWaterBotGateway.class);
        ArgumentCaptor<WaterBotMessage> waterBotMessageArgumentCaptor = ArgumentCaptor.forClass(WaterBotMessage.class);
        var expectedWBMessage = TestUtils.buildWaterBotMessage("TG-" + expectedId, "nick", "nick", expectedMessage);
        var update = buildTestUpdate(TestUtils.buildWaterBotMessage(expectedId, "nick", "nick", expectedMessage));

        tgBot.setGateway(tgGateway);
        tgBot.onUpdateReceived(update);

        Mockito.verify(tgGateway).onUpdates(waterBotMessageArgumentCaptor.capture());
        var message = waterBotMessageArgumentCaptor.getValue();
        assertThat(message, is(equalTo(expectedWBMessage)));
    }

    @Test
    public void canHandleTelegramMessage() {
        WaterBotClient tgBot = new TelegramBot("asdsd", "dasd", new TelegramMessageAdapter());
        assertThat(tgBot.canHandle(TestUtils.buildWaterBotMessageResponse("TG-1", "ciao")), is(equalTo(true)));
    }

    private Update buildTestUpdate(WaterBotMessage wbMsg) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        chat.setUserName(wbMsg.getUserTag());
        chat.setFirstName(wbMsg.getUserName());
        chat.setId(Long.parseLong(wbMsg.getUserId().getValue()));
        message.setChat(chat);
        message.setText(wbMsg.getMessage());

        update.setMessage(message);

        return update;
    }

}
