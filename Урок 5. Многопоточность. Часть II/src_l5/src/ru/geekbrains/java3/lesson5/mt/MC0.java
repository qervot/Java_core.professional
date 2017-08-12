package ru.geekbrains.java3.lesson5.mt;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MC0 {

    public static void main(String[] args) {
        //ArrayList
        //Vector
        //CopyOnWriteArrayList
        //HashMap
        //ConcurrentHashMap

        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(10);
        abq.put("aaaa");
        abq.offer("asdfsdfdfs");
        abq.add("aadasd");

        abq.take();
        abq.poll();
        abq.remove();

        HashMap hm = new HashMap();
        Map mm = Collections.synchronizedMap(hm);

    }

    public static void main11(String[] args) {
        ReadWriteLock rwl = new ReentrantReadWriteLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    rwl.readLock().lock();
                    Thread.sleep(2000);
                    System.out.println("A");
                    rwl.readLock().unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }

        new Thread(()-> {
            rwl.writeLock().lock();
            try {
                Thread.sleep(1000);
                System.out.println("B1");
                rwl.writeLock().unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            rwl.writeLock().lock();
            try {
                Thread.sleep(1000);
                System.out.println("B2");
                rwl.writeLock().unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main10(String[] args) {

        ReentrantLock rl = new ReentrantLock();

        class A {
            void a1() {
                try {
                    if (rl.tryLock(5, TimeUnit.SECONDS)) {

                        try {
                            System.out.println("lock start");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            rl.unlock();
                        }

                    } else {
                        System.out.println("move out");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

        A aClass = new A();

        new Thread(() -> {
            aClass.a1();
        }).start();

        new Thread(() -> {
            aClass.a1();
        }).start();
    }

    public static void main9(String[] args) {

        ReentrantLock rl = new ReentrantLock();

        class A {
            void a1() {
                try {
                    rl.lock();
                    System.out.println("lock start");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            void a2() {
                try {
                    Thread.sleep(1000);
                    System.out.println("lock end");
                    rl.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        A aClass = new A();

        new Thread(() -> {
            aClass.a1();
            aClass.a2();
        }).start();

        new Thread(() -> {
            aClass.a1();
            aClass.a2();
        }).start();
    }

    public static void main8(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3);

        new Thread(() -> {
            try {
                Thread.sleep(1500);
                System.out.println("H1 ready");
                cb.await();
                System.out.println("H1 run");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println("H2 ready");
                cb.await();
                System.out.println("H2 run");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(900);
                System.out.println("H3 ready");
                cb.await();
                System.out.println("H3 run");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void main7(String[] args) {
        CountDownLatch cdl = new CountDownLatch(20);

        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            final int w = i;
            service.execute(() -> {
                try {
                    System.out.println("thread - " + w + "started");
                    Thread.sleep(1000);
                    cdl.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main out");
    }

    public static void main6(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Semaphore smp = new Semaphore(4);

        class MyTask {
            void a(int n) {
                try {
                    System.out.println("parallel task-start-" + n);
                    Thread.sleep(500);
                    System.out.println("parallel task-end-" + n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        MyTask mt = new MyTask();

        for (int i = 0; i < 10; i++) {
            final int w = i;
            service.execute(() -> {
                try {
                    mt.a(w);

                    smp.acquire();

                    System.out.println(w + " - start");
                    Thread.sleep(2000);
                    System.out.println(w + " - end");

                    smp.release();

                    mt.a(w);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
    }

    public static void main5(String[] args) {
        List<Callable<Object>> tasks = new ArrayList<>();
        tasks.add(() -> {
            Thread.sleep(500);
            System.out.println(1);
            return null;
        });

        tasks.add(() -> {
            Thread.sleep(4000);
            System.out.println(2);
            return null;
        });

        tasks.add(() -> {
            Thread.sleep(1000);
            System.out.println(3);
            return null;
        });

        tasks.add(() -> {
            Thread.sleep(2000);
            System.out.println(4);
            return null;
        });


        ExecutorService es = Executors.newFixedThreadPool(tasks.size());

        System.out.println("start");

        try {
            es.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            es.shutdown();
        }

        System.out.println("end");
    }

    public static void main4(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<String> res = service.submit(() -> {
            int x = 10 / 0;
            Thread.sleep(2000);
            return "ABS";
        });


        service.shutdown();

        System.out.println("1");
        System.out.println(res.get());
        System.out.println("2");

    }

    public static void main3(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<String> res = service.submit(() -> {
            Thread.sleep(2000);
            return "ABS";
        });

        FutureTask<String> ft = new FutureTask<String>(() -> {
            return "A";
        });

        FutureTask<String> ftres = (FutureTask<String>) service.submit(ft);

        service.shutdown();

        System.out.println("1");
        System.out.println(res.get());
        System.out.println("2");

    }

    public static void main1(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                // .....
                // .....
                // .....
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            System.out.println("Thread end");
        });

        thread.start();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("wake up neo!");

        thread.interrupt();
    }


    public static void main2(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);


        service.execute(() -> System.out.println("A"));

        service.execute(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        });

        service.shutdown();

        try {
            service.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");


    }
}
