package com.ncoding.core.actions;

import com.ncoding.core.models.User;
import com.ncoding.core.models.WaterBotMessageResponse;
import com.ncoding.core.ports.UserRepository;
import com.ncoding.core.services.IWaterBotGateway;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegisterUserActionUnitTest {

    @Test
    public void execute() {
        var expectedId = "1";
        var repository = Mockito.mock(UserRepository.class);
        var waterbotGateway = Mockito.mock(IWaterBotGateway.class);
        var gatewayCaptor = ArgumentCaptor.forClass(WaterBotMessageResponse.class);
        var repoCapture = ArgumentCaptor.forClass(User.class);
        var expectedResponseMessage = TestUtils.buildWaterBotMessageResponse(expectedId, "User registered successfully");
        Action action = new RegisterUserAction(TestUtils.buildWaterBotMessage(expectedId, "nick", "nick", "/start"),
                waterbotGateway,
                repository);

        action.execute();

        Mockito.verify(repository).register(repoCapture.capture());
        Mockito.verify(waterbotGateway).sendMessage(gatewayCaptor.capture());
        var repoValue = repoCapture.getValue();
        var messageValue = gatewayCaptor.getValue();
        assertThat(repoValue.getUserId().getValue(), is(equalTo("1")));
        assertThat(messageValue, is(equalTo(expectedResponseMessage)));
    }
}
