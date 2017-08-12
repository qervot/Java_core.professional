package com.geekbrains;

import java.io.Serializable;
import java.util.ArrayList;

public class Main implements Serializable {

    private static int a;
    private int b;

    public static void main(String[] args) {
        int c;

        System.out.println(c);

         //Тестирование класса в котором есть методы со всеми анотациями
        //RunTests.start("SimpleAnnotatedClass");

        // Тестирование класса в котором нет метода с анотацией BeforeSuite
        //RunTests.start("SimpleAnnotatedClass2");

        // Тестирование класса с 2 методами с анотацией BeforeSuite - получаем исключение
        //RunTests.start(a);
    }
}
