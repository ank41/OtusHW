package ru.otus;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstEntry = map.firstEntry();
        Customer key = firstEntry.getKey();
        return Map.entry(new Customer(key.getId(), key.getName(), key.getScores()), firstEntry.getValue());

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> result = map.higherEntry(customer);
        return result != null ? Map.entry(new Customer(result.getKey()), result.getValue()) : null;

    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}