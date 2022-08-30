package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.core.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActionFactory implements IActionFactory {
    private final UserRepository repository;
    private final ReportRepository reportRepository;
    private final Clock clock;

    public Action createAction(WaterBotMessage waterBotMessage, IWaterBotGateway waterBotGateway) {
        return switch (extractCommand(waterBotMessage.getMessage())) {
            case EchoAction.CODE -> new EchoAction(waterBotGateway,waterBotMessage);
            case RegisterUserAction.CODE -> new RegisterUserAction(waterBotMessage, waterBotGateway, repository);
            case ReportAction.CODE -> new ReportAction(reportRepository, waterBotMessage, clock, waterBotGateway);
            case SetTimeZoneAction.CODE -> new SetTimeZoneAction(repository,waterBotMessage,waterBotGateway);
            default -> null;
        };
    }

    private String extractCommand(String message) {
        return message.split(" ")[0];
    }
}
