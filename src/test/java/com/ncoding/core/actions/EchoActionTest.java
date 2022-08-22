package com.ncoding.core.actions;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.services.WaterBotGateway;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class EchoActionTest {

    @Test
    public void execute() {
        var waterbotGateway = Mockito.mock(WaterBotGateway.class);
        ArgumentCaptor<WaterBotMessage> messageCaptor = ArgumentCaptor.forClass(WaterBotMessage.class);
        var message = TestUtils.buildWaterBotMessage(1L, "hello");
        Action action = new EchoAction(waterbotGateway, message);

        action.execute();

        Mockito.verify(waterbotGateway).sendMessage(messageCaptor.capture());
        WaterBotMessage value = messageCaptor.getValue();

        assertThat(value, is(equalTo(message)));
    }

}
