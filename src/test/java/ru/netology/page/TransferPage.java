package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from =  $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id = 'action-transfer']");
    private SelenideElement error = $("[data-test-id = 'error-notification']");
    private SelenideElement cancel = $("[data-test-id = 'action-cancel']");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public void setAmount(String sum) {
        amount.setValue(sum);
    }

    public void setFromCardField(String numberCard) {
        from.setValue(numberCard);
    }

    public DashboardPage getTransfer() {
        transferButton.click();
        return new DashboardPage();
    }

    public void invalidTransfer() {
        transferButton.click();
        error.shouldBe(visible);
    }

    public DashboardPage cancelTransfer() {
        cancel.click();
        return new DashboardPage();
    }
}
