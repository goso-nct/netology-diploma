package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShopApplicationTest {

    SelenideElement element;

//    @BeforeAll
//    static void setUpAll() {
//        SelenideLogger.addListener("allure", new AllureSelenide());
//    }

//    @BeforeEach
//    public void setUpEach() {
//        user = DataGenerator.Registration.generateUser();
//        firstMeetingDate = DataGenerator.generateDate(3, 5);
//        secondMeetingDate = DataGenerator.generateDate(5, 10);
//        open("http://localhost:9999");
//    }
//
//    @AfterEach
//    void tearDownEach() {
//        closeWebDriver();
//    }
//
//    @AfterAll
//    static void tearDownAll() { SelenideLogger.removeListener("allure"); }

//    @Test
//    void shouldDontSuccessfulIfInvalidCity() {
//        $("[data-test-id=city] input").setValue(DataGenerator.invalidCity);
//        $("[data-test-id=date] input").doubleClick();
//        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
//        $("[data-test-id=date] input").setValue(firstMeetingDate);
//
//        $("[data-test-id=name] input").setValue(user.getName());
//        $("[data-test-id=phone] input").setValue(user.getPhone());
//        $("[data-test-id=agreement] .checkbox__box").click();
//        $("[role=button] .button__content").click();
//
//        $(byText("Доставка в выбранный город недоступна")).isDisplayed();
//    }

    //@Test
    @RepeatedTest(5)
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

        //System.out.println(DataHelper.generateMonth());
        //System.out.println(DataHelper.generateName());
        System.out.println(DataHelper.generateCIV());
    }



}
