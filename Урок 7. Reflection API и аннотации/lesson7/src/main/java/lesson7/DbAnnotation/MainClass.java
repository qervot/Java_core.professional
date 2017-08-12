package lesson7.DbAnnotation;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;

public class MainClass {
    public static void main(String[] args) throws Exception {
        Class c = Student.class;

        HashMap<Class, String> hm = new HashMap<>();
        hm.put(int.class, "Integer");
        hm.put(String.class, "TEXT");

        Connection connection = DriverManager.getConnection("jdbc:sqlite:1.db");
                String x = "CREATE TABLE " + ((XTable) c.getAnnotation(XTable.class)).tableName() + "(";

        Field[] fields = c.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(XField.class)) {
                x += field.getName() + " " + hm.get(field.getType()) + ", ";
            }
        }

        x = x.substring(0, x.length() - 2);

        x += ");";

        System.out.println(x);

        Statement stmt = connection.createStatement();

        stmt.execute(x);


        connection.close();
    }
}
