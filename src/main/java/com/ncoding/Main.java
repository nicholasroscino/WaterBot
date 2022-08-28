package com.ncoding;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.ports.*;
import com.ncoding.infrastructure.*;
import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.com.services.IWaterBotScheduler;
import com.ncoding.com.services.WaterBotGateway;
import com.ncoding.com.services.WaterBotScheduler;
import com.ncoding.ui.TelegramBot;
import com.ncoding.ui.TelegramMessageAdapter;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BotConfig config = ConfigProvider.provideConfig(Environment.PROD);

        TelegramBot waterBot = new TelegramBot(config.getToken(), config.getBotName(), new TelegramMessageAdapter());
        WaterBotRepository wbRepository = new InMemoryWaterBotRepository();
        ReportRepository reportRepository = new InMemoryReportRepository();
        Clock clock = new SystemClock();
        IActionFactory actionFactory = new ActionFactory(wbRepository, reportRepository, clock);
        JobScheduler waterBotScheduler = new WispSchedulerWrapper();
        IWaterBotGateway waterBotGateway = new WaterBotGateway(List.of(waterBot), actionFactory);
        MessagePicker messagePicker = new RandomMessagePicker();
        IWaterBotScheduler wbScheduler = new WaterBotScheduler(waterBotScheduler,
                waterBotGateway,
                messagePicker,
                wbRepository,
                List.of(8, 10, 12, 14, 18, 20),
                clock);

        waterBot.setGateway(waterBotGateway);
        wbScheduler.runScheduler();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(waterBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
