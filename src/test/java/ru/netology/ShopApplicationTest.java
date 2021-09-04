package ru.netology;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import java.time.Duration;

import ru.netology.data.DataHelper;

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

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUpEach() {
        //DataHelper.deleteData();
        open(appUrl);
    }

    @AfterEach
    void tearDownEach() {
        closeWebDriver();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    // ----------------------------  DEBIT  ---------------------------------------


    @Test
    void shouldBeSuccessfulDebitBuyByApprovedCard() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        accepted();
    }

    @Test
    void shouldBeFailureDebitBuyByDeclinedCard() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getDeclinedCard());
        rejected();
    }

    @Test
    void shouldBeFailureDebitBuyByInvalidCard() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getInvalidCard());
        rejected();
    }

    @Test
    void shouldBeMessageIfMonthInvalid() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        month.setValue(DataHelper.getInvalidMonth());
        year.setValue(DataHelper.generateYear());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Неверно указан срок действия карты"));
    }

    @Test
    void shouldBeMessageIfYearInvalidInFuture() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.getInvalidYearInFuture());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Неверно указан срок действия карты"));
    }

    @Test
    void shouldBeMessageIfYearInvalidInPast() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.getInvalidYearInPast());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Истёк срок действия карты"));
    }

    @Test
    void shouldBeMessageIfHolderIsEmpty() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.generateYear());
        cvc.setValue(DataHelper.generateCvc());
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldBeMessageIfCvcIsInvalid() {
        btnDebitBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.generateYear());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.getInvalidCvc());
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Неверный формат"));
    }


    // ----------------------------  CREDIT  ---------------------------------------


    @Test
    void shouldBeSuccessfulCreditBuyByApprovedCard() {
        btnCreditBuy.click();
        card.setValue(DataHelper.getApprovedCard());
        accepted();
    }

    @Test
    void shouldBeFailureCreditBuyByDeclinedCard() {
        btnCreditBuy.click();
        card.setValue(DataHelper.getDeclinedCard());
        rejected();
    }

    @Test
    void shouldBeFailureCreditBuyByInvalidCard() {
        btnCreditBuy.click();
        card.setValue(DataHelper.getInvalidCard());
        rejected();
    }


    // ----------------------------  support  ---------------------------------------


    void fillOtherCardFields() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.generateYear());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
    }

    void accepted() {
        fillOtherCardFields();
        btnContinue.click();
        noticeAccepted.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена Банком"));
    }

    void rejected() {
        fillOtherCardFields();
        btnContinue.click();
        noticeRejected.shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Банк отказал в проведении операции"));
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
