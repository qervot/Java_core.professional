package ru.geekbrains.java3.lesson4.mt.l4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example_L4_3 {
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(11);

        System.out.println("Main started");

        //ExecutorService serv = Executors.newCachedThreadPool();
        //ExecutorService serv = Executors.newSingleThreadExecutor();
        ExecutorService serv = Executors.newFixedThreadPool(5);
        MyTimer.start();
        for (int i = 0; i < 10; i++) {
            final int w = i;

            //старт потока тут
            serv.execute(() -> {
                System.out.println(w + " - Begin");
                try {
                    Thread.sleep(100 + (int) (3000 * Math.random()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(w + " - End");
                cdl.countDown();
            });

        }

        serv.execute(() -> {
            System.out.println("Other code");
            try {
                Thread.sleep(1000);
                System.out.println("Other code ended");
                cdl.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        serv.shutdown();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyTimer.stopAndPrint();

        System.out.println("Main ended");
    }
}
