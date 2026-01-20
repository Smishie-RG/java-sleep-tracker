package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepingSession::getSleepStart)
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDateTime lastEnd = sessions.stream()
                .map(SleepingSession::getSleepEnd)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDate firstNight = firstStart.getHour() >= 12
                ? firstStart.toLocalDate().plusDays(1)
                : firstStart.toLocalDate().minusDays(1);

        LocalDate lastNight = lastEnd.toLocalDate();

        long sleeplessNights = firstNight.datesUntil(lastNight.plusDays(1))
                .filter(night -> sessions.stream().noneMatch(s -> intersectsNight(s, night)))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }

    private boolean intersectsNight(SleepingSession session, LocalDate nightDate) {
        LocalDateTime nightStart = nightDate.atTime(NIGHT_START);
        LocalDateTime nightEnd = nightDate.atTime(NIGHT_END);
        return session.getSleepEnd().isAfter(nightStart) && session.getSleepStart().isBefore(nightEnd);
    }
}