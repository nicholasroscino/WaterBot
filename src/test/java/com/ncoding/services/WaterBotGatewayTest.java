package com.ncoding.services;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.ui.TelegramBot;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WaterBotGatewayTest {

    @Test
    public void verifyRightMessageSent() {
        var telegramBot = Mockito.mock(TelegramBot.class);
        var waterBotRepository = Mockito.mock(WaterBotRepository.class);
        WaterBotGateway wbGateway = new WaterBotGateway(telegramBot, new ActionFactory(waterBotRepository));

        WaterBotMessage waterBotMessage = new WaterBotMessage(
                UserId.fromLong(1L),
                "message"
        );
        wbGateway.sendMessage(waterBotMessage);

        Mockito.verify(telegramBot).sendMessage(1L, "message");
    }

    @Test
    public void verifyEchoActionIsCreated() {
        var telegramBot = Mockito.mock(TelegramBot.class);
        var waterBotRepository = Mockito.mock(WaterBotRepository.class);
        var wbGateway = new WaterBotGateway(telegramBot, new ActionFactory(waterBotRepository));
        wbGateway.onUpdates(TestUtils.buildWaterBotMessage(1L, "/echo"));

        Mockito.verify(telegramBot).sendMessage(1L, "/echo");
    }

}
