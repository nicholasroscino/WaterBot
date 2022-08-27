package com.ncoding.infrastructure;

import com.ncoding.core.ports.Clock;

import java.time.Instant;
import java.time.ZoneOffset;

public class SystemClock implements Clock {
    @Override
    public int getCurrentUTCHour() {
        return Instant.now().atOffset(ZoneOffset.UTC).getHour();
    }
}
