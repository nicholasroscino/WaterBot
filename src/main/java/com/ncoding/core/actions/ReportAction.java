package com.ncoding.core.actions;

import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.models.Report;
import com.ncoding.core.models.WaterBotMessage;
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

        Report r = new Report(message.getUserId(), msg, clock.getCurrentTimestamp());
        repository.save(r);
        gateway.sendMessage(new WaterBotMessage(message.getUserId(), "The report has been submitted, thank you :)"));
    }
}
