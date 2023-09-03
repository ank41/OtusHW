package ru.otus.Cell;

import ru.otus.Banknote;

public class AtmCell extends Cell {

    private int number;

    public AtmCell(Banknote banknote) {
        super(banknote);
    }

    @Override
    public int addBanknotes(int number) {
        this.number += number;
        return number;
    }

    @Override
    public int getBanknotes(int numberBanknotes) {
        int result;
        if (number >= numberBanknotes) {
            number -= numberBanknotes;
            result = numberBanknotes;
        } else {
            result = number;
            number = 0;
        }

        return result;
    }

    @Override
    public int getNumberOfBanknotes() {
        return number;
    }

    @Override
    public int getDenominate() {
        return super.getDenominate();
    }

    @Override
    public Banknote getBanknoteType() {
        return super.getBanknoteType();
    }


}
