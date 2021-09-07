package ru.netology;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShopApplicationTest {

    static String appUrl;
    static {
        appUrl = System.getenv("APP_URL");
        appUrl = (appUrl == null) ? "http://localhost:8080" : appUrl;
    }

    SelenideElement btnDebitBuy = $$("[class=button__content]").get(0);
    SelenideElement btnCreditBuy = $$("[class=button__content]").get(1);
    SelenideElement btnContinue = $$("[class=button__content]").get(2);
    SelenideElement card = $$("[class=input__control]").get(0);
    SelenideElement month = $$("[class=input__control]").get(1);
    SelenideElement year = $$("[class=input__control]").get(2);
    SelenideElement holder = $$("[class=input__control]").get(3);
    SelenideElement cvc = $$("[class=input__control]").get(4);
    SelenideElement noticeAccepted = $(".notification_status_ok");
    SelenideElement noticeRejected = $(".notification_status_error");
    SelenideElement subErr = $(".input__sub");
    ElementsCollection subErrList = $$(".input__sub");

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
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.expectAccept();
    }

    @Test
    void shouldBeFailureDebitBuyByDeclinedCard() {
        appPage.debitBuy();
        appPage.useDeclinedCard();
        appPage.expectReject();
    }

    @Test
    void shouldBeFailureDebitBuyByInvalidCard() {
        appPage.debitBuy();
        appPage.useInvalidCard();
        appPage.expectReject();
    }

    @Test
    void shouldBeMessageIfMonthInvalid() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidMonth();
        appPage.expectNoticeCardInvalidPeriod();
    }

    @Test
    void shouldBeMessageIfYearInvalidInFuture() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidYearInFuture();
        appPage.expectNoticeCardInvalidPeriod();
    }

    @Test
    void shouldBeMessageIfYearInvalidInPast() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidYearInPast();
        appPage.expectNoticeCardExpired();
    }

    @Test
    void shouldBeMessageIfHolderIsEmpty() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.noSetHolder();
        appPage.expectNoticeFieldRequired();
    }

    @Test
    void shouldBeMessageIfCvcIsInvalid() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidCvc();
        appPage.expectNoticeInvalidFormat();
    }


    // ----------------------------  CREDIT  ---------------------------------------


    @Test
    void shouldBeSuccessfulCreditBuyByApprovedCard() {
        appPage.creditBuy();
        appPage.useApprovedCard();
        appPage.expectAccept();
    }

    @Test
    void shouldBeFailureCreditBuyByDeclinedCard() {
        appPage.creditBuy();
        appPage.useDeclinedCard();
        appPage.expectReject();
    }

    @Test
    void shouldBeFailureCreditBuyByInvalidCard() {
        appPage.creditBuy();
        appPage.useDeclinedCard();
        appPage.expectReject();
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
