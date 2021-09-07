package ru.netology;

public class CreditBuy {

    AppPage appPage;

    public CreditBuy() {
        appPage = new AppPage();
    }

    void buyByApprovedCard() {
        appPage.creditBuy();
        appPage.useApprovedCard();
        appPage.expectAccept();
    }

    void buyByDeclinedCard() {
        appPage.creditBuy();
        appPage.useDeclinedCard();
        appPage.expectReject();
    }

    void buyByInvalidCard() {
        appPage.creditBuy();
        appPage.useInvalidCard();
        appPage.expectReject();
    }


}
