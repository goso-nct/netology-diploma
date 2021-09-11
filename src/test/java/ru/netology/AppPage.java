package ru.netology;

import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AppPage {

    private final SelenideElement headerBuyType = $$("h3.heading").get(1);
    private final SelenideElement btnDebitBuy = $$("[class=button__content]").get(0);
    private final SelenideElement btnCreditBuy = $$("[class=button__content]").get(1);
    private final SelenideElement btnContinue = $$("[class=button__content]").get(2);
    private final SelenideElement card = $$("[class=input__control]").get(0);
    private final SelenideElement month = $$("[class=input__control]").get(1);
    private final SelenideElement year = $$("[class=input__control]").get(2);
    private final SelenideElement holder = $$("[class=input__control]").get(3);
    private final SelenideElement cvc = $$("[class=input__control]").get(4);
    private final SelenideElement noticeAccepted = $(".notification_status_ok");
    private final SelenideElement noticeRejected = $(".notification_status_error");
    private final SelenideElement subErr = $(".input__sub");

    int BANK_RESPONSE_WAIT = 15;

    void isDebit() {
        headerBuyType.shouldHave(text("Оплата по карте"));
    }

    void isCredit() {
        headerBuyType.shouldHave(text("Кредит по данным карты"));
    }

    void debitBuy() {
        btnDebitBuy.click();
        isDebit();
    }

    void creditBuy() {
        btnCreditBuy.click();
        isCredit();
    }

    void useApprovedCard() {
        card.setValue(DataHelper.getApprovedCard());
    }

    void useDeclinedCard() {
        card.setValue(DataHelper.getDeclinedCard());
    }

    void useInvalidCard() {
        card.setValue(DataHelper.getInvalidCard());
    }

    void expectNoticeCardInvalidPeriod() {
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text("Неверно указан срок действия карты"));
    }

    void setInvalidMonth() {
        month.setValue(DataHelper.getInvalidMonth());
        year.setValue(DataHelper.generateYear());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
    }

    void setInvalidYearInFuture() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.getInvalidYearInFuture());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
    }

    void setInvalidYearInPast() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.getInvalidYearInPast());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
    }

    void expectNoticeCardExpired() {
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text("Истёк срок действия карты"));
    }

    void noSetHolder() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.generateYear());
        cvc.setValue(DataHelper.generateCvc());
    }

    void expectNoticeFieldRequired() {
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    void setInvalidCvc() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.generateYear());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.getInvalidCvc());
    }

    void expectNoticeInvalidFormat() {
        btnContinue.click();
        subErr.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text("Неверный формат"));
    }

    void fillOtherCardFields() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.generateYear());
        holder.setValue(DataHelper.generateName());
        cvc.setValue(DataHelper.generateCvc());
    }

    void expectAccept() {
        fillOtherCardFields();
        btnContinue.click();
        noticeAccepted.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text("Операция одобрена Банком"));
    }

    void expectReject() {
        fillOtherCardFields();
        btnContinue.click();
        noticeRejected.shouldBe(appear, Duration.ofSeconds(BANK_RESPONSE_WAIT))
                .shouldHave(text("Банк отказал в проведении операции"));
    }

    @SneakyThrows
    public void buy() {
        btnContinue.click();
        Thread.sleep(BANK_RESPONSE_WAIT * 1_000L);
    }
}
