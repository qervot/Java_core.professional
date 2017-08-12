package ru.geekbrains.java3.lesson4.mt.p0_temp;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

//Домашнее задание:
//        1) Прочитать небольшой файл(около 50 байт) в байтовый массив и вывести этот массив в консоль;
//        2) Последовательно сшить 5 файлов в один (файлы также ~100 байт).
//        Может пригодиться следующая вещь:
//        ArrayList<FileInputStream> al = new ArrayList<>();
//        ...
//        Enumeration<FileInputStream> e = Collections.enumeration(al);
//        3) Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb),
//        вводим страницу, программа выводит ее в консоль (за страницу можно принять 1800 символов).
//        Время чтения файла должно находится в разумных пределах (программа не должна загружаться дольше 10 секунд),
//        ну и чтение тоже не должно занимать >5 секунд.

public class HomeWork {

    public static void main(String[] args) {
        //task1();
        //task2();
        task3();

    }

    public static void task1() {
        BufferedInputStream bis = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            bis = new BufferedInputStream(new FileInputStream("1.txt"));

            int x;
            while ((x = bis.read()) != -1) {
                out.write(x);
            }

            byte[] bytes = out.toByteArray();

//            byte[] bytes = new byte[bis.available()];
//            bis.read(bytes);

            System.out.println(Arrays.toString(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void task2() {
        ArrayList<BufferedInputStream> al = new ArrayList<>();

        Arrays.asList("1", "2", "3", "4", "5").forEach(e -> {
            try {
                al.add(new BufferedInputStream(new FileInputStream(e + ".txt")));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        Enumeration<BufferedInputStream> en = Collections.enumeration(al);
        SequenceInputStream s = new SequenceInputStream(en);
        int x;
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("out.txt"));
            while ((x = s.read()) != -1) {
                out.write(x);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void task3() {

        final int BATCH_SIZE = 1800;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер страницы: ");
        int pageNumber = scanner.nextInt();

        File bigFile = new File("warAndPeace1.txt");
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(bigFile, "r")) {
            byte bytes[] = new byte[BATCH_SIZE];
            randomAccessFile.seek(Math.abs(pageNumber -1) * BATCH_SIZE);
            randomAccessFile.read(bytes);

            String page = new String(bytes, Charset.forName("UTF-8"));

            System.out.println(page);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }



    }
}
