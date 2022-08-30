package com.ncoding.core.services;

import com.ncoding.core.models.DayPhases;

public interface DayPhaseRetriever {
    DayPhases getByHour(int hour);
}
