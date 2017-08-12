package ru.geekbrains.java3.lesson4.mt.l4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class X10 {

    public static void main2(String[] args) {

        final int CLIENT_COUNT = 100000;
        CountDownLatch cdl = new CountDownLatch(CLIENT_COUNT);

        //ExecutorService serv = Executors.newFixedThreadPool(1000);
        //ExecutorService serv = Executors.newCachedThreadPool();
        MyTimer.start();
        for (int i = 0; i < CLIENT_COUNT; i++) {

            //старт потока тут
            new Thread(() -> {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                cdl.countDown();
            }).start();

        }

        //serv.shutdown();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyTimer.stopAndPrint();

    }

    public static void main(String[] args) {

        final int CLIENT_COUNT = 100000;
        CountDownLatch cdl = new CountDownLatch(CLIENT_COUNT);

        //ExecutorService serv = Executors.newFixedThreadPool(100);
        ExecutorService serv = Executors.newCachedThreadPool();
        MyTimer.start();
        for (int i = 0; i < CLIENT_COUNT; i++) {

            //старт потока тут
            serv.execute(() -> {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                cdl.countDown();
            });

        }

        serv.shutdown();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyTimer.stopAndPrint();

    }
}
