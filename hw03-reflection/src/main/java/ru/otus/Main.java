package ru.otus;

import ru.otus.framework.runner.impl.DefaultRunner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
            new DefaultRunner().run("ru.otus.TestExample");
    }
}
