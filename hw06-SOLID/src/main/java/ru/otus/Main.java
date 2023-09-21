package ru.otus;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {




        List<Word> words = List.of(
                Word.of("large",-1),
                Word.of("phone",-6),
                Word.of("newspaper",2),
                Word.of("chocolate",-10),
                Word.of("connection",13),
                Word.of("engineering",10)
        );
        System.out.println("fffffff");
        System.out.println(words.stream()
                .filter(Predicate.not(Word::isBad))
                .max(Comparator.comparing(Word::getPriority))
                .map(Word::getValue)
                .orElse("Nothing found"));

    }
    public static class Word {
        private final String value;
        private final int priority;

        static Word of(String value, int priority){
            return new Word(value,priority);
        }

        public boolean isBad(){
            return  this.value.contains("la");
        }

        private Word(String value, int priority){
            this.value = value;
            this.priority = priority;
        }

        public String getValue() {
            return value;
        }

        public int getPriority() {
            return priority;
        }
    }

}