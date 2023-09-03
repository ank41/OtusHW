package ru.otus.Cell;

import ru.otus.Banknote;

public abstract class Cell {

    private final Banknote banknote;

    public Cell(Banknote banknote) {
        this.banknote = banknote;
    }
    public abstract int addBanknotes(int number);

    public abstract int getBanknotes(int number);

    public abstract int getNumberOfBanknotes();

     public int getDenominate(){
     return banknote.getDenomination();
    }

     public Banknote getBanknoteType(){
        return banknote;
    }


}
