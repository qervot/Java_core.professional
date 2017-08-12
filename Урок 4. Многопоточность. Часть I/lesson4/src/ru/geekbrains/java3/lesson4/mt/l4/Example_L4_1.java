package ru.geekbrains.java3.lesson4.mt.l4;

public class Example_L4_1 {

    public static void main(String[] args) {

        ThreadExampleClass t1 = new ThreadExampleClass();
        Thread t2 = new Thread(new RunnableExample());

        System.out.println("Begin");

        t1.start();
        t2.start();

        System.out.println("!!!!-------------------!!!");

        try {
            t1.join();
            System.out.println("aaaa-------------------");
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End");
    }
}
