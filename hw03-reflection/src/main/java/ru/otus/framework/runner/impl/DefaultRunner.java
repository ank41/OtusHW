package ru.otus.framework.runner.impl;

import ru.otus.framework.annotation.After;
import ru.otus.framework.annotation.Before;
import ru.otus.framework.annotation.Test;
import ru.otus.framework.runner.Runner;
import ru.otus.framework.utils.ReflectionUtils;
import ru.otus.framework.utils.TestResult;

import java.lang.reflect.Method;
import java.util.Objects;

public class DefaultRunner implements Runner {


    @Override
    public void run(String className) throws ClassNotFoundException {

        TestResult testResult = new TestResult();

        final Class<?> underTestClass = Class.forName(className);
        final Method[] methods = Objects.requireNonNull(underTestClass).getMethods();
        final Method[] testMethods = ReflectionUtils.filterMethodsByAnnotation(methods, Test.class);
        final Method beforeMethod = ReflectionUtils.getMethodByAnnotation(methods, Before.class);
        final Method afterMethod = ReflectionUtils.getMethodByAnnotation(methods, After.class);

        for (Method testMethod : testMethods) {
            executeTest(underTestClass, beforeMethod, afterMethod, testMethod, testResult);
        }

        testResult.showResultTests();

    }


    private <T> void executeTest(final Class<T> clazz, final Method beforeMethod, final Method afterMethod, final Method testMethod, TestResult testResult) {
        try {
            final T testObject = clazz.getDeclaredConstructor().newInstance();

            if (!Objects.isNull(beforeMethod)) {
                beforeMethod.invoke(testObject);
            }

            testMethod.invoke(testObject);

            if (!Objects.isNull(beforeMethod)) {
                afterMethod.invoke(testObject);
            }


        } catch (Exception e) {
            testResult.increaseFailedTest();
        } finally {
            testResult.increaseTotalTest();
        }
    }
}
