package ru.netology;

import ru.netology.data.DataHelper.*;

public class FrontendHelper {

    BuyType buyType;
    AppPage appPage;

    public FrontendHelper(BuyType buyType) {
        this.buyType = buyType;
        this.appPage = new AppPage();
    }

    void chooseBuyType() {
        if (buyType == BuyType.CREDIT)
            appPage.creditBuy();
        else if (buyType == BuyType.DEBIT)
            appPage.debitBuy();
    }

    void buyOnApprovedCard(){
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.expectAccept();
    };

    void buyOnDeclinedCard() {
        chooseBuyType();
        appPage.useDeclinedCard();
        appPage.expectReject();
    }

    void buyOnInvalidCard() {
        chooseBuyType();
        appPage.useInvalidCard();
        appPage.expectReject();
    }

    void noticeIfMonthInvalid() {
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.setInvalidMonth();
        appPage.expectNoticeCardInvalidPeriod();
    }

    void noticeIfYearInvalidInFuture() {
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.setInvalidYearInFuture();
        appPage.expectNoticeCardInvalidPeriod();
    }

    void noticeIfYearInvalidInPast() {
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.setInvalidYearInPast();
        appPage.expectNoticeCardExpired();
    }

    void noticeIfHolderIsEmpty() {
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.noSetHolder();
        appPage.expectNoticeFieldRequired();
    }

    void noticeIfCvcIsInvalid() {
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.setInvalidCvc();
        appPage.expectNoticeInvalidFormat();
    }

    void buyOnApprovedCardNoExpect(){
        chooseBuyType();
        appPage.useApprovedCard();
        appPage.fillOtherCardFields();
        appPage.buy();
    };

    void buyOnDeclinedCardNoExpect(){
        chooseBuyType();
        appPage.useDeclinedCard();
        appPage.fillOtherCardFields();
        appPage.buy();
    };

    void buyOnInvalidCardNoExpect(){
        chooseBuyType();
        appPage.useInvalidCard();
        appPage.fillOtherCardFields();
        appPage.buy();
    };

}
