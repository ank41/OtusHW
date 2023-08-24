package ru.otus.proxy;

import ru.otus.Demo;
import ru.otus.TestLogging;
import ru.otus.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {
    private Ioc() {}

    public static TestLogging createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Demo.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }
}
