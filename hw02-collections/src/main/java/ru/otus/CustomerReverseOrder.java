package ru.otus;


import java.util.LinkedList;
import java.util.List;

public class CustomerReverseOrder {

    // todo: 2. надо реализовать методы этого класса
    // надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private final LinkedList<Customer> customerList = new LinkedList<>();

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public Customer take() {
        return customerList.pollLast(); // это "заглушка, чтобы скомилировать"
    }
}
