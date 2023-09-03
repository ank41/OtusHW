package ru.otus.ATM;

import ru.otus.Banknote;
import ru.otus.Cell.AtmCell;
import ru.otus.Cell.Cell;

import java.util.*;


public class AtmImpl implements Atm {

    private final List<Cell> cells = new ArrayList<>();
    private int balance = 0;

    public AtmImpl(Banknote[] banknotes) {
        for (var banknote : banknotes) {
            cells.add(new AtmCell(banknote));
        }
    }

    @Override
    public Map<Banknote, Integer> getMoney(int amount) {
        if (amount % 100 != 0) {
            throw new IllegalArgumentException("Сумма " + amount + " рублей не может быть выдана. Введите сумму, кратную 100");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Сумма " + amount + " рублей не может быть выдана.");
        }

        return findingMoney(amount);
    }

    @Override
    public void addMoney(Map<Banknote, Integer> banknotes) {
        final int amount = processAdd(banknotes);
        balance += amount;

    }

    @Override
    public int getBalance() {
        return balance;
    }

    private Map<Banknote, Integer> findingMoney(int amount) {

        final var result = new HashMap<Banknote, Integer>();
        int sum = 0;
        int requiredAmount = amount;

        for (var cell : cells) {

            var denomination = cell.getDenominate();
            var numberBanknotes = cell.getNumberOfBanknotes();

            if (requiredAmount >= denomination && numberBanknotes != 0) {
                int expectedBanknotes = requiredAmount / denomination;
                var realBanknotes = cell.getBanknotes(expectedBanknotes);
                sum += sumBanknotes(denomination, realBanknotes);
                requiredAmount = amount - sum;
                result.put(cell.getBanknoteType(), realBanknotes);
            }
        }

        if (sum == amount) {
            balance -= amount;
            return result;
        } else {
            addMoney(result);
            throw new IllegalArgumentException("Сумма " + amount + " рублей не может быть выдана.");
        }
    }


    private int processAdd(Map<Banknote, Integer> banknotes) {
        int result = 0;

        for (Cell cell : cells) {
            if (banknotes.containsKey(cell.getBanknoteType())) {
                result += sumBanknotes(cell.getDenominate(), cell.addBanknotes(banknotes.get(cell.getBanknoteType())));

            }
        }
        return result;
    }

    private int sumBanknotes(int denomination, int number) {
        return denomination * number;
    }

}
