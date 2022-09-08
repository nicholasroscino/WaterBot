package com.ncoding.infrastructure.system;

import com.ncoding.core.ports.Clock;

import java.time.Instant;
import java.time.ZoneOffset;

public class SystemClock implements Clock {
    @Override
    public int getCurrentUTCHour() {
        return getCurrentTimeZoneHour(ZoneOffset.UTC);
    }

    public int getCurrentTimeZoneHour(ZoneOffset offset) {
        return Instant.now().atOffset(offset).getHour();
    }

    @Override
    public String getCurrentTimestamp() {
        return Instant.now().toString();
    }
}
