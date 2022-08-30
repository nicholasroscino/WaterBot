package com.ncoding.core.actions;

import com.ncoding.core.services.WaterBotGateway;
import com.ncoding.core.models.WaterBotMessageResponse;
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
        ArgumentCaptor<WaterBotMessageResponse> messageCaptor = ArgumentCaptor.forClass(WaterBotMessageResponse.class);
        var message = TestUtils.buildWaterBotMessage("1", "nick", "nick", "hello");
        Action action = new EchoAction(waterbotGateway, message);

        action.execute();

        Mockito.verify(waterbotGateway).sendMessage(messageCaptor.capture());
        var value = messageCaptor.getValue();
        assertThat(value, is(equalTo(TestUtils.buildWaterBotMessageResponse(message.getUserId().getValue(), message.getMessage()))));
    }

}
