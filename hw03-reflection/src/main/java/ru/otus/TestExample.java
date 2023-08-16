package ru.otus;

import ru.otus.framework.annotation.After;
import ru.otus.framework.annotation.Before;
import ru.otus.framework.annotation.Test;

public class TestExample {

    //Счетчик всегда должен быть равен 0
    private int startCounter = 0;

    @Before
    public void before() {
        System.out.println("start test method number " + startCounter++);
    }

    @Test
    public void test1() {
        System.out.println("Test 2");
    }

    @Test
    public void failedTest() {
        System.out.println("failed test");
        throw new RuntimeException();

    }

    @Test
    public void test3() {
        System.out.println("test 3");
    }


    @Test
    public void test4() {
        System.out.println("Счетчик все еще 0");
    }

    @After
    public void after() {
        System.out.println("after");
    }
}
