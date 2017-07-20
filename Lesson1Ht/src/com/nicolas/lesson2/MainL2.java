package com.nicolas.lesson2;

import java.sql.*;

public class MainL2 {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;


    public static void main2(String[] args) throws Exception {
        connect();

        stmt = connection.createStatement();

        //stmt.execute("INSERT INTO STUDENTS (Name, Score) VALUES ('Alex', 50)");
        //int cnt = stmt.executeUpdate("DELETE FROM STUDENTS WHERE Score > 45");

        //int cnt = stmt.executeUpdate("UPDATE Students SET Score = 50 WHERE Name = 'Max'");

        //stmt.execute("DELETE FROM Students");

        //ResultSet rs = stmt.executeQuery("SELECT * FROM Students");

        //INSERT SELECT UPDATE DELETE  -  CRUD

//        while (rs.next()) {
//            System.out.println(rs.getInt("id")
//                    + " " + rs.getString("NaMe")
//                    + " " + rs.getInt("SCoRE"));
//        }

        //System.out.println("cnt = " + cnt);

        disconnect();
    }

    public static void main3(String[] args) throws Exception {
        connect();

        stmt = connection.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS Students (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name  TEXT,\n" +
                "    score INTEGER);");

        stmt.execute("DELETE FROM Students");

//        String s1 ="a", s2 = "b";
//        stmt.execute("SELECT NICK FROM USERS WHERE LOGIN = '" + s1 + "' AND PASSWORD = '" + s2 + "'");
        // s1 = admin /*

        connection.setAutoCommit(false);
        ps = connection.prepareStatement("INSERT INTO Students (Name, Score) VALUES (?, ?)");
        long t = System.currentTimeMillis();

        Savepoint sp = connection.setSavepoint();
        connection.rollback(sp);

        for (int i = 0; i < 10000; i++) {
            //stmt.executeUpdate("INSERT INTO Students (Name, Score) VALUES ('Bob" + (i + 1) + "', 5)");
            ps.setString(1, "Bob" + (i + 1));
            ps.setInt(2, (i + 1) * 10);
            //ps.executeUpdate();
            ps.addBatch();
        }

        ps.executeBatch();

        connection.commit();
        //connection.setAutoCommit(true);
        System.out.println("time: " + (System.currentTimeMillis() - t));

        /*ResultSet rs = stmt.executeQuery("SELECT * FROM Students");

        while (rs.next()) {
            System.out.println(rs.getInt("id")
                    + " " + rs.getString("NaMe")
                    + " " + rs.getInt("SCoRE"));
        }*/

        disconnect();
    }

    public static void main(String[] args) throws Exception {
        connect();

        stmt = connection.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS Students (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name  TEXT,\n" +
                "    score INTEGER);");

        stmt.execute("DELETE FROM Students");

        //connection.setAutoCommit(false);
        ps = connection.prepareStatement("INSERT INTO Students (Name, Score) VALUES (?, ?)");

        ps.setString(1, "Bob");
        ps.setInt(2, 10);
        ps.executeUpdate();

        Savepoint sp = connection.setSavepoint();

        ps.setString(1, "Max");
        ps.setInt(2, 20);
        ps.executeUpdate();

        connection.rollback(sp);

        ps.setString(1, "Alex");
        ps.setInt(2, 30);
        ps.executeUpdate();

        //connection.commit();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Students");

        while (rs.next()) {
            System.out.println(rs.getInt("id")
                    + " " + rs.getString("NaMe")
                    + " " + rs.getInt("SCoRE"));
        }

        disconnect();
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:MainDb.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
