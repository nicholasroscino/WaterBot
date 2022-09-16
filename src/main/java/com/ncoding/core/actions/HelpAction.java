package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.services.IWaterBotGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HelpAction implements Action {
    public static final String CODE = "/help";

    private final IWaterBotGateway waterBotGateway;
    private final WaterBotMessage receivedMessage;

    private String helpMessageString() {
        return """
                /start: The command to start the bot
                /help: the command to get a list of all available commands.
                /report: The command to send feedback from user. eg. /report [message]
                /timezone - usage: /timezone [number]. number has to be signed like +1 or -3. For UTC Time provide Z instead.
                \teg. /timezone Z""";
    }

    @Override
    public void execute() {
        this.waterBotGateway.sendMessage(new WaterBotMessageResponse(receivedMessage.getUserId(), helpMessageString()));
    }
}
