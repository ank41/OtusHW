package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.ATM.Atm;
import ru.otus.ATM.AtmImpl;

import java.util.HashMap;
import java.util.Map;

import static ru.otus.Banknote.*;

public class TestATM {

    private static Atm atm;

    @BeforeEach
    void preparationATM() {
        atm = new AtmImpl(Banknote.values());
    }
    private static void fillingATM() {
        //95 000 рублей
        atm.addMoney(Map.of(
                HUNDRED, 100,
                TWO_HUNDRED, 100,
                FIVE_HUNDRED, 10,
                THOUSAND, 10,
                FIVE_THOUSAND, 10));
    }

    @Test
    void testZeroBalance() {
        //пытаемся снять деньги с 0 баланса
        RuntimeException fail = Assertions.assertThrows(IllegalArgumentException.class, () -> atm.getMoney(1000));
        Assertions.assertEquals("Сумма " + 1000 + " рублей не может быть выдана.", fail.getMessage());
    }

    @Test
    void testIncorrectRequestFormat() {
        //пытаемся снять некорректную сумму
        RuntimeException fail = Assertions.assertThrows(IllegalArgumentException.class, () -> atm.getMoney(1150));
        Assertions.assertEquals("Сумма " + 1150 + " рублей не может быть выдана. Введите сумму, кратную 100", fail.getMessage());
    }

    @Test
    void testAdding() {
        fillingATM();
        Assertions.assertEquals(95000, atm.getBalance());
    }

    @Test
    void testAtmGetMoney() {
        fillingATM();
        Map<Banknote, Integer> expectedResult = new HashMap<>(Map.of(
                HUNDRED, 50,
                TWO_HUNDRED, 100,
                FIVE_HUNDRED, 10,
                THOUSAND, 10,
                FIVE_THOUSAND, 10));

        Assertions.assertEquals(expectedResult, atm.getMoney(90000));
        Assertions.assertEquals(5000, atm.getBalance());
        Assertions.assertThrows(IllegalArgumentException.class, () -> atm.getMoney(10000));
    }

    @Test
    void testNotEnoughBanknotes() {
        // 10000 руб
        atm.addMoney(Map.of(
                FIVE_THOUSAND, 2
        ));
        // имеются только банкноты номиналом крупнее нужной суммы
        Assertions.assertThrows(IllegalArgumentException.class, () -> atm.getMoney(1000));
        //баланс не должен измениться
        Assertions.assertEquals(10000, atm.getBalance());
    }
}
