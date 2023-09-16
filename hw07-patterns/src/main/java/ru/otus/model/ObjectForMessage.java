package ru.otus.model;

import java.util.ArrayList;
import java.util.List;


public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() {
        var copy = new ObjectForMessage();
        if (this.getData() != null) {
            //не использую List.copyOf, т.к. возвращает иммутабельный лист, а это не то, что может ожидать пользователь
            //Но не уверен, что этот варинт лучше
            copy.setData(new ArrayList<>(this.getData()));
        }
        return copy;
    }

}
