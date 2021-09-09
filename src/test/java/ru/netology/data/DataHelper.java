package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import ru.netology.data.entity.CreditRequestEntity;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Locale;

public class DataHelper {

    static Faker faker = new Faker(new Locale("en"));

    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;

    static {
        dbUrl  = System.getenv("DB_URL");
        dbUser = System.getenv("DB_USER");
        dbPass = System.getenv("DB_PASS");

        dbUrl  = (dbUrl  == null) ? "jdbc:mysql://localhost:3306/app" : dbUrl;
        dbUser = (dbUser == null) ? "app" : dbUser;
        dbPass = (dbPass == null) ? "pass" : dbPass;
    }

    private DataHelper() {}

    public static String getApprovedCard() {
        return "4444 4444 4444 4441";
    }
    public static String getDeclinedCard() {
        return "4444 4444 4444 4442";
    }
    public static String getInvalidCard()  { return "4444 4444 4444 4443"; }

    public static String generateName() {
        return faker.name().lastName().toUpperCase();
    }
    public static String generateMonth() {
        int mi = faker.random().nextInt(1,12);
        return String.format("%02d", mi);
    }
    public static String generateCvc() { return faker.random().nextInt(100,999).toString(); }
    public static String generateYear() {
        return faker.random().nextInt(Year.now().plusYears(1).getValue(), Year.now().plusYears(5).getValue()).toString().substring(2);
    }
    public static String getInvalidMonth() { return "13"; }
    public static String getInvalidYearInFuture() {
        return Year.now().plusYears(10).toString().substring(2);
    }
    public static String getInvalidYearInPast() {
        return Year.now().minusYears(11).toString().substring(2);
    }
    public static String getInvalidCvc() { return "1"; }
    public static int getTripPrice() { return 4500000; }

    @SneakyThrows
    public static String getOrderId() {
        String id = null;
        String sql = "SELECT id FROM order_entity";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        var rs = dataStmt.executeQuery();
        rs.next();
        id = rs.getString(1);
        return id;
    }

    @SneakyThrows
    public static OrderEntity getOrder() {
        OrderEntity obj = null;
        String sql = "SELECT id, created, credit_id, payment_id FROM order_entity";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        var rs = dataStmt.executeQuery();
        if (rs != null && rs.next()) {
            obj = new OrderEntity();
            obj.setId(rs.getString(1));
            obj.setCreated(rs.getObject(2, LocalDateTime.class));
            obj.setCreditId(rs.getString(3));
            obj.setPaymentId(rs.getString(4));
        }
        return obj;
    }

    @SneakyThrows
    public static OrderEntity getOrderById(String id) {
        OrderEntity obj = null;
        String sql = "SELECT created, credit_id, payment_id FROM order_entity WHERE id = ?";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        dataStmt.setString(1, id);
        var rs = dataStmt.executeQuery();
        if (rs != null && rs.next()) {
            obj = new OrderEntity();
            obj.setId(id);
            obj.setCreated(rs.getObject(1, LocalDateTime.class));
            obj.setCreditId(rs.getString(2));
            obj.setPaymentId(rs.getString(3));
        }
        return obj;
    }

    @SneakyThrows
    public static PaymentEntity getPayment() {
        PaymentEntity obj = null;
        String sql = "SELECT id, amount, created, status, transaction_id FROM payment_entity";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        var rs = dataStmt.executeQuery();
        if (rs != null && rs.next()) {
            obj = new PaymentEntity();
            obj.setId(rs.getString(1));
            obj.setAmount(rs.getInt(2));
            obj.setCreated(rs.getObject(3, LocalDateTime.class));
            obj.setStatus(Status.valueOf(rs.getString(4)));
            obj.setTransactionId(rs.getString(5));
        }
        return obj;
    }

    @SneakyThrows
    public static PaymentEntity getPaymentById(String id) {
        PaymentEntity obj = null;
        String sql = "SELECT amount, created, status, transaction_id  FROM payment_entity WHERE id = ?";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        dataStmt.setString(1, id);
        var rs = dataStmt.executeQuery();
        if (rs != null && rs.next()) {
            obj = new PaymentEntity();
            obj.setId(id);
            obj.setAmount(rs.getInt(1));
            obj.setCreated(rs.getObject(2, LocalDateTime.class));
            obj.setStatus(Status.valueOf(rs.getString(3)));
            obj.setTransactionId(rs.getString(4));
        }
        return obj;
    }

    @SneakyThrows
    public static CreditRequestEntity getCreditRequest() {
        CreditRequestEntity obj = null;
        String sql = "SELECT id, bank_id, created, status FROM credit_request_entity";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        var rs = dataStmt.executeQuery();
        if (rs != null && rs.next()) {
            obj = new CreditRequestEntity();
            obj.setId(rs.getString(1));
            obj.setBankId(rs.getString(2));
            obj.setCreated(rs.getObject(3, LocalDateTime.class));
            obj.setStatus(Status.valueOf(rs.getString(4)));
        }
        return obj;
    }

    @SneakyThrows
    public static CreditRequestEntity getCreditRequestById(String id) {
        CreditRequestEntity obj = null;
        String sql = "SELECT bank_id, created, status FROM credit_request_entity WHERE id = ?";
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.prepareStatement(sql);
        dataStmt.setString(1, id);
        var rs = dataStmt.executeQuery();
        if (rs != null && rs.next()) {
            obj = new CreditRequestEntity();
            obj.setId(id);
            obj.setBankId(rs.getString(1));
            obj.setCreated(rs.getObject(2, LocalDateTime.class));
            obj.setStatus(Status.valueOf(rs.getString(3)));
        }
        return obj;
    }

    @SneakyThrows
    public static void deleteData() {
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.createStatement();
        dataStmt.execute("TRUNCATE payment_entity");
        dataStmt.execute("TRUNCATE credit_request_entity");
        dataStmt.execute("TRUNCATE order_entity");
    }

    @SneakyThrows
    public static void dropOrder() {
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.createStatement();
        dataStmt.execute("DROP TABLE IF EXISTS order_entity");
    }

    @SneakyThrows
    public static void truncatePayment() {
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.createStatement();
        dataStmt.execute("TRUNCATE payment_entity");
    }

    @SneakyThrows
    public static void truncateCreditRequest() {
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.createStatement();
        dataStmt.execute("TRUNCATE credit_request_entity");
    }


}



