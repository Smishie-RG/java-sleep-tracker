package ru.yandex.practicum.sleeptracker.enums;

public enum ChronoType {
    OWL("Сова"),
    LARK("Жаворонок"),
    DOVE("Голубь");

    private final String russianName;

    ChronoType(String russianName) {
        this.russianName = russianName;
    }

    @Override
    public String toString() {
        return russianName;
    }
}