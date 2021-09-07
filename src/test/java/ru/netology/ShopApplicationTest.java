package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        //DataHelper.deleteData();
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
        new DebitBuy().debitBuyByDeclinedCard();
    }

    @Test
    void shouldBeFailureDebitBuyByInvalidCard() {
        new DebitBuy().debitBuyByInvalidCard();
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

    @Test
    //@RepeatedTest(15)
    void test() {
        //DataHelper.deleteData();
        //String orderId = DataHelper.getOrderId();
        //System.out.println(orderId);
        //OrderEntity oe = DataHelper.getOrderById(orderId);
        //System.out.println(oe);
        //PaymentEntity pe = DataHelper.getPaymentById("0b4ef46f-0d2d-4adc-827b-17c01efcc04a");
        //System.out.println(pe);
        //CreditRequestEntity cre = DataHelper.getCreditRequestById("5861f697-3fa6-46e0-95f9-b3d404445a56");
        //System.out.println(cre);
        //System.out.println(DataHelper.generateYear());
        //System.out.println(DataHelper.generateMonth());
        //System.out.println(DataHelper.generateName());
        //System.out.println(DataHelper.generateCIV());
        //System.out.println(DataHelper.getInvalidYear());
    }

}
