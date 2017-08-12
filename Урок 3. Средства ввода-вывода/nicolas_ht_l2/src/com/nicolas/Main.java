package com.nicolas;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private static Connection connection;

    private static PreparedStatement psAddProduct;
    private static PreparedStatement psUpdateProduct;
    private static PreparedStatement psSelectCost;
    private static PreparedStatement psSelectCostRange;

    private static Consumer<String> cons = val234234234 -> {
        System.out.println(val234234234);
    };

    public static void main(String[] args) throws SQLException {

        // Пример использования внедрения собственных функций, в определенный класс, который может принять внешнюю функцию
        WorkClass<String> wk = new WorkClass<>();
        wk.setMyCallBack(cons); //установили нашу функцию

        wk.someMeth("Моя строка"); //тут, в качестве синтетического примера, вызываем нашу функцию из стороннего класса


        //------------------------ Это методы домашнего задания из урока 2 ----------------------
        initBase();
        //prepareCommands();  // -- использование лямбда функций.

        processActions();

        disconnect();
    }

    /**
     * Подготовка переменных
     *
     * @throws SQLException ошибки
     */
    private static void prepareStatements() throws SQLException {

        psAddProduct = connection
                .prepareStatement("INSERT INTO products (product_id, title, cost) VALUES (?, ?, ?)");

        psUpdateProduct = connection
                .prepareStatement("UPDATE products SET cost = ? WHERE title = ?");

        psSelectCost = connection
                .prepareStatement("SELECT cost FROM products WHERE title = ?");

        psSelectCostRange = connection
                .prepareStatement("SELECT product_id, title, cost FROM products WHERE cost between ? and ?");
    }

    /**
     * Пример хранения функций в лямбдах
     */
    private static void prepareCommands() {

        //мап, который будет хранить наши анонимные функции, ключ - команда, значение - анонимный класс/лямбда функция, которую требуется выполнить для определенной команды
        HashMap<String, Consumer<String>> manyMethods = new HashMap<>();

        /*
        Создаем нашу функцию для получения стоимости товара, на вход принимается один строковый параметр
         */
        Consumer<String> getCost = userInput -> {

            String itemName = userInput.split(" ")[1];

            try {
                psSelectCost.setString(1, itemName);

                ResultSet rs = psSelectCost.executeQuery();

                if (rs.next()) {
                    int cost = rs.getInt("cost");
                    System.out.println("Цена товара " + cost);
                } else {
                    System.out.println("Такого товара нет");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };

        manyMethods.put("one", getCost); //положили нашу функцию в мап

        manyMethods.get("one").accept("/цена товар545"); //выполнили функцию, получив ее по ключу

        /*
        Пример использования собственный лямбда функций или, как они еще называются, реализаций функциональных интерфейсов
        MyFuncInterface - наш самописный функциональный интерфейс, который находится в самом низу кода
         */
        MyFuncInterface<Integer, Integer> aaaa = (a1, a2) -> {
            return "MyStr";
        };

        //Исполнение функции, getTransResult - имя единственного абстрактного метода в нашем функциональном интерфейсе
        String retValue = aaaa.getTransResult(10,50);
    }

    /**
     * Инициализация базы
     *
     * @throws SQLException ошибки
     */
    private static void initBase() throws SQLException {
        connect();

        connection.setAutoCommit(false);

        String createTableQuery = "CREATE TABLE IF NOT EXISTS products (\n" +
                "    id         INTEGER        PRIMARY KEY AUTOINCREMENT,\n" +
                "    product_id INTEGER        UNIQUE ON CONFLICT ROLLBACK,\n" +
                "    title      TEXT,\n" +
                "    cost       INTEGER \n" +
                ");";

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createTableQuery);

        stmt.executeUpdate("DELETE FROM products");

        prepareStatements();

        for (int i = 1; i < 10000; i++) {
            psAddProduct.setInt(1, i);
            psAddProduct.setString(2, "товар" + i);
            psAddProduct.setInt(3, i * 10);

            psAddProduct.addBatch();
        }

        psAddProduct.executeBatch();

        //connection.commit();
        connection.setAutoCommit(true);

    }

    /**
     * Обработка ввода пользователя
     *
     * @throws SQLException ошибки
     */
    private static void processActions() throws SQLException {
        System.out.print("Чтобы узнать цену товара введите ");
        System.out.println("/цена товар545");

        System.out.print("\nЧтобы изменить цену товара введите ");
        System.out.println("/сменитьцену товар10 10000");

        System.out.print("\nЧтобы чтобы вывести товары в заданнмо зеновом диапазоне введите ");
        System.out.println("/товарыпоцене 100 600");

        System.out.println("\nДля выхода введите /exit");

        boolean canExit = false;
        Scanner scanner = new Scanner(System.in);

        while (!canExit) {
            String userInput = scanner.nextLine();
            String userCommand = userInput.split(" ")[0];

            switch (userCommand) {
                case "/цена":
                    getCost(userInput);
                    break;
                case "/сменитьцену":
                    setCost(userInput);
                    break;
                case "/товарыпоцене":
                    getCostRange(userInput);
                    break;
                case "/exit": //nicolas:> тут выходим
                    canExit = true;
                    break;
                default:
                    System.out.println("Введена неправильная команда.");
            }
        }
    }

    /**
     * Получить цену товара по имени
     *
     * @param userInput входная строка
     * @throws SQLException ошибки
     */
    private static void getCost(String userInput) throws SQLException {

        String itemName = userInput.split(" ")[1];

        psSelectCost.setString(1, itemName);

        ResultSet rs = psSelectCost.executeQuery();

        if (rs.next()) {
            int cost = rs.getInt("cost");
            System.out.println("Цена товара " + cost);
        } else {
            System.out.println("Такого товара нет");
        }

    }

    /**
     * Получить товары по заданному диапазону цен
     *
     * @param userInput входная строка
     * @throws SQLException ошибки
     */
    private static void getCostRange(String userInput) throws SQLException {
        int lowerRange = Integer.parseInt(userInput.split(" ")[1]);
        int upperRange = Integer.parseInt(userInput.split(" ")[2]);

        psSelectCostRange.setInt(1, lowerRange);
        psSelectCostRange.setInt(2, upperRange);

        ArrayList<String> titlesList = new ArrayList<>();

        ResultSet rs = psSelectCostRange.executeQuery();
        while (rs.next()) {
            titlesList.add(rs.getString("title"));
        }

        if (!titlesList.isEmpty()) {
            System.out.println("Товары в заданном ценовом диапазоне: " + titlesList.toString());
        } else {
            System.out.println("Нет товаров в заданном диапазоне");
        }

    }

    /**
     * Установить цену товара по имени
     *
     * @param userInput входная строка
     * @throws SQLException ошибки
     */
    private static void setCost(String userInput) throws SQLException {
        String itemName = userInput.split(" ")[1];
        Integer itemCost = Integer.parseInt(userInput.split(" ")[2]);

        psUpdateProduct.setInt(1, itemCost);
        psUpdateProduct.setString(2, itemName);

        psUpdateProduct.executeUpdate();
    }

    /**
     * Подключение к базе
     */
    private static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MainDB.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отключение от базы
     */
    private static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Наш самописный самый простой функциональный интерфейс, реализация которого является лямбда функцией.
     * Как видно, интерфейс является дженериком
     * @param <T>
     * @param <E>
     */
    @FunctionalInterface
    public interface MyFuncInterface<T, E> {

        String getTransResult(T fv, E sv);
    }
}
