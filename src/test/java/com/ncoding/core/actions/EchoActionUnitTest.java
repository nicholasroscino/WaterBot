package com.ncoding.core.actions;

import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.com.services.WaterBotGateway;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class EchoActionUnitTest {

    @Test
    public void execute() {
        var waterbotGateway = Mockito.mock(WaterBotGateway.class);
        ArgumentCaptor<WaterBotMessage> messageCaptor = ArgumentCaptor.forClass(WaterBotMessage.class);
        var message = TestUtils.buildWaterBotMessage("1", "hello");
        Action action = new EchoAction(waterbotGateway, message);

        action.execute();

        Mockito.verify(waterbotGateway).sendMessage(messageCaptor.capture());
        WaterBotMessage value = messageCaptor.getValue();

        assertThat(value, is(equalTo(message)));
    }

}
