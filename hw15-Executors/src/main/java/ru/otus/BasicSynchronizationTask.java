package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BasicSynchronizationTask {
    private static final Logger logger = LoggerFactory.getLogger(BasicSynchronizationTask.class);
    private final BlockingQueue<String> queue;
    private final int count;

    public BasicSynchronizationTask(BlockingQueue<String> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    public static void go() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("Thread 1");
        queue.add("Thread 2");
        var basicSynchronization = new BasicSynchronizationTask(queue, 10);
        var thread1 = new Thread(() -> basicSynchronization.run(1));
        thread1.setName("Thread 1");

        var thread2 = new Thread(() -> basicSynchronization.run(1));
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();
    }

    private synchronized void run(int step) {
        int approach = step;
        while(step != 0) {
            try {
                var currentThreadName = Thread.currentThread().getName();
                while (!queue.peek().equals(currentThreadName)) {
                    this.wait();
                }
                var poll = queue.take();
                queue.put(poll);
                logger.info(String.valueOf(step));
                if (approach < count) {
                    ++step;
                    ++approach;
                } else {
                    --step;
                }
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
          BasicSynchronizationTask.go();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}