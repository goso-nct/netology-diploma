package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.Status;
import ru.netology.data.entity.CreditRequestEntity;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ShopApplicationTest {

    static String appUrl;
    static {
        appUrl = System.getenv("APP_URL");
        appUrl = (appUrl == null) ? "http://localhost:8080" : appUrl;
    }

    AppPage appPage;

    @BeforeAll
    void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUpEach() {
        appPage = new AppPage();
        open(appUrl);
    }

    @AfterEach
    void tearDownEach() {
        closeWebDriver();
    }

    @AfterAll
    void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    // ----------------------------  DEBIT  ---------------------------------------

    @Test
    void shouldBeSuccessfulDebitBuyByApprovedCard() {
        new DebitBuy().buyByApprovedCard();
    }

    @Test
    void shouldBeFailureDebitBuyByDeclinedCard() {
        new DebitBuy().buyByDeclinedCard();
    }

    @Test
    void shouldBeFailureDebitBuyByInvalidCard() {
        new DebitBuy().buyByInvalidCard();
    }

    @Test
    void shouldBeMessageIfMonthInvalid() {
        new DebitBuy().messageIfMonthInvalid();
    }

    @Test
    void shouldBeMessageIfYearInvalidInFuture() {
        new DebitBuy().messageIfYearInvalidInFuture();
    }

    @Test
    void shouldBeMessageIfYearInvalidInPast() {
        new DebitBuy().messageIfYearInvalidInPast();
    }

    @Test
    void shouldBeMessageIfHolderIsEmpty() {
        new DebitBuy().messageIfHolderIsEmpty();
    }

    @Test
    void shouldBeMessageIfCvcIsInvalid() {
        new DebitBuy().messageIfCvcIsInvalid();
    }


    // ----------------------------  CREDIT  ---------------------------------------

    @Test
    void shouldBeSuccessfulCreditBuyByApprovedCard() {
        new CreditBuy().buyByApprovedCard();
    }

    @Test
    void shouldBeFailureCreditBuyByDeclinedCard() {
        new CreditBuy().buyByDeclinedCard();
    }

    @Test
    void shouldBeFailureCreditBuyByInvalidCard() {
        new CreditBuy().buyByInvalidCard();
    }


    // ----------------------------  Check DB  ---------------------------------------

    @Test
    void z_checkStatusOnDebitBuyByApprovedCard() {
        DataHelper.deleteData();
        new DebitBuy().buyByApprovedCardNoExpect();
        PaymentEntity payment = DataHelper.getPayment();
        assertTrue(payment.isValid());
        assertEquals(payment.getStatus(), Status.APPROVED);
    }

    @Test
    void z_checkStatusOnDebitBuyByDeclinedCard() {
        DataHelper.deleteData();
        new DebitBuy().buyByDeclinedCardNoExpect();
        PaymentEntity payment = DataHelper.getPayment();
        assertTrue(payment.isValid());
        assertEquals(payment.getStatus(), Status.DECLINED);
    }

    @Test
    void z_checkAmountOnDebitBuyByApprovedCard() {
        DataHelper.deleteData();
        new DebitBuy().buyByApprovedCard();
        PaymentEntity payment = DataHelper.getPayment();
        assertEquals(payment.getAmount(), DataHelper.getTripPrice());
    }

    @Test
    void z_checkForeignKeyOnDebitBuy() {
        DataHelper.deleteData();
        new DebitBuy().buyByApprovedCard();
        PaymentEntity payment = DataHelper.getPayment();
        OrderEntity order = DataHelper.getOrder();
        assertTrue(order.isValid());
        assertEquals(payment.getId(), order.getPaymentId());
    }

    @Test
    // TODO: 09.09.21 разобрать на составляющие
    void z_checkDbOnSuccessfulCreditBuyByApprovedCard() {
        DataHelper.deleteData();
        new CreditBuy().buyByApprovedCard();
        CreditRequestEntity credit = DataHelper.getCreditRequest();
        OrderEntity order = DataHelper.getOrder();
        assertTrue(credit.isValid());
        assertTrue(order.isValid());
        assertEquals(credit.getStatus(), Status.APPROVED);
        assertEquals(credit.getId(), order.getCreditId());
    }

    @Test
    void zz1_checkUseTransactionOnCreditBuy() {
        DataHelper.truncateCreditRequest();
        DataHelper.dropOrder();
        new CreditBuy().buyByApprovedCard();
        CreditRequestEntity credit = DataHelper.getCreditRequest();
        assertNull(credit);
    }

    @Test
    void zz2_checkUseTransactionOnDebitBuy() {
        DataHelper.truncatePayment();
        DataHelper.dropOrder();
        new DebitBuy().buyByApprovedCard();
        PaymentEntity payment = DataHelper.getPayment();
        assertNull(payment);
    }

}
