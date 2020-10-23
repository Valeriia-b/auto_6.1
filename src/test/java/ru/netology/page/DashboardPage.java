package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement card1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']>[data-test-id='action-deposit']");
    private SelenideElement card2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']>[data-test-id='action-deposit']");
    private SelenideElement balanceCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement balanceCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage depositCard1() {
        card1Button.click();
        return new TransferPage();
    }

    public TransferPage depositCard2() {
        card2Button.click();
        return new TransferPage();
    }

    public int getCurrentBalanceCard1() {
        String balancePart = balanceCard1.getText().split(":")[1];
        int value = Integer.parseInt(balancePart.substring(0, balancePart.indexOf("р")).strip());
        return value;
    }

    public int getCurrentBalanceCard2() {
        String balancePart = balanceCard2.getText().split(":")[1];
        int value = Integer.parseInt(balancePart.substring(0, balancePart.indexOf("р")).strip());
        return value;
    }
}
