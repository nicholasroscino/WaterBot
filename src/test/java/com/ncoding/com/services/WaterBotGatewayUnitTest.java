package com.ncoding.com.services;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.ui.TelegramBot;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class WaterBotGatewayUnitTest {

    @Test
    public void verifyRightMessageSent() {
        var telegramBot = Mockito.mock(TelegramBot.class);
        var waterBotRepository = Mockito.mock(WaterBotRepository.class);
        var reportRepository = Mockito.mock(ReportRepository.class);
        var clock = Mockito.mock(Clock.class);
        WaterBotGateway wbGateway = new WaterBotGateway(List.of(telegramBot), new ActionFactory(waterBotRepository, reportRepository, clock));
        WaterBotMessage waterBotMessage = new WaterBotMessage(
                new UserId("1"),
                "message"
        );
        Mockito.when(telegramBot.canHandle(Mockito.any())).thenReturn(true);

        wbGateway.sendMessage(waterBotMessage);

        Mockito.verify(telegramBot).sendMessage(waterBotMessage);
    }

    @Test
    public void verifyEchoActionIsCreated() {
        var telegramBot = Mockito.mock(TelegramBot.class);
        var waterBotRepository = Mockito.mock(WaterBotRepository.class);
        var clock = Mockito.mock(Clock.class);
        var reportRepository = Mockito.mock(ReportRepository.class);
        var wbGateway = new WaterBotGateway(List.of(telegramBot), new ActionFactory(waterBotRepository, reportRepository, clock));
        var echoMessage = TestUtils.buildWaterBotMessage("1", "/echo");
        Mockito.when(telegramBot.canHandle(Mockito.any())).thenReturn(true);

        wbGateway.onUpdates(echoMessage);

        Mockito.verify(telegramBot).sendMessage(TestUtils.buildWaterBotMessage("1", "/echo"));
    }

}
