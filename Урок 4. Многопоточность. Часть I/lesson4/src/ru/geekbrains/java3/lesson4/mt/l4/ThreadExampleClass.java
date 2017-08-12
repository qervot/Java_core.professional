package ru.geekbrains.java3.lesson4.mt.l4;

public class ThreadExampleClass extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("obj: " + i);

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
