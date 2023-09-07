package ru.otus.MoneyRules;

public class MoneyRulesCheckerATM implements MoneyRulesChecker {

    private final int minDenomination;

    public MoneyRulesCheckerATM(int minDenomination) {
        this.minDenomination = minDenomination;
    }

    @Override
    public boolean checkAmountFormat(int amount) {
        return amount % minDenomination == 0;
    }

    @Override
    public boolean CheckBalance(int amount, int balance) {
        return balance >= amount;
    }

    @Override
    public boolean checkNumbersOfBanknotes(int numberBanknotes) {
        return numberBanknotes >= 0;
    }

    @Override
    public boolean checkPayoutAmount(int requestedPayment, int payment) {
        return requestedPayment == payment;
    }

    @Override
    public int getMinDenomination() {
        return minDenomination;
    }
}
