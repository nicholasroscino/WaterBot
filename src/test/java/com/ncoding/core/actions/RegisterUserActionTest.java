package com.ncoding.core.actions;

import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.services.IWaterBotGateway;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegisterUserActionTest {

    @Test
    public void execute() {
        var repository = Mockito.mock(WaterBotRepository.class);
        var waterbotGateway = Mockito.mock(IWaterBotGateway.class);
        ArgumentCaptor<WaterBotMessage> gatewayCaptor = ArgumentCaptor.forClass(WaterBotMessage.class);
        ArgumentCaptor<UserId> repoCapture = ArgumentCaptor.forClass(UserId.class);
        var expectedResponseMessage = TestUtils.buildWaterBotMessage(1L, "User registered successfully");

        Action action = new RegisterUserAction(TestUtils.buildWaterBotMessage(1L, "/start"),
                waterbotGateway,
                repository);

        action.execute();

        Mockito.verify(repository).registerUser(repoCapture.capture());
        Mockito.verify(waterbotGateway).sendMessage(gatewayCaptor.capture());

        UserId repoValue = repoCapture.getValue();
        WaterBotMessage messageValue = gatewayCaptor.getValue();

        assertThat(repoValue.getValue(), is(equalTo(1L)));
        assertThat(messageValue, is(equalTo(expectedResponseMessage)));
    }
}
