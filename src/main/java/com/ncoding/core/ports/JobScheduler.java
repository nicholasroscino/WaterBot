package com.ncoding.core.ports;

public interface JobScheduler {
    void scheduleEveryHour(Runnable runnable);
}
