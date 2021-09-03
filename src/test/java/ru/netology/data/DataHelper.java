package ru.netology.data;

import com.github.javafaker.Faker;
import ru.netology.data.entity.CreditRequestEntity;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;

public class DataHelper
{
    static Faker faker = new Faker(new Locale("en"));

    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;

    static {
        dbUrl  = System.getenv("DB_URL");
        dbUser = System.getenv("DB_USER");
        dbPass = System.getenv("DB_PASS");

        dbUrl  = dbUrl  == null ? "jdbc:mysql://localhost:3306/app" : dbUrl;
        dbUser = dbUser == null ? "app" : dbUser;
        dbPass = dbPass == null ? "pass" : dbPass;
    }

    private DataHelper() {}

    public static String getApprovedCard() {
        return "4444 4444 4444 4441";
    }
    public static String getDeclinedCard() {
        return "4444 4444 4444 4442";
    }
    public static String getInvalidCard() {
        return "4444 4444 4444 4443";
    }

    public static String generateName() {
        return faker.name().lastName().toUpperCase();
    }
    public static String generateMonth() {
        int mi = faker.random().nextInt(1,12);
        return String.format("%02d", mi);
    }
    public static String generateCIV() { return faker.random().nextInt(100,999).toString(); }
    public static String generateYear() { return faker.random().nextInt(22,25).toString(); }
    public static String getLastYear() { return "20"; }
    public static String getInvalidMonth() { return "20"; }


    public static String getOrderId() {
        String id = null;
        String sql = "SELECT id FROM order_entity";
        try(
                var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                var dataStmt = conn.prepareStatement(sql);
        ){
            var rs = dataStmt.executeQuery();
            rs.next();
            id = rs.getString(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static OrderEntity getOrderById(String id) {
        OrderEntity obj = null;
        String sql =
                "SELECT credit_id, payment_id, created" +
                "  FROM order_entity" +
                "  WHERE id = ?";
        try(
                var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                var dataStmt = conn.prepareStatement(sql);
        ){
            dataStmt.setString(1, id);
            var rs = dataStmt.executeQuery();
            if (rs != null && rs.next()) {
                obj = new OrderEntity();
                obj.setId(id);
                obj.setPaymentId(rs.getString("payment_id"));
                obj.setCreditId(rs.getString("credit_id"));
                obj.setCreated(rs.getObject("created", LocalDateTime.class));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static PaymentEntity getPaymentById(String id) {
        PaymentEntity obj = null;
        String sql =
                "SELECT amount, created, status, transaction_id  " +
                        "  FROM payment_entity" +
                        "  WHERE id = ?";
        try(
                var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                var dataStmt = conn.prepareStatement(sql);
        ){
            dataStmt.setString(1, id);
            var rs = dataStmt.executeQuery();
            if (rs != null && rs.next()) {
                obj = new PaymentEntity();
                obj.setId(id);
                obj.setAmount(rs.getInt("amount"));
                obj.setCreated(rs.getObject("created", LocalDateTime.class));
                obj.setStatus(Status.valueOf(rs.getString("status")));
                obj.setTransactionId(rs.getString("transaction_id"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static CreditRequestEntity getCreditRequestById(String id) {
        CreditRequestEntity obj = null;
        String sql =
                "SELECT bank_id, created, status" +
                        "  FROM credit_request_entity" +
                        "  WHERE id = ?";
        try(
                var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                var dataStmt = conn.prepareStatement(sql);
        ){
            dataStmt.setString(1, id);
            var rs = dataStmt.executeQuery();
            if (rs != null && rs.next()) {
                obj = new CreditRequestEntity();
                obj.setId(id);
                obj.setBankId(rs.getString("bank_id"));
                obj.setCreated(rs.getObject("created", LocalDateTime.class));
                obj.setStatus(Status.valueOf(rs.getString("status")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void deleteData() {
        try(
            var conn = DriverManager.getConnection(dbUrl, "app", "pass");
            var dataStmt = conn.createStatement();
        ){
            dataStmt.execute("TRUNCATE payment_entity");
            dataStmt.execute("TRUNCATE credit_request_entity");
            dataStmt.execute("TRUNCATE order_entity");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



