package ru.geekbrains.java3.lesson4.mt.l4;

public class WN {
    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("A");
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("B");
            }
        }).start();
    }
}
