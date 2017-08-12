package ru.geekbrains.java3.lesson5.mt.homework;

public class Task3 {

    public static void main(String[] args) {
        MFU mfu = new MFU();

        new Thread(() -> {
            mfu.print("one-print", 10);
            mfu.scan("one-scan", 10);
        }).start();

        new Thread(() -> {
            mfu.print("two-print", 10);
            mfu.scan("two-scan", 10);
        }).start();

        new Thread(() -> {
            mfu.print("three-print", 10);
            mfu.scan("three-scan", 10);
        }).start();
    }

    static class MFU {
        Object printLock = new Object();
        Object scanLock = new Object();

        public void print(String doc, int n) {
            synchronized (printLock) {
                System.out.println();
                System.out.printf("Начало печати документа: %s ", doc);
                System.out.println();
                System.out.print("Печатается страница:");
                for (int i = 0; i < n; i++) {
                    System.out.print(" " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
        }

        public void scan(String doc, int n) {
            synchronized (scanLock) {
                System.out.println();
                System.out.printf("Начало сканирования документа: %s ", doc);
                System.out.println();
                System.out.print("Сканируется страница:");
                for (int i = 0; i < n; i++) {
                    System.out.print(" " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
        }

    }
}
