package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorExceptionAtEvenSecond implements Processor {

    private final NowTime nowService;

    public ProcessorExceptionAtEvenSecond(NowTime nowService) {
        this.nowService = nowService;
    }

    @Override
    public Message process(Message message) {
        if (nowService.now().getSecond() % 2 == 0) {
            System.out.println("Четная секунда");
            throw new RuntimeException();
        }
        return message;
    }
}
