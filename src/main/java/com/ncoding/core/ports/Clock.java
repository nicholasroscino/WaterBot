package com.ncoding.core.ports;

public interface Clock {
    int getCurrentUTCHour();
    String getCurrentTimestamp();
}
