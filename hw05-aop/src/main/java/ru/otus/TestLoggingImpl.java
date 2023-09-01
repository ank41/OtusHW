package ru.otus;

import ru.otus.annotation.Log;

public class TestLoggingImpl implements  TestLogging{
    @Log
    @Override
    public void calculation(int i1) {

    }

    @Log
    @Override
    public void calculation(int i1, int i2) {

    }

    @Log
    @Override
    public void calculation(int i1, int i2, String s3) {

    }
}
