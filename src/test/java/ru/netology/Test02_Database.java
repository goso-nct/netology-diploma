package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.BuyType;
import ru.netology.data.Status;
import ru.netology.data.entity.CommonEntity;
import ru.netology.data.entity.CreditRequestEntity;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.DataHelper.appUrl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class Test02_Database {

    @BeforeAll
    void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUpEach() {
        open(appUrl);
    }

    @AfterAll
    void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @EnumSource(BuyType.class)
    @ParameterizedTest(name = "checkStatusBuyOnApproved {0} Card")
    void checkStatusBuyOnApprovedCard(BuyType buyType) {
        DataHelper.deleteData();
        new FrontendHelper(buyType).buyOnApprovedCardNoExpect();
        CommonEntity entity = DataHelper.getEntityWithStatus(buyType);
        assertTrue(entity.isValid());
        assertEquals(entity.getStatus(), Status.APPROVED);
    }

    @EnumSource(BuyType.class)
    @ParameterizedTest(name = "checkStatusBuyOnDeclined {0} Card")
    void checkStatusBuyOnDeclinedCard(BuyType buyType) {
        DataHelper.deleteData();
        new FrontendHelper(buyType).buyOnDeclinedCardNoExpect();
        CommonEntity entity = DataHelper.getEntityWithStatus(buyType);
        assertTrue(entity.isValid());
        assertEquals(entity.getStatus(), Status.DECLINED);
    }

    @Test
    void checkAmountDebitBuyOnApprovedCard() {
        DataHelper.deleteData();
        new FrontendHelper(BuyType.DEBIT).buyOnApprovedCard();
        PaymentEntity payment = DataHelper.getPayment();
        assertEquals(payment.getAmount(), DataHelper.getTripPrice());
    }

    @Test
    void checkForeignKeyDebitBuy() {
        DataHelper.deleteData();
        new FrontendHelper(BuyType.DEBIT).buyOnApprovedCard();
        PaymentEntity payment = DataHelper.getPayment();
        assertTrue(payment.isValid());
        OrderEntity order = DataHelper.getOrder();
        assertTrue(order.isValid());
        assertEquals(payment.getId(), order.getPaymentId());
    }

    @Test
    void shouldBeNullCreditIdOnDebitBuy() {
        DataHelper.deleteData();
        new FrontendHelper(BuyType.DEBIT).buyOnApprovedCard();
        OrderEntity order = DataHelper.getOrder();
        assertNull(order.getCreditId());
    }

    @Test
    void checkForeignKeyCreditBuy() {
        DataHelper.deleteData();
        new FrontendHelper(BuyType.CREDIT).buyOnApprovedCard();
        CreditRequestEntity credit = DataHelper.getCreditRequest();
        assertTrue(credit.isValid());
        OrderEntity order = DataHelper.getOrder();
        assertTrue(order.isValid());
        assertEquals(credit.getId(), order.getCreditId());
    }

    @Test
    void shouldBeNullPaymentIdOnCreditBuy() {
        DataHelper.deleteData();
        new FrontendHelper(BuyType.CREDIT).buyOnApprovedCard();
        OrderEntity order = DataHelper.getOrder();
        assertNull(order.getPaymentId());
    }

    @Test
    void z1_checkUseTransactionOnCreditBuy() {
        DataHelper.truncateCreditRequest();
        DataHelper.dropOrder();
        new FrontendHelper(BuyType.CREDIT).buyOnApprovedCard();
        CreditRequestEntity credit = DataHelper.getCreditRequest();
        assertNull(credit);
    }

    @Test
    void z2_checkUseTransactionOnDebitBuy() {
        DataHelper.truncatePayment();
        DataHelper.dropOrder();
        new FrontendHelper(BuyType.DEBIT).buyOnApprovedCard();
        PaymentEntity payment = DataHelper.getPayment();
        assertNull(payment);
    }

}