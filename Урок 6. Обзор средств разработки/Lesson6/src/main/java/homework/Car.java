package homework;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    private static int LOCAL_CARS_COUNT;

    static {
        LOCAL_CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier startBarrier;
    private CountDownLatch cdl;

    private static AtomicBoolean ab = new AtomicBoolean(false);
    private static volatile int isWinner = 0;

    private static Object o = new Object();

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier startBarrier, CountDownLatch cdl) {
        this.race = race;
        this.speed = speed;
        this.cdl = cdl;
        this.startBarrier = startBarrier;
        LOCAL_CARS_COUNT++;
        this.name = "Участник #" + LOCAL_CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            startBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        cdl.countDown();

        if (isWinner++ == 0) {
            System.out.println(name + " - WIN ++");
        }

        if (cdl.getCount() == LOCAL_CARS_COUNT - 1) {
            System.out.println(name + " - WIN cdl");
        }

        //определение победителя

        if (!ab.getAndSet(true)) {
            System.out.println(name + " - WIN atomic");
        }

//        synchronized (o) {
//            cdl.countDown();
//            if(cdl.getCount() == ru.geekbrains.lesson6.MainClass.CARS_COUNT - 1) {
//                System.out.println(name + " - WIN");
//            }
//        }


    }
}
