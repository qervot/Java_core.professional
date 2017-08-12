package ru.geekbrains.java3.lesson4.mt.l4;

public class Counter {

    int c;

    public Counter() {
        c = 0;
    }

    public int value() {
        return c;
    }

    public void inc() { c++; }
    public void dec() { c--; }
}
