package com.nicolas.lesson3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    static class Timer {
        static long t;
        static void start() {
            t = System.currentTimeMillis();
        }

        static void stopAndPrint() {
            System.out.println("time: " + (System.currentTimeMillis() - t));
        }
    }

    public static void main(String[] args) throws Exception {
        Cat cat1 = new Cat("Barsik");
        Cat cat2 = new Cat("Barsik3");

        Ball ball = new Ball();
        cat1.setBall(ball);
        cat2.setBall(ball);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cat.ser"));

        oos.writeObject(cat1);
        oos.writeObject(cat2);

        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cat.ser"));

        Cat cat11 = (Cat) ois.readObject();
        Cat cat21 = (Cat) ois.readObject();

        Ball ball1 = cat1.getBall();
        Ball ball2 = cat2.getBall();

        ois.close();

        cat11.info();
        cat21.info();

        System.out.println(ball1.getName());


    }

    //Установка кодировки при записи и чтении из файла
    public static void main12(String[] args) throws Exception {
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("1.txt"), StandardCharsets.UTF_8);
        InputStreamReader in = new InputStreamReader(new FileInputStream("1.txt"), StandardCharsets.UTF_8);
    }

    //Произвольный доступ к файлу
    public static void main11(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("1.txt", "rw");

        raf.seek(5);
        //System.out.println((char) raf.read());
        raf.write(65);
        raf.close();
    }

    //Последовательное чтение нескольких потоков как одного
    public static void main10(String[] args) throws Exception{
        ArrayList<FileInputStream> ali = new ArrayList<>();
        ali.add(new FileInputStream("a.txt"));
        ali.add(new FileInputStream("b.txt"));
        ali.add(new FileInputStream("c.txt"));

        SequenceInputStream sin = new SequenceInputStream(Collections.enumeration(ali));
        int x;
        while ((x = sin.read()) != -1) {
            System.out.print((char) x);
        }

        sin.close();
    }

    //Чтение файла с заданной кодировкой
    public static void main9(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("4.txt"), StandardCharsets.UTF_16)
        );

    }

    //"Трубы" запись с одного конца, чтение с другого
    public static void main8(String[] args) throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();

        in.connect(out);

        out.write(10);
        out.write(20);

        System.out.println(in.read());
        System.out.println(in.read());

    }

    //Байтовый поток
    public static void main7(String[] args) throws Exception {
        byte[] b = {1,2,3,4,5};
        ByteArrayInputStream in = new ByteArrayInputStream(b);

        int x;
        while ((x = in.read()) != -1) {
            System.out.print(x + " ");
        }

        in.close();

        System.out.println();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        for (int i = 0; i < 100; i++) {
            out.write(i);
        }

        out.close();

        System.out.println(Arrays.toString(out.toByteArray()));
    }

    //Запись примитивов
    public static void main6(String[] args) throws Exception {
        long a = 10000000L;
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream("3.txt"))
        );
        out.writeLong(a);
        out.close();

        DataInputStream in = new DataInputStream(new FileInputStream("3.txt"));
        System.out.println(in.readLong());

        in.close();
    }

    //Буфферизированный вывод
    public static void main5(String[] args) throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("2.txt"));
        out.write(65);
        //out.flush();
        out.close();
    }

    //Буфферизированное чтение
    public static void main4(String[] args) throws Exception {
        Timer.start();

        //FileInputStream in = new FileInputStream("str.txt");
        //byte[] bytes = new byte[in.available()];
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream("str.txt"));
        //String s = "";

        StringBuilder sb = new StringBuilder("");
        int x;
        //in.read(bytes);

//        for (byte bt : bytes) {
//            sb.append(bt);
//        }
        while ((x = bin.read()) != -1) {
            sb.append((char) x);
        }

        bin.close();

        Timer.stopAndPrint();

    }

    //Прямое побайтовое чтение/запись из/в файловый поток
    public static void main3(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("1.txt");

        for (int i = 0; i < 5; i++) {
            System.out.print(65 + i);
            out.write(65 + i);
        }

        out.close();

        System.out.println();

        FileInputStream in = new FileInputStream("1.txt");

        int x;
        while ((x = in.read()) != -1) {
            System.out.print((char) x);
        }

        in.close();
    }

    //Файлы и что с ними делать
    public static void main2(String[] args) throws Exception {
        File f = new File("1");
        //System.out.println(f.mkdirs());

        //System.out.println(f.exists());

        String[] fileNames = f.list((dir, name) -> {
            if (name.startsWith("1") && name.endsWith(".txt")) return true;
            return false;
        });

        for (String fn : fileNames) {
            System.out.println(fn);
        }

    }
}
