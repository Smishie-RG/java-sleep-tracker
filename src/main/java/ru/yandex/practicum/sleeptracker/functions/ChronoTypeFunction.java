package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.enums.ChronoType;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronoTypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime OWL_SLEEP = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(this::isNightSleep)
                .collect(Collectors.toList());

        if (nightSessions.isEmpty()) return new SleepAnalysisResult("Хронотип пользователя", ChronoType.DOVE);

        Map<ChronoType, Long> counts = nightSessions.stream()
                .map(this::classifySession)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long maxCount = counts.values().stream().max(Long::compare).orElse(0L);

        List<ChronoType> maxTypes = counts.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        ChronoType dominant = maxTypes.size() > 1 ? ChronoType.DOVE : maxTypes.get(0);

        return new SleepAnalysisResult("Хронотип пользователя", dominant);
    }

    private ChronoType classifySession(SleepingSession session) {
        LocalTime sleepTime = session.getSleepStartTime();
        LocalTime wakeTime = session.getSleepEndTime();

        if (sleepTime.isAfter(OWL_SLEEP) && wakeTime.isAfter(OWL_WAKE)) {
            return ChronoType.OWL;
        }
        if (sleepTime.isBefore(LARK_SLEEP) && wakeTime.isBefore(LARK_WAKE)) {
            return ChronoType.LARK;
        }
        return ChronoType.DOVE;
    }

    private boolean isNightSleep(SleepingSession session) {
        return session.getSleepStartTime().isBefore(LocalTime.of(23, 59)) &&
                session.getSleepEndTime().isAfter(LocalTime.MIDNIGHT);
    }
}