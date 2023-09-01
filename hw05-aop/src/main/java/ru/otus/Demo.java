package ru.otus;

import ru.otus.proxy.Ioc;

public class Demo {
    public static void main(String[] args) {
        var testLogging = (TestLogging) Ioc.createLoggedClass(new TestLoggingImpl());

        testLogging.calculation(124);
        testLogging.calculation(5435, 2213);
        testLogging.calculation(450, 2110, "String");
    }
}
