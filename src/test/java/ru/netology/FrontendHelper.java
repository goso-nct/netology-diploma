package ru.netology;

import ru.netology.data.DataHelper.BuyType;

public class FrontendHelper {

    AppPage appPage;

    public FrontendHelper(BuyType buyType) {
        this.appPage = new AppPage();
        if (buyType == BuyType.CREDIT)
            appPage.creditBuy();
        else if (buyType == BuyType.DEBIT)
            appPage.debitBuy();
    }

    void buyOnApprovedCard(){
        appPage.useApprovedCard();
        appPage.expectAccept();
    };

    void buyOnDeclinedCard() {
        appPage.useDeclinedCard();
        appPage.expectReject();
    }

    void buyOnNonExistentCard() {
        appPage.useInvalidCard();
        appPage.expectReject();
    }

    public void checkNoticeEmptyFields() {
        appPage.buy();
        appPage.checkSubBlankFields();
    }

    public void noticeIfCardInvalid() {
        appPage.setInvalidCard();
        appPage.expectSubInvalidFormatInCard();
    }

    void noticeIfMonthInvalid() {
        appPage.useApprovedCard();
        appPage.setInvalidMonth();
        appPage.expectSubInvalidPeriodInMonth();
    }

    void noticeIfYearInvalidInFuture() {
        appPage.useApprovedCard();
        appPage.setInvalidYearInFuture();
        appPage.expectSubInvalidPeriodInYear();
    }

    void noticeIfYearInvalidInPast() {
        appPage.useApprovedCard();
        appPage.setInvalidYearInPast();
        appPage.expectSubCardExpiredInYear();
    }

    void noticeIfHolderIsEmpty() {
        appPage.useApprovedCard();
        appPage.setNoHolder();
        appPage.expectSubRequiredInHolder();
    }

    void noticeIfHolderIsInvalid() {
        appPage.useApprovedCard();
        appPage.setInvalidHolder();
        appPage.expectSubInvalidFormatInHolder();
    }

    public void noticeIfHolderIsLong() {
        appPage.useApprovedCard();
        appPage.setLongHolder();
        appPage.expectSubInvalidFormatInHolder();
    }

    public void noticeIfHolderIsShort() {
        appPage.useApprovedCard();
        appPage.setShortHolder();
        appPage.expectSubInvalidFormatInHolder();
    }

    void noticeIfCvcIsInvalid() {
        appPage.useApprovedCard();
        appPage.setInvalidCvc();
        appPage.expectSubInvalidFormatInCvc();
    }

    void buyOnApprovedCardNoExpect(){
        appPage.useApprovedCard();
        appPage.setValidFieldsExceptCardNumber();
        appPage.buyAndWait();
    };

    void buyOnDeclinedCardNoExpect(){
        appPage.useDeclinedCard();
        appPage.setValidFieldsExceptCardNumber();
        appPage.buyAndWait();
    };

}
