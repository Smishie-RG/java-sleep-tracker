package ru.yandex.practicum.sleeptracker.model;

import ru.yandex.practicum.sleeptracker.enums.SleepQuality;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SleepingSession {

    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime sleepEnd, SleepQuality quality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.quality = quality;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationInMinutes() {
        return Duration.between(sleepStart, sleepEnd).toMinutes();
    }

    public LocalTime getSleepStartTime() {
        return sleepStart.toLocalTime();
    }

    public LocalTime getSleepEndTime() {
        return sleepEnd.toLocalTime();
    }

    @Override
    public String toString() {
        return sleepStart + " -> " + sleepEnd + " (" + quality + ")";
    }
}