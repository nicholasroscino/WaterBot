package com.ncoding.com.services;

import com.ncoding.core.models.DayPhases;

public interface DayPhaseRetriever {
    DayPhases getByHour(int hour);
}
