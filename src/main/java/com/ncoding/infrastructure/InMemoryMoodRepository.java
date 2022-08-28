package com.ncoding.infrastructure;

import com.ncoding.core.models.DayPhases;
import com.ncoding.core.ports.MoodRepository;

import java.util.List;
import java.util.Map;

public class InMemoryMoodRepository implements MoodRepository {
    private final Map<DayPhases, List<String>> cuteEngMessages = Map.of(
            DayPhases.Lunch, List.of(
                    "Hey! Enjoy your meal! remember to drink at least 2 glasses of water!",
                    "Helloouu, Lunch Time!! Have a nice Lunch and don't forget to drink at least 2 glasses of water!",
                    "D-D-D-DOUBLE DRINK! Enjoy your lunch! :3",
                    "Oh, you are going to eat! You should drink some water too!",
                    "I Wanna eat something too!! Bring me with you, so I'll remember you to drink some water too!"
            ),
            DayPhases.Dinner, List.of(
                    "Hey! Enjoy your meal! Remember to drink at least 2 glasses of water!",
                    "Helloouu, Dinner Time!! Have a nice Dinner and don't forget to drink at least 2 glasses of water!",
                    "D-D-D-DOUBLE DRINK! Enjoy your dinner! :3",
                    "Oh, you are going to eat! You should drink some water too!",
                    "I Wanna eat something too!! Bring me with you, so I'll remember you to drink some water too!"
            ),
            DayPhases.Morning, List.of(
                    "Hey! Good Morning! There are no better way of starting the day other than drinking a glass of water!",
                    "Yaaaaawn... Good Morning Honey! Have you already drunk a glass of water? If you haven't you should!",
                    "Hey! Good Morning! What about drinking a glass of water? It'll make you feel better!"
            ),
            DayPhases.Night, List.of(
                    "Hey! Goodnight! You should go drink a glass of water before going to bed!",
                    "Time to get some water! I'm going to sleep now, so.. Goooodnight and sleep tight!"
            ),
            DayPhases.Other, List.of(
                    "Hey! Water timeeee! let's go drink some water!",
                    "Hey, it's time to drink a glass of water!"
            )
    );

    @Override
    public List<String> getMood(DayPhases dayPhases) {
        return cuteEngMessages.get(dayPhases);
    }
}
