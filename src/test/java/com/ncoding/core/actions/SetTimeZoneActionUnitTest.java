package com.ncoding.core.actions;

import com.ncoding.com.services.IWaterBotGateway;
import com.ncoding.core.models.*;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.ReportRepository;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

public class SetTimeZoneActionUnitTest {

    @Test
    public void execute() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        WaterBotMessage message = TestUtils.buildWaterBotMessage("TG-1", "nick","nick", "/timezone +1");
        IWaterBotGateway waterBotGateway = Mockito.mock(IWaterBotGateway.class);
        var captor = ArgumentCaptor.forClass(WaterBotMessageResponse.class);
        var userCaptor = ArgumentCaptor.forClass(User.class);
        User userStub = new User(new UserId("TG-1"), "nick","nick", ZoneOffset.UTC);
        Mockito.when(userRepository.getOne(Mockito.any())).thenReturn(userStub);

        SetTimeZoneAction reportAction = new SetTimeZoneAction(userRepository,message,waterBotGateway);
        reportAction.execute();

        Mockito.verify(waterBotGateway).sendMessage(captor.capture());
        Mockito.verify(userRepository).update(userCaptor.capture());
        var user = userCaptor.getValue();
        var value = captor.getValue();
        assertThat(value, equalTo(TestUtils.buildWaterBotMessageResponse("TG-1", "The timezone has been updated")));
        assertThat(user, equalTo(new User(new UserId("TG-1"),"nick", "nick", ZoneOffset.of("+1"))));
    }

    @Test
    public void executeWithWrongInput() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        WaterBotMessage message = TestUtils.buildWaterBotMessage("TG-1", "nick","nick", "/timezone +15");
        IWaterBotGateway waterBotGateway = Mockito.mock(IWaterBotGateway.class);
        var captor = ArgumentCaptor.forClass(WaterBotMessageResponse.class);

        SetTimeZoneAction reportAction = new SetTimeZoneAction(userRepository,message,waterBotGateway);
        reportAction.execute();

        Mockito.verify(waterBotGateway).sendMessage(captor.capture());

        var value = captor.getValue();
        assertThat(value, equalTo(TestUtils.buildWaterBotMessageResponse("TG-1", "Wrong Timezone. /help to receive tips.")));
    }
}
