package ru.otus.proxy;

import ru.otus.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class DemoInvocationHandler implements InvocationHandler {
    private final Object instance;
    private final Set<String> methods = new HashSet<>();

    DemoInvocationHandler(Object instance) {
        this.instance = instance;

        for (Method method : instance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                methods.add(method.getName() + Arrays.toString(method.getParameterTypes()));
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String key = method.getName() + Arrays.toString(method.getParameterTypes());
        if (methods.contains(key)) {
            System.out.print("executed method:" + key);

            for (int n = 0; n < args.length; n++) {
                System.out.print(", param" + (n + 1) + "=" + args[n]);
            }
            System.out.println();
        }
        return method.invoke(instance, args);
    }



}
