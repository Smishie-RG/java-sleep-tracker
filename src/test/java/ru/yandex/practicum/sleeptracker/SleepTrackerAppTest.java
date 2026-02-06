package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.ChronoType;
import ru.yandex.practicum.sleeptracker.enums.SleepQuality;
import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    @Test
    public void testTotalSessions_EmptyList() {
        List<SleepingSession> sessions = new ArrayList<>();
        TotalSessionsFunction function = new TotalSessionsFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    @Test
    public void testTotalSessions_MultipleElements() {
        List<SleepingSession> sessions = createTestSessions();
        TotalSessionsFunction function = new TotalSessionsFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(3L, result.getValue());
    }

    @Test
    public void testMinDuration_SingleSession() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,22,0),
                LocalDateTime.of(2025,10,2,8,0),
                SleepQuality.GOOD));
        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(600L, result.getValue());
    }

    @Test
    public void testMinDuration_MultipleSessions() {
        List<SleepingSession> sessions = createTestSessions();
        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(50L, result.getValue());
    }

    @Test
    public void testMaxDuration_SingleSession() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,23,0),
                LocalDateTime.of(2025,10,2,9,0),
                SleepQuality.NORMAL));
        MaxDurationFunction function = new MaxDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(600L, result.getValue());
    }

    @Test
    public void testMaxDuration_MultipleSessions() {
        List<SleepingSession> sessions = createTestSessions();
        MaxDurationFunction function = new MaxDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(540L, result.getValue());
    }

    @Test
    public void testAverageDuration_TwoSessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,22,0),
                LocalDateTime.of(2025,10,2,6,0),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,2,23,0),
                LocalDateTime.of(2025,10,3,7,0),
                SleepQuality.NORMAL));
        AverageDurationFunction function = new AverageDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("480.0", result.getValue());
    }

    @Test
    public void testAverageDuration_SingleSession() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,22,0),
                LocalDateTime.of(2025,10,2,4,0),
                SleepQuality.GOOD));
        AverageDurationFunction function = new AverageDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("360.0", result.getValue());
    }

    @Test
    public void testBadQuality_NoBadSessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,22,0),
                LocalDateTime.of(2025,10,2,8,0),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,2,23,0),
                LocalDateTime.of(2025,10,3,7,0),
                SleepQuality.NORMAL));
        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    @Test
    public void testBadQuality_SomeBadSessions() {
        List<SleepingSession> sessions = createTestSessions();
        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    public void testChronoType_Owl() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,23,30),
                LocalDateTime.of(2025,10,2,10,0),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 1),
                LocalDateTime.of(2025, 10, 3, 9, 30),
                SleepQuality.NORMAL
        ));
        ChronoTypeFunction function = new ChronoTypeFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(ChronoType.OWL, result.getValue());
    }

    @Test
    public void testChronoType_Lark() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,21,0),
                LocalDateTime.of(2025,10,2,6,0),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,2,21,30),
                LocalDateTime.of(2025,10,3,6,30),
                SleepQuality.NORMAL));
        ChronoTypeFunction function = new ChronoTypeFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(ChronoType.LARK, result.getValue());
    }

    @Test
    public void testChronoType_Dove() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,22,30),
                LocalDateTime.of(2025,10,2,7,30),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,2,22,0),
                LocalDateTime.of(2025,10,3,8,0),
                SleepQuality.NORMAL));
        ChronoTypeFunction function = new ChronoTypeFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(ChronoType.DOVE, result.getValue());
    }

    @Test
    public void testChronoType_Mixed() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,23,30),
                LocalDateTime.of(2025,10,2,10,0),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,2,21,0),
                LocalDateTime.of(2025,10,3,6,0),
                SleepQuality.NORMAL));
        ChronoTypeFunction function = new ChronoTypeFunction();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(ChronoType.DOVE, result.getValue());
    }

    private List<SleepingSession> createTestSessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,1,22,0),
                LocalDateTime.of(2025,10,2,7,0),
                SleepQuality.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,2,23,0),
                LocalDateTime.of(2025,10,3,7,0),
                SleepQuality.NORMAL));
        sessions.add(new SleepingSession(LocalDateTime.of(2025,10,3,14,30),
                LocalDateTime.of(2025,10,3,15,20),
                SleepQuality.BAD));
        return sessions;
    }

    @Test
    public void testSleeplessNights_NoSleeplessNights() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 0),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.GOOD
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 0),
                LocalDateTime.of(2025, 10, 3, 7, 0),
                SleepQuality.NORMAL
        ));

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.getValue(), "Не должно быть бессонных ночей");
    }

    @Test
    public void testSleeplessNights_OneSleeplessNight() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 0),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.GOOD
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 23, 0),
                LocalDateTime.of(2025, 10, 4, 7, 0),
                SleepQuality.NORMAL
        ));

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue(), "Должна быть 1 бессонная ночь");
    }

    @Test
    public void testSleeplessNights_EdgeCase_AfterNoon() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 14, 0),
                LocalDateTime.of(2025, 10, 1, 15, 0),
                SleepQuality.NORMAL
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                SleepQuality.GOOD
        ));

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.getValue(), "Не должно быть бессонных ночей");
    }

    @Test
    public void testSleeplessNights_EdgeCase_MultiDay() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 0),
                LocalDateTime.of(2025, 10, 4, 10, 0),
                SleepQuality.GOOD
        ));

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.getValue(), "Не должно быть бессонных ночей при многодневном сне");
    }

    @Test
    public void testAverageDuration_FractionalValue() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 10, 0),
                LocalDateTime.of(2025, 10, 1, 10, 1),
                SleepQuality.GOOD
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 11, 0),
                LocalDateTime.of(2025, 10, 1, 11, 2),
                SleepQuality.GOOD
        ));

        AverageDurationFunction function = new AverageDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("1.5", result.getValue(), "Среднее значение должно быть дробным");
    }
}