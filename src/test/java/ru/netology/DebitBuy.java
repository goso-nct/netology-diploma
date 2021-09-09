package ru.netology;

public class DebitBuy {

    AppPage appPage;

    public DebitBuy() {
        appPage = new AppPage();
    }

    void buyByApprovedCard(){
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.expectAccept();
    };

    void buyByApprovedCardNoExpect(){
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.fillOtherCardFields();
        appPage.buy();
    };

    void buyByDeclinedCard() {
        appPage.debitBuy();
        appPage.useDeclinedCard();
        appPage.expectReject();
    }

    void buyByDeclinedCardNoExpect(){
        appPage.debitBuy();
        appPage.useDeclinedCard();
        appPage.fillOtherCardFields();
        appPage.buy();
    };

    void buyByInvalidCard() {
        appPage.debitBuy();
        appPage.useInvalidCard();
        appPage.expectReject();
    }

    void messageIfMonthInvalid() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidMonth();
        appPage.expectNoticeCardInvalidPeriod();
    }

    void messageIfYearInvalidInFuture() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidYearInFuture();
        appPage.expectNoticeCardInvalidPeriod();
    }

    void messageIfYearInvalidInPast() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidYearInPast();
        appPage.expectNoticeCardExpired();
    }

    void messageIfHolderIsEmpty() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.noSetHolder();
        appPage.expectNoticeFieldRequired();
    }

    void messageIfCvcIsInvalid() {
        appPage.debitBuy();
        appPage.useApprovedCard();
        appPage.setInvalidCvc();
        appPage.expectNoticeInvalidFormat();
    }

}
