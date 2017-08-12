package lesson7;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class MainClass {

    public static void main(String[] args) throws Exception {
        URL[] folderUrls = {new URL("file:/C:/5/")};
        Class c = URLClassLoader.newInstance(folderUrls).loadClass("Unknown");

        Object o = c.getConstructor(int.class, int.class).newInstance(7,5);
        Method m = c.getMethod("doSomething");
        m.invoke(o);
    }

    public static void main5(String[] args) throws Exception {
        Class mc = MyClass.class;
        Method[] methods = mc.getDeclaredMethods();

        for (Method o : methods) {
            if (o.isAnnotationPresent(MyAnnotation.class)) {
                o.invoke(null);
                System.out.println(o.getAnnotation(MyAnnotation.class).value());
                System.out.println(o.getAnnotation(MyAnnotation.class).data());
            }
        }
    }

    public static void main4(String[] args) throws Exception {
        Class cs = Dog.class;

        Dog dog = new Dog(5,7);
        Method[] methods = cs.getDeclaredMethods();

        for (Method o : methods) {
            System.out.println(o);
        }

        Method meth = cs.getDeclaredMethod("info", int.class);

        meth.invoke(dog, 42);

    }

    public static void main3(String[] args) throws Exception {
        Class cs = Dog.class;

        Dog dog = new Dog(5,7);
        Field f = cs.getDeclaredField("c");

        f.setAccessible(true);
        f.set(dog, 100);
        System.out.println(f.get(dog));

        Field[] fields = cs.getDeclaredFields();

        for (Field o : fields) {
            System.out.println(o.getName());
        }


    }

    public static void main2(String[] args) throws Exception{
        Class cs = String.class;
        Constructor[] constructors = cs.getConstructors();

        for (Constructor o : constructors) {
            System.out.println(o);
        }

        System.out.println("--------------------");

        Constructor cl = cs.getConstructors()[0];
        System.out.println(cl);
        Class[] paramsClasses =  cl.getParameterTypes();

        for(Class o : paramsClasses) {
            System.out.println(o);
        }


    }

    public static void main1(String[] args) throws Exception {

        Class c = Dog.class;
        Class[] interfaces = c.getInterfaces();
        for (Class inf : interfaces) {
            System.out.println(inf.getSimpleName());
        }

    }

}
