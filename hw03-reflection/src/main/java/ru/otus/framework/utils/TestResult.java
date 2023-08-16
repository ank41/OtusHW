package ru.otus.framework.utils;

public class TestResult {
    private int executed;
    private int failed;

    private int getCountTotalTest() {
        return executed;
    }

    private int getCountFailedTest() {
        return failed;
    }

    private int getCountPassedTest() {
        return getCountTotalTest() - getCountFailedTest();
    }

    public void increaseTotalTest() {
        executed++;
    }

    public void increaseFailedTest() {
        failed++;
    }

    public void showResultTests() {
        var TESTS_FINISHED = "All tests are finished";
        System.out.println(TESTS_FINISHED);
        var TEST_RESULT = "executed: %s, passed: %s, failed: %s";
        System.out.println(String.format(TEST_RESULT, getCountTotalTest(), getCountPassedTest(), getCountFailedTest()));
    }

}
