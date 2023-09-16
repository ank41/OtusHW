package ru.otus.processor.homework;

import java.time.LocalDateTime;

public class NowTimeImpl implements NowTime {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
