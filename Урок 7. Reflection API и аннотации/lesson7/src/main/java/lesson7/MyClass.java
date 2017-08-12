package lesson7;

public class MyClass {

    @MyAnnotation(value = 555, data = 111)
    static void a() {
        System.out.println("a");
    }

    @MyAnnotation
    static void b() {
        System.out.println("b");
    }

    @MyAnnotation(value = 20)
    static void c() {
        System.out.println("c");
    }


}
