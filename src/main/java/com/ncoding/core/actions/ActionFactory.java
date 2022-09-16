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

    private static final String BAN_ACTION = "/ban";
    private static final String UNBAN_ACTION = "/unban";


    public Action createAction(WaterBotMessage waterBotMessage, IWaterBotGateway waterBotGateway) {
        return switch (extractCommand(waterBotMessage.getMessage())) {
            case EchoAction.CODE -> new EchoAction(waterBotGateway, waterBotMessage);
            case RegisterUserAction.CODE -> new RegisterUserAction(waterBotMessage, waterBotGateway, repository);
            case ReportAction.CODE -> new ReportAction(reportRepository, waterBotMessage, clock, waterBotGateway);
            case SetTimeZoneAction.CODE -> new SetTimeZoneAction(repository, waterBotMessage, waterBotGateway);
            case HelpAction.CODE -> new HelpAction(waterBotGateway, waterBotMessage);
            default -> null;
        };
    }

    @Override
    public Action createChatMemberUpdatedAction(WaterBotMessage message) {
        return switch (extractCommand(message.getMessage())) {
            case BAN_ACTION -> new BanAction(repository, message);
            case UNBAN_ACTION -> new UnbanAction(repository, message);
            default -> null;
        };
    }

    private String extractCommand(String message) {
        return message.split(" ")[0];
    }
}
