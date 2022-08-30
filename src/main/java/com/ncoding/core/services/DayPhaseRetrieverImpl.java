package com.ncoding.core.services;

import com.ncoding.core.models.DayPhases;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class DayPhaseRetrieverImpl implements DayPhaseRetriever {
    private final Map<Integer, DayPhases> phasesMap;

    @Override
    public DayPhases getByHour(int hour) {
        return phasesMap.get(hour);
    }
}
