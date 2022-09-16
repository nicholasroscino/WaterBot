package com.ncoding.core.actions;

import com.ncoding.core.services.IWaterBotGateway;
import com.ncoding.core.models.Report;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReportAction implements Action {
    public static final String CODE = "/report";

    private final ReportRepository repository;
    private final WaterBotMessage message;
    private final Clock clock;
    private final IWaterBotGateway gateway;

    @Override
    public void execute() {
        var msg = message.getMessage().substring(CODE.length()).trim();

        if(msg.length() == 0) {
            gateway.sendMessage(new WaterBotMessageResponse(message.getUserId(), "The report message should contain a message"));
            return;
        }

        Report r = new Report(message.getUserId(), msg, clock.getCurrentTimestamp());
        repository.save(r);
        gateway.sendMessage(new WaterBotMessageResponse(message.getUserId(), "The report has been submitted, thank you :)"));
    }
}
