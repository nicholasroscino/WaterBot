package com.ncoding.core.ports;

import com.ncoding.core.models.DayPhases;

import java.util.List;

public interface MoodRepository {
    List<String> getMood(DayPhases dayPhases);
}
