package com.ncoding;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.JobScheduler;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.infrastructure.InMemoryWaterBotRepository;
import com.ncoding.infrastructure.RandomMessagePicker;
import com.ncoding.infrastructure.SystemClock;
import com.ncoding.infrastructure.WispSchedulerWrapper;
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

        System.out.println(config);

        TelegramBot waterBot = new TelegramBot(config.getToken(), config.getBotName(), new TelegramMessageAdapter());
        WaterBotRepository wbRepository = new InMemoryWaterBotRepository();
        Clock clock = new SystemClock();
        IActionFactory actionFactory = new ActionFactory(wbRepository);
        JobScheduler waterBotScheduler = new WispSchedulerWrapper();
        IWaterBotGateway waterBotGateway = new WaterBotGateway(List.of(waterBot), actionFactory);
        MessagePicker messagePicker = new RandomMessagePicker();
        waterBot.setGateway(waterBotGateway);
        IWaterBotScheduler wbScheduler = new WaterBotScheduler(waterBotScheduler, waterBotGateway, messagePicker, wbRepository,List.of(14,15), clock);

        wbScheduler.runScheduler();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(waterBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
