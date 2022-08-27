package com.ncoding.core.actions;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.services.WaterBotGateway;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

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
        var waterBotGateway = Mockito.mock(WaterBotGateway.class);
        var fixedMessage = "Hello, Drink now";
        var waterBotRepository = Mockito.mock(WaterBotRepository.class);

        var userToSendMessageTo = new UserId("1");
        var set = new HashSet<>(List.of(userToSendMessageTo));
        when(waterBotRepository.getUsers()).thenReturn(set);

        var messagePicker = new FixedMessagePicker(fixedMessage);
        ArgumentCaptor<WaterBotMessage> wbArgumentCaptor = ArgumentCaptor.forClass(WaterBotMessage.class);
        var expectedMessage = new WaterBotMessage(userToSendMessageTo, fixedMessage);

        DrinkAlertAction waterBot = new DrinkAlertAction(waterBotGateway, messagePicker, waterBotRepository);

        waterBot.execute();
        verify(waterBotGateway).sendMessage(wbArgumentCaptor.capture());

        var value = wbArgumentCaptor.getValue();
        assertThat(value, is(equalTo(expectedMessage)));
    }
}
