package com.ncoding.core.actions;

import com.ncoding.core.models.User;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.Clock;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.com.services.WaterBotGateway;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AllArgsConstructor
class FixedMessagePicker implements MessagePicker {
    private String messageToReturn;

    @Override
    public String getMessage() {
        return this.messageToReturn;
    }
}

public class DrinkAlertActionUnitTest {

    @Test
    public void execute() {
        var triggerTime = 12;
        var fixedMessage = "Hello, Drink now";
        var userToSendMessageTo = new User(new UserId("1"), "nick", "nick", ZoneOffset.UTC);
        var set = new HashSet<>(List.of(userToSendMessageTo));
        var waterBotGateway = Mockito.mock(WaterBotGateway.class);
        var waterBotRepository = Mockito.mock(UserRepository.class);
        var clock = Mockito.mock(Clock.class);
        var messagePicker = new FixedMessagePicker(fixedMessage);
        ArgumentCaptor<WaterBotMessageResponse> wbArgumentCaptor = ArgumentCaptor.forClass(WaterBotMessageResponse.class);
        var expectedMessage = new WaterBotMessageResponse(userToSendMessageTo.getUserId(), fixedMessage);
        DrinkAlertAction waterBot = new DrinkAlertAction(waterBotGateway, messagePicker, waterBotRepository, List.of(triggerTime), clock);
        when(clock.getCurrentUTCHour()).thenReturn(triggerTime);
        when(waterBotRepository.getAll()).thenReturn(set);

        waterBot.execute();
        verify(waterBotGateway).sendMessage(wbArgumentCaptor.capture());
        var value = wbArgumentCaptor.getValue();
        assertThat(value, is(equalTo(expectedMessage)));
    }
}
