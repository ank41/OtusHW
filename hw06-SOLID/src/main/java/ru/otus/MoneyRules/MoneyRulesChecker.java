package ru.otus.MoneyRules;

//я не придумал лучше название
public interface MoneyRulesChecker {

    int getMinDenomination();

    boolean checkAmountFormat(int amount);

    boolean CheckBalance(int amount, int balance);

    boolean checkNumbersOfBanknotes(int numberBanknotes);

    boolean checkPayoutAmount(int requestAmount, int payoutAmount);
}
