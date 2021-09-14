package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import ru.netology.data.entity.CommonEntity;
import ru.netology.data.entity.CreditRequestEntity;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Locale;
import java.util.Properties;

public class DataHelper {

    public enum BuyType {CREDIT, DEBIT}
    public static final Faker faker = new Faker(new Locale("en"));

    static String dbUrl;
    static String dbUser;
    static String dbPass;

    // параметры берём из свойств тестируемого приложения
    static {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("artifacts/application.properties"));
            dbUrl = props.getProperty("spring.datasource.url");
            dbUser = props.getProperty("spring.datasource.username");
            dbPass = props.getProperty("spring.datasource.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String appUrl = "http://localhost:8080";

    // только для моей конфигурации стенда:
    static String TEST_IP = System.getenv("TEST_IP");
    static {
        if (TEST_IP != null) {
            dbUrl = dbUrl.replace("localhost", TEST_IP);
            appUrl = appUrl.replace("localhost", TEST_IP);
        }
    }

    private DataHelper() {}

    public static String getApprovedCard() {
        return "4444 4444 4444 4441";
    }
    public static String getDeclinedCard() {
        return "4444 4444 4444 4442";
    }
    public static String getNonExistentCard()  {
        return "4444 4444 4444 4443";
    }
    public static String getInvalidCard()  {
        return "0000";
    }
    public static String getInvalidCvc() {
        return "1";
    }
    public static int getTripPrice() {
        return 45_000_00;
    }
    public static String getInvalidHolder() {
        return "!@#$%^&*()_+ 1234567890";
    }
    // ISO IEC 7813: card holder length 2..26
    public static String getLongHolder() {
        return "GEORGE HERBERT WALKER BUSH ELDER";
    }
    public static String getShortHolder() {
        return "Z";
    }
    public static String generateHolder() {
        return (DataHelper.faker.name().firstName() + " " + DataHelper.faker.name().lastName()).toUpperCase();
    }
    public static String generateMonth() {
        return String.format("%02d", faker.random().nextInt(1,12));
    }
    public static String generateCvc() {
        return faker.random().nextInt(100,999).toString();
    }
    public static String generateYear() {
        return faker.random().nextInt(Year.now().plusYears(1).getValue(), Year.now().plusYears(5).getValue()).toString().substring(2);
    }
    public static String getInvalidMonth() {
        return "13";
    }
    public static String getInvalidYearInFuture() {
        return Year.now().plusYears(10).toString().substring(2);
    }
    public static String getInvalidYearInPast() {
        return Year.now().minusYears(1).toString().substring(2);
    }

    public static CommonEntity getEntityWithStatus(BuyType buyType) {
        CommonEntity entity = null;
        if (buyType == BuyType.CREDIT)
            entity = getCreditRequest();
        else if (buyType == BuyType.DEBIT)
            entity = getPayment();
        return entity;
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
    public static void deleteData() {
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.createStatement();
        dataStmt.execute("TRUNCATE payment_entity");
        dataStmt.execute("TRUNCATE credit_request_entity");
        dataStmt.execute("TRUNCATE order_entity");
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

    @SneakyThrows
    public static void dropOrder() {
        var conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        var dataStmt = conn.createStatement();
        dataStmt.execute("DROP TABLE IF EXISTS order_entity");
    }

    public static String getParams() {
        return "dbUrl=" + dbUrl + ", dbUser=" + dbUser + ", dbPass=" + dbPass + ", appUrl=" + appUrl;
    }

}




