package com.ncoding.com.services;

import com.ncoding.core.actions.ActionFactory;
import com.ncoding.core.models.UserId;
import com.ncoding.core.models.WaterBotMessage;
import com.ncoding.core.ports.WaterBotRepository;
import com.ncoding.ui.TelegramBot;
import com.ncoding.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WaterBotSchedulerUnitTest {

    @Test
    public void verifyRightMessageSent() {

    }


}
