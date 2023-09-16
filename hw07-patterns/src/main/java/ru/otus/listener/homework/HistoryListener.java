package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> historyMessage;

    public HistoryListener() {
        this.historyMessage = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        historyMessage.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(historyMessage.get(id));
    }
}
