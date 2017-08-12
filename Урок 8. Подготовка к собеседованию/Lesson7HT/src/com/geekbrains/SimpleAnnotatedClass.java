package com.geekbrains;

public class SimpleAnnotatedClass {

    @BeforeSuite
    public void before(){
        System.out.println("BeforeSuite run");
    }

//    @BeforeSuite
//    public void before2(){
//        System.out.println("before2");
//    }

    @AfterSuite
    public void after(){
        System.out.println("AfterSuite run");
    }

    @Test(order = 3)
    public void test3(int a){
        System.out.println("Test3 run");
    }

    @Test(order = 4)
    public void test4(){
        System.out.println("Test4 run");
    }

    @Test
    public void test5(){
        System.out.println("Test1 run");
    }

    @Test(order = 3)
    public void test31(){
        System.out.println("Test3.1 run");
    }

    @Test(order = 19)
    public void testSkip(){
        System.out.println("TestSkip run");
    }
}
