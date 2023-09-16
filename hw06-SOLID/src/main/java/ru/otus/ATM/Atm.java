package ru.otus.ATM;

import ru.otus.Banknote;

import java.util.Map;

public interface Atm {

    Map<Banknote, Integer> getMoney(int amount);


    void addMoney(Map<Banknote, Integer> money);


    int getBalance();
}
