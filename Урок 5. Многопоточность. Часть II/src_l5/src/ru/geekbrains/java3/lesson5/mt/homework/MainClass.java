package ru.geekbrains.java3.lesson5.mt.homework;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentSkipListMap;

public class MainClass {
// Домашнее задание:
// 1) Создать три потока, каждый из которых выводит определенную букву(A, B и C), порядок должен быть именно ABCABCABC...
// (просто создав потоки и запустив их, Вы не сможете это гарантировать), если не сможете сделать сами по примеру из исходников,
// просто разберите код из решения
// 2) Написать совсем небольшой метод, в котором несколько потоков построчно пишут данные в файл(штук по 20 записей, с периодом в 50 мс)
// 3) Написать класс МФУ на котором возможны одновременная печать и сканирование документов, при этом нельзя
// одновременно печатать два документа или сканировать(при печати в консоль выводится сообщения "отпечатано 1, 2, 3,... страницы",
// при сканировании то же самое только "отсканировано...", вывод в консоль все также с периодом в 50 мс.)
    public static void main(String[] args) {

        class TextWriter implements Runnable {
            private PrintWriter pw;
            private int x;

            public TextWriter(PrintWriter pw, int x) {
                this.pw = pw;
                this.x = x;
            }

            @Override
            public void run() {
                for (int i = 0; i < x; i++) {
                    try {
                        Thread.sleep(50);
                        pw.println(x);
                        pw.flush();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        PrintWriter pw;
        try {
            pw = new PrintWriter("x1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Thread t1 = new Thread(new TextWriter(pw, 5));
        Thread t2 = new Thread(new TextWriter(pw, 10));
        Thread t3 = new Thread(new TextWriter(pw, 15));

        try {
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            t3.join();

            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
