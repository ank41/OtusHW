package ru.otus.ATM;

import ru.otus.Banknote;
import ru.otus.Cell.AtmCell;
import ru.otus.Cell.Cell;
import ru.otus.MoneyRules.MoneyRulesChecker;

import java.util.*;


public class AtmImpl implements Atm {

    private final Set<Cell> cellsTreeSet = new TreeSet<>((o1, o2) -> {
        var denO1 = o1.getBanknoteType().getDenomination();
        var denO2 = o2.getBanknoteType().getDenomination();
        return Integer.compare(denO2, denO1);
    });
    private final MoneyRulesChecker moneyRulesChecker;

    public AtmImpl(List<Banknote> banknotes, MoneyRulesChecker moneyRulesChecker) {
        for (var banknote : banknotes) {
            cellsTreeSet.add(new AtmCell(banknote));
        }
        this.moneyRulesChecker = moneyRulesChecker;
    }

    @Override
    public Map<Banknote, Integer> getMoney(int amount) {
        var balance = getBalance();
        if (!moneyRulesChecker.checkAmountFormat(amount)) {
            throw new IllegalArgumentException("Сумма " + amount + " рублей не может быть выдана. Введите сумму, кратную " + moneyRulesChecker.getMinDenomination());
        }
        if (!moneyRulesChecker.CheckBalance(amount, balance)) {
            throw new IllegalArgumentException("Сумма " + amount + " рублей не может быть выдана.");
        }

        return findingMoney(amount);
    }

    @Override
    public void addMoney(Map<Banknote, Integer> banknotes) {
        checkCorrectNumbersBanknotes(banknotes);
        processAdd(banknotes);
    }

    @Override
    public int getBalance() {
        int result = 0;
        for (Cell cell : cellsTreeSet) {
            result += sumBanknotes(cell.getBanknoteType().getDenomination(), cell.getNumberOfBanknotes());
        }
        return result;
    }

    private Map<Banknote, Integer> findingMoney(int amount) {

        final var result = new HashMap<Banknote, Integer>();
        int sum = 0;
        int requiredAmount = amount;

        for (var cell : cellsTreeSet) {

            var denomination = cell.getBanknoteType().getDenomination();
            var numberBanknotes = cell.getNumberOfBanknotes();

            if (requiredAmount >= denomination && numberBanknotes != 0) {
                int expectedBanknotes = requiredAmount / denomination;
                var realBanknotes = cell.getBanknotes(expectedBanknotes);
                sum += sumBanknotes(denomination, realBanknotes);
                requiredAmount = amount - sum;
                result.put(cell.getBanknoteType(), realBanknotes);
            }
        }

        if (moneyRulesChecker.checkPayoutAmount(amount, sum)) {
            return result;
        } else {
            addMoney(result);
            throw new IllegalArgumentException("Сумма " + amount + " рублей не может быть выдана.");
        }
    }


    private void processAdd(Map<Banknote, Integer> banknotes) {

        for (Cell cell : cellsTreeSet) {
            if (banknotes.containsKey(cell.getBanknoteType())) {
                cell.addBanknotes(banknotes.get(cell.getBanknoteType()));
            }
        }

    }

    private int sumBanknotes(int denomination, int number) {
        return denomination * number;
    }

    private void checkCorrectNumbersBanknotes(Map<Banknote, Integer> banknotes) {
        for (Cell cell : cellsTreeSet) {
            if (banknotes.containsKey(cell.getBanknoteType())) {
                if (!moneyRulesChecker.checkNumbersOfBanknotes(banknotes.get(cell.getBanknoteType()))) {
                    throw new IllegalArgumentException("Отрицательное число банкнот номиналом " + cell.getBanknoteType().getDenomination());
                }
            }
        }

    }


}
