package com.ncoding;

import com.ncoding.com.services.*;
import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.models.DayPhases;
import com.ncoding.core.ports.*;
import com.ncoding.infrastructure.*;
import com.ncoding.ui.TelegramBot;
import com.ncoding.ui.TelegramMessageAdapter;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        BotConfig config = ConfigProvider.provideConfig(Environment.PROD);

        TelegramBot waterBot = new TelegramBot(config.getToken(), config.getBotName(), new TelegramMessageAdapter());
        UserRepository wbRepository = new InMemoryWaterBotRepository();
        ReportRepository reportRepository = new InMemoryReportRepository();
        Clock clock = new SystemClock();
        IActionFactory actionFactory = new ActionFactory(wbRepository, reportRepository, clock);
        JobScheduler waterBotScheduler = new WispSchedulerWrapper();
        IWaterBotGateway waterBotGateway = new WaterBotGateway(List.of(waterBot), actionFactory);

        Map<Integer, DayPhases> phasesMap = new HashMap<>();
        phasesMap.put(8, DayPhases.Morning);
        phasesMap.put(10, DayPhases.Other);
        phasesMap.put(12, DayPhases.Lunch);
        phasesMap.put(14, DayPhases.Other);
        phasesMap.put(16, DayPhases.Other);
        phasesMap.put(18, DayPhases.Other);
        phasesMap.put(20, DayPhases.Dinner);
        phasesMap.put(22, DayPhases.Night);

        DayPhaseRetriever dayPhaseRetriever = new DayPhaseRetrieverImpl(phasesMap);
        MoodRepository moodRepository = new InMemoryMoodRepository();

        MessagePicker messagePicker = new RandomMessagePicker(dayPhaseRetriever,moodRepository);
        IWaterBotScheduler wbScheduler = new WaterBotScheduler(waterBotScheduler,
                waterBotGateway,
                messagePicker,
                wbRepository,
                phasesMap.keySet(),
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
