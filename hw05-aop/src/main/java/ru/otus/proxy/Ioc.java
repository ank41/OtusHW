package ru.otus.proxy;

import ru.otus.Demo;
import ru.otus.TestLogging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {
    private Ioc() {
    }

    public static Object createLoggedClass(Object clazz) {
        InvocationHandler handler = new DemoInvocationHandler(clazz);
        return Proxy.newProxyInstance(Demo.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }
}
