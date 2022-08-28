package com.ncoding.core.actions;

import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.models.Report;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReportActionUnitTest {

    @Test
    public void execute() {
        ReportRepository reportRepository = Mockito.mock(ReportRepository.class);
        WaterBotMessage message = TestUtils.buildWaterBotMessage("TG-1", "nick","nick", "/report huston we have a problem");
        Clock clock = Mockito.mock(Clock.class);
        IWaterBotGateway waterBotGateway = Mockito.mock(IWaterBotGateway.class);
        var captor = ArgumentCaptor.forClass(WaterBotMessageResponse.class);
        ArgumentCaptor<Report> reportCaptor = ArgumentCaptor.forClass(Report.class);
        Mockito.when(clock.getCurrentTimestamp()).thenReturn("2022-08-28T01:24:01Z");

        ReportAction reportAction = new ReportAction(reportRepository,message,clock,waterBotGateway);
        reportAction.execute();

        Mockito.verify(waterBotGateway).sendMessage(captor.capture());
        Mockito.verify(reportRepository).save(reportCaptor.capture());
        Report report = reportCaptor.getValue();
        var value = captor.getValue();
        assertThat(value, equalTo(TestUtils.buildWaterBotMessageResponse("TG-1", "The report has been submitted, thank you :)")));
        assertThat(report, equalTo(new Report(new UserId("TG-1"),"huston we have a problem", "2022-08-28T01:24:01Z")));
    }
}
