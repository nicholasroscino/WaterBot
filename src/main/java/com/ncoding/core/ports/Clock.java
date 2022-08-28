package com.ncoding.core.ports;

import java.time.ZoneOffset;

public interface Clock {
    int getCurrentUTCHour();
    String getCurrentTimestamp();
    int getCurrentTimeZoneHour(ZoneOffset offset);
}
