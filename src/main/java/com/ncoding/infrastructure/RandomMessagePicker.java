package com.ncoding.infrastructure;

import com.ncoding.core.services.DayPhaseRetriever;
import com.ncoding.core.ports.MessagePicker;
import com.ncoding.core.ports.MoodRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RandomMessagePicker implements MessagePicker {
    private final DayPhaseRetriever dayPhaseRetriever;
    private final MoodRepository moodRepository;

    @Override
    public String getMessage(int hour) {
        var phase = dayPhaseRetriever.getByHour(hour);
        var list = moodRepository.getMood(phase);

        var rand = (int)Math.floor(Math.random()*list.size());

        return list.get(rand);
    }
}
