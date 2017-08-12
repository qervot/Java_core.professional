package ru.geekbrains.java3.lesson4.mt.l4;

public class MyTimer {
    private static long t;

    public static void start() {
        t = System.currentTimeMillis();
    }

    public static void  stopAndPrint() {
        System.out.println("time: " + (System.currentTimeMillis() - t));
    }
}
