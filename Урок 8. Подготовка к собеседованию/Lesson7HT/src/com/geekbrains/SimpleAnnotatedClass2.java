package com.geekbrains;

/**
 *
 * Класс для проверки. Не содержит методов с аннотацией BeforeSuite.
 * Должен нормально отрабатывать
 */
public class SimpleAnnotatedClass2 {

    @AfterSuite
    public void after(){
        System.out.println("AfterSuite run");
    }

    @Test(order = 3)
    public void test3(){
        System.out.println("Test3 run");
    }

    @Test(order = 4)
    public void test4(){
        System.out.println("Test4 run");
    }

    @Test(order = 1)
    public void test1(){
        System.out.println("Test1 run");
    }

    @Test(order = 3)
    public void test31(){
        System.out.println("Test3.1 run");
    }

    @BeforeSuite
    public void before(){
        System.out.println("BeforeSuite run");
    }
}
