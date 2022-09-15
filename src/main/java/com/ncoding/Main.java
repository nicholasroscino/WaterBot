package com.ncoding;

import com.ncoding.core.services.*;
import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.actions.IActionFactory;
import com.ncoding.core.models.DayPhases;
import com.ncoding.core.ports.*;
import com.ncoding.infrastructure.mariadb.MariaDbMoodRepository;
import com.ncoding.infrastructure.mariadb.MariaDbReportRepository;
import com.ncoding.infrastructure.mariadb.MariaDbWaterBotRepository;
import com.ncoding.infrastructure.system.SystemClock;
import com.ncoding.infrastructure.wisp.WispSchedulerWrapper;
import com.ncoding.ui.TelegramBot;
import com.ncoding.ui.TelegramMessageAdapter;
import org.mariadb.jdbc.MariaDbDataSource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        BotConfig config = ConfigProvider.provideConfig(Environment.PROD);
        MariaDbDataSource mariaDbDataSource = new MariaDbDataSource();

        try {
            mariaDbDataSource.setUrl("jdbc:mariadb://localhost:3307/waterbot");
            mariaDbDataSource.setUser("nick");
            mariaDbDataSource.setPassword("pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TelegramBot waterBot = new TelegramBot(config.getToken(), config.getBotName(), new TelegramMessageAdapter());
        UserRepository wbRepository = new MariaDbWaterBotRepository(mariaDbDataSource);
        ReportRepository reportRepository = new MariaDbReportRepository(mariaDbDataSource);
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
        MoodRepository moodRepository = new MariaDbMoodRepository(mariaDbDataSource);

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
