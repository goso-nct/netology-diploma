package ru.netology;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

public class AppPage {

    private final SelenideElement headerBuyType = $$("h3.heading").get(1);
    private final SelenideElement btnDebitBuy = $(byText("Купить"));
    private final SelenideElement btnCreditBuy = $(byText("Купить в кредит"));
    private final SelenideElement btnContinue = $(byText("Продолжить"));
    private final SelenideElement txtCard = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement subCard = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement txtMonth = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement subMonth = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement txtYear = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement subYear = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement txtHolder = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement subHolder = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement txtCvc = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement subCvc = $(byText("CVC/CVV")).parent().$(".input__sub");
    private final SelenideElement noticeAccepted = $(".notification_status_ok");
    private final SelenideElement noticeRejected = $(".notification_status_error");

    final int BANK_RESPONSE_WAIT = 15;

    String STR_REQUIRED_FIELD = "Поле обязательно для заполнения";
    String STR_INCORRECT_PERIOD = "Неверно указан срок действия карты";
    String STR_INVALID_FORMAT = "Неверный формат";
    String STR_CARD_EXPIRED = "Истёк срок действия карты";
    String STR_DEBIT = "Оплата по карте";
    String STR_CREDIT = "Кредит по данным карты";
    String STR_APPROVED = "Операция одобрена Банком";
    String STR_REFUSED = "Банк отказал в проведении операции";

    void clearField(SelenideElement element) {
        element.sendKeys(CONTROL + "A", DELETE);
    }

    void setValidFieldsExceptCardNumber() {
        txtMonth.setValue(DataHelper.generateMonth());
        txtYear.setValue(DataHelper.generateYear());
        txtHolder.setValue(DataHelper.generateHolder());
        txtCvc.setValue(DataHelper.generateCvc());
    }

    void setValidFieldsExceptCardNumberAndClear(SelenideElement element) {
        txtMonth.setValue(DataHelper.generateMonth());
        txtYear.setValue(DataHelper.generateYear());
        txtHolder.setValue(DataHelper.generateHolder());
        txtCvc.setValue(DataHelper.generateCvc());
        clearField(element);
    }

    void setInvalidCard() {
        setValidFieldsExceptCardNumberAndClear(txtCard);
        txtCard.setValue(DataHelper.getInvalidCard());
    }

    void setInvalidMonth() {
        setValidFieldsExceptCardNumberAndClear(txtMonth);
        txtMonth.setValue(DataHelper.getInvalidMonth());
    }

    void setInvalidYearInFuture() {
        setValidFieldsExceptCardNumberAndClear(txtYear);
        txtYear.setValue(DataHelper.getInvalidYearInFuture());
    }

    void setInvalidYearInPast() {
        setValidFieldsExceptCardNumberAndClear(txtYear);
        txtYear.setValue(DataHelper.getInvalidYearInPast());
    }

    void setNoHolder() {
        setValidFieldsExceptCardNumberAndClear(txtHolder);
    }

    void setInvalidHolder() {
        setValidFieldsExceptCardNumberAndClear(txtHolder);
        txtHolder.setValue(DataHelper.getInvalidHolder());
    }

    void setLongHolder() {
        setValidFieldsExceptCardNumberAndClear(txtHolder);
        txtHolder.setValue(DataHelper.getLongHolder());
    }

    void setShortHolder() {
        setValidFieldsExceptCardNumberAndClear(txtHolder);
        txtHolder.setValue(DataHelper.getShortHolder());
    }

    void setInvalidCvc() {
        setValidFieldsExceptCardNumberAndClear(txtCvc);
        txtCvc.setValue(DataHelper.getInvalidCvc());
    }

    void debitBuy() {
        btnDebitBuy.click();
        headerBuyType.shouldHave(text(STR_DEBIT));
    }

    void creditBuy() {
        btnCreditBuy.click();
        headerBuyType.shouldHave(text(STR_CREDIT));
    }

    void useApprovedCard() {
        txtCard.setValue(DataHelper.getApprovedCard());
    }

    void useDeclinedCard() {
        txtCard.setValue(DataHelper.getDeclinedCard());
    }

    void useInvalidCard() {
        txtCard.setValue(DataHelper.getNonExistentCard());
    }

    void expectSubInField(String text, SelenideElement element) {
        btnContinue.click();
        element.shouldBe(appear).shouldHave(text(text));
    }

    void expectSubInvalidFormatInCard() {
        expectSubInField(STR_INVALID_FORMAT, subCard);
    }

    void expectSubInvalidPeriodInMonth() {
        expectSubInField(STR_INCORRECT_PERIOD, subMonth);
    }

    void expectSubInvalidPeriodInYear() {
        expectSubInField(STR_INCORRECT_PERIOD, subYear);
    }

    void expectSubCardExpiredInYear() {
        expectSubInField(STR_CARD_EXPIRED, subYear);
    }

    void expectSubRequiredInHolder() {
        expectSubInField(STR_REQUIRED_FIELD, subHolder);
    }

    void expectSubInvalidFormatInHolder() {
        expectSubInField(STR_INVALID_FORMAT, subHolder);
    }

    void expectSubInvalidFormatInCvc() {
        expectSubInField(STR_INVALID_FORMAT, subCvc);
    }

    void expectAccept() {
        setValidFieldsExceptCardNumber();
        btnContinue.click();
        noticeAccepted.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text(STR_APPROVED));
    }

    void expectReject() {
        setValidFieldsExceptCardNumber();
        btnContinue.click();
        noticeRejected.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text(STR_REFUSED));
    }

    void buy() {
        btnContinue.click();
    }

    void buyAndWait() {
        btnContinue.click();
        Selenide.sleep(BANK_RESPONSE_WAIT * 1_000L);
    }

    void checkSubBlankFields() {
        subCard.shouldHave(text(STR_REQUIRED_FIELD));
        subMonth.shouldHave(text(STR_REQUIRED_FIELD));
        subYear.shouldHave(text(STR_REQUIRED_FIELD));
        subHolder.shouldHave(text(STR_REQUIRED_FIELD));
        subCvc.shouldHave(text(STR_REQUIRED_FIELD));
    }
}
