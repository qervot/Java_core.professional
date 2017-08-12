import org.junit.*;

import java.sql.*;

public class DbTest {

    private static Connection connection;
    private static Statement stmt;
    private static Savepoint sp;

    @BeforeClass
    public static void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:MainDB.db");
            stmt = connection.createStatement();
            sp = connection.setSavepoint("A");
            //connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        try {

            connection.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS CNT FROM Students");
            if (!rs.next()) {
                Assert.fail("База данных пуста");
            }

            int startCnt = rs.getInt("CNT");

            String sql = "INSERT INTO Students (name, score) VALUES ('bob2', 11)";
            stmt.executeUpdate(sql);

            rs = stmt.executeQuery("SELECT COUNT(*) AS CNT FROM Students");
            if (!rs.next()) {
                if (startCnt + 1 != rs.getInt("CNT")) {
                    Assert.fail("Ошибка добавления новой записи!");
                }
            }

            int cntUpdated = stmt.executeUpdate("UPDATE Students SET score = 12 WHERE name = 'bob2'");
            if (cntUpdated == 0) {
                Assert.fail("Не найденны обновляемые данные!");
            }

            rs = stmt.executeQuery("SELECT score FROM Students WHERE name = 'bob2'");
            rs.next();
            if (rs.getInt("score") != 12) {
                Assert.fail("Ошибка получения баллов студента!");
            }

            int cntDeleted = stmt.executeUpdate("DELETE FROM Students WHERE name = 'bob2'");
            if (cntDeleted == 0) {
                Assert.fail("Не найдена запись для удаления!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Нарушена структура базы данных!");
        }
    }

    @AfterClass
    public static void close() {
        try {
            connection.rollback(sp);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
