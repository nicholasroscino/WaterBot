package com.ncoding;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.ports.JobScheduler;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.infrastructure.InMemoryWaterBotRepository;
import com.ncoding.infrastructure.RandomMessagePicker;
import com.ncoding.infrastructure.WispSchedulerWrapper;
import com.ncoding.services.IWaterBotGateway;
import com.ncoding.services.IWaterBotScheduler;
import com.ncoding.services.WaterBotGateway;
import com.ncoding.services.WaterBotScheduler;
import com.ncoding.ui.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("MMMMM");

        BotConfig config = ConfigProvider.provideConfig(Environment.PROD);

        System.out.println(config);

        TelegramBot waterBot = new TelegramBot(config.getToken(), config.getBotName());
        WaterBotRepository wbRepository = new InMemoryWaterBotRepository();

        IActionFactory actionFactory = new ActionFactory(wbRepository);
        JobScheduler waterBotScheduler = new WispSchedulerWrapper();
        IWaterBotGateway waterBotGateway = new WaterBotGateway(waterBot, actionFactory);
        MessagePicker messagePicker = new RandomMessagePicker();
        waterBot.setGateway(waterBotGateway);
        IWaterBotScheduler wbScheduler = new WaterBotScheduler(waterBotScheduler, waterBotGateway, messagePicker, wbRepository);

        wbScheduler.runScheduler();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(waterBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
