package com.ncoding.core.services;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.ui.TelegramBot;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class WaterBotGatewayUnitTest {

    @Test
    public void verifyRightMessageSent() {
        var telegramBot = Mockito.mock(TelegramBot.class);
        var waterBotRepository = Mockito.mock(UserRepository.class);
        var reportRepository = Mockito.mock(ReportRepository.class);
        var clock = Mockito.mock(Clock.class);
        WaterBotGateway wbGateway = new WaterBotGateway(List.of(telegramBot), new ActionFactory(waterBotRepository, reportRepository, clock));
        WaterBotMessageResponse waterBotMessage = new WaterBotMessageResponse(
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
        var waterBotRepository = Mockito.mock(UserRepository.class);
        var clock = Mockito.mock(Clock.class);
        var reportRepository = Mockito.mock(ReportRepository.class);
        var wbGateway = new WaterBotGateway(List.of(telegramBot), new ActionFactory(waterBotRepository, reportRepository, clock));
        var echoMessage = TestUtils.buildWaterBotMessage("1", "nick", "nick", "/echo");
        Mockito.when(telegramBot.canHandle(Mockito.any())).thenReturn(true);

        wbGateway.onUpdates(echoMessage);

        Mockito.verify(telegramBot).sendMessage(TestUtils.buildWaterBotMessageResponse("1", "/echo"));
    }

}
