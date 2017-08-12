package com.geekbrains;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class RunTests {

    /**
     * Перегруженный публичный метод для запуска тестирования класса
     *
     * @param obj Класс для тестирования
     */
    public static void start(Class obj) {
        getAnnotateAndRun(obj);
    }

    /**
     * Перегруженный публичный метод для запуска тестирования класса
     *
     * @param objFullName полное наименование класса
     */
    public static void start(final String objFullName) {
        try {
            getAnnotateAndRun(Class.forName("com.geekbrains." + objFullName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Собственно сам запуск тестирования класса
     *
     * @param testedObj тестируемый класс
     */
    private static void getAnnotateAndRun(Class testedObj) {
        Method beforeMethod = null;
        Method afterMethod = null;
        // получаем методы класса
        ArrayList<Method> methodsList = new ArrayList<>();

        for (Method method : testedObj.getDeclaredMethods()) {

            if (method.getDeclaredAnnotations().length == 0) {
                continue;
            }

            if (method.getDeclaredAnnotation(BeforeSuite.class) != null) {

                if (beforeMethod != null) {
                    throw new RuntimeException("Класс " + testedObj.getSimpleName()
                            + ". Кол-во аннотаций BeforeSuite больше 1");
                }

                beforeMethod = method;
                continue;
            }

            if (method.getDeclaredAnnotation(AfterSuite.class) != null) {
                if (afterMethod != null) {
                    throw new RuntimeException("Класс " + testedObj.getSimpleName()
                            + ". Кол-во аннотаций AfterSuite больше 1");
                }
                afterMethod = method;
                continue;
            }

            if (method.getDeclaredAnnotation(Test.class).order() > 10
                    || method.getDeclaredAnnotation(Test.class).order() < 1) {

                System.out.println("Класс " + testedObj.getSimpleName() + ". Метод имеет некорректный приоритет");
                continue;
            }

            methodsList.add(method);
        }

        methodsList.sort((method1, method2) -> {
            int order1 = method1.getDeclaredAnnotation(Test.class).order();
            int order2 = method2.getDeclaredAnnotation(Test.class).order();
            return Integer.compare(order2, order1);
        });

        if (beforeMethod != null) {
            methodsList.add(0, beforeMethod);
        }

        if (afterMethod != null) {
            methodsList.add(afterMethod);
        }

        // Создаем экземпляр класса для запуска методов
        Object obj = null;
        try {
            obj = testedObj.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // немного красивости
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        // Запуск методов
        try {
            for (Method method : methodsList) {
                System.out.print(df.format(Calendar.getInstance().getTime())
                        + " :: Run " + testedObj.getSimpleName() + "." + method.getName() + ": ");
                method.invoke(obj);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
