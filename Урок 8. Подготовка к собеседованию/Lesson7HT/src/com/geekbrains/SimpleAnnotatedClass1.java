package com.geekbrains;

/**
 *
 * Класс для проверки. При запуске тестирования должно подниматься исключение,
 * т.к. есть 2 метода с аннотацией BeforeSuite
 */
public class SimpleAnnotatedClass1 {

    //@BeforeSuite
    public void before(){
        System.out.println("BeforeSuite run");
    }

    @BeforeSuite
    public void before2(){
        System.out.println("before2");
    }

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

    public void testEmpty(){
        System.out.println("TestEmpty run");
    }

    @Test(order = 1)
    public void test1(){
        System.out.println("Test1 run");
    }

    @Test(order = 3)
    public void test31(){
        System.out.println("Test3.1 run");
    }
}
