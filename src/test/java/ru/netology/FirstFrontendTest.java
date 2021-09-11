package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.data.DataHelper.BuyType;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.appUrl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirstFrontendTest {

    @BeforeAll
    void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUpEach() {
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

    @EnumSource(BuyType.class)
    @ParameterizedTest(name = "shouldBeSuccessfulBuyOnApproved{0}Card")
    void shouldBeSuccessfulBuyOnApprovedCard(BuyType buyType) {
        new FrontendHelper(buyType).buyOnApprovedCard();
    }

    @EnumSource(BuyType.class)
    @ParameterizedTest(name = "shouldBeFailureBuyOnDeclined{0}Card")
    void shouldBeFailureBuyOnDeclinedCard(BuyType buyType) {
        new FrontendHelper(buyType).buyOnDeclinedCard();
    }

    @EnumSource(BuyType.class)
    @ParameterizedTest(name ="shouldBeFailureBuyOnInvalid{0}Card")
    void shouldBeFailureBuyOnInvalidCard(BuyType buyType) {
        new FrontendHelper(buyType).buyOnInvalidCard();
    }

    @Test
    void shouldBeNoticeIfMonthInvalid() {
        new FrontendHelper(BuyType.DEBIT).noticeIfMonthInvalid();
    }

    @Test
    void shouldBeNoticeIfYearInvalidInFuture() {
        new FrontendHelper(BuyType.DEBIT).noticeIfYearInvalidInFuture();
    }

    @Test
    void shouldBeNoticeIfYearInvalidInPast() {
        new FrontendHelper(BuyType.DEBIT).noticeIfYearInvalidInPast();
    }

    @Test
    void shouldBeNoticeIfHolderIsEmpty() {
        new FrontendHelper(BuyType.DEBIT).noticeIfHolderIsEmpty();
    }

    @Test
    void shouldBeNoticeIfCvcIsInvalid() {
        new FrontendHelper(BuyType.DEBIT).noticeIfCvcIsInvalid();
    }

}
