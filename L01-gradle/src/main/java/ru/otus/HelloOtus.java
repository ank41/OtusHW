/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.otus;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;

/**
 * To start the application: ./gradlew build java -jar
 * ./L01-gradle/build/libs/gradleHelloWorld-0.1.jar
 *
 * <p>To unzip the jar: unzip -l L01-gradle.jar unzip -l gradleHelloWorld-0.1.jar
 */
@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {

        List<Character> myList = Arrays.asList('H', 'E', 'L', 'L', 'O', 'G', 'E', 'E', 'K', 'S');

        List<List<Character>> lists = Lists.partition(myList, 3);

        for (List<Character> sublist : lists) {
            System.out.println(sublist);
        }
    }
}
