package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import org.junit.jupiter.api.Nested;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {
    private String amount = "2000";
    @BeforeEach
    void setup (){open("http://localhost:9999");}

    @Nested
    public class ValidLogin {
        @BeforeEach
        public void login() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val codeVerify = DataHelper.getVerificationCodeFor();
            verificationPage.validVerify(codeVerify);
        }

        @Test
        void shouldTransferFromCard1ToCard2() {
            val dashboardPage = new DashboardPage();
            int expectedBalance1 = dashboardPage.getCurrentBalanceCard1() + Integer.parseInt(amount);
            int expectedBalance2 = dashboardPage.getCurrentBalanceCard2() - Integer.parseInt(amount);
            dashboardPage.depositCard1();
            val transferPage = new TransferPage();
            transferPage.setAmount(amount);
            transferPage.setFromCardField(DataHelper.getCard2());
            transferPage.getTransfer();
            assertEquals(expectedBalance1, dashboardPage.getCurrentBalanceCard1());
            assertEquals(expectedBalance2, dashboardPage.getCurrentBalanceCard2());
        }

        @Test
        void shouldTransferFromCard2ToCard1() {
            val dashboardPage = new DashboardPage();
            int expectedBalance1 = dashboardPage.getCurrentBalanceCard1() - Integer.parseInt(amount);
            int expectedBalance2 = dashboardPage.getCurrentBalanceCard2() + Integer.parseInt(amount);
            dashboardPage.depositCard2();
            val transferPage = new TransferPage();
            transferPage.setAmount(amount);
            transferPage.setFromCardField(DataHelper.getCard1());
            transferPage.getTransfer();
            assertEquals(expectedBalance1, dashboardPage.getCurrentBalanceCard1());
            assertEquals(expectedBalance2, dashboardPage.getCurrentBalanceCard2());
        }

        @Test
        void shouldErrorWhenTransferFromInvalidCard() {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.depositCard1();
            TransferPage transferPage = new TransferPage();
            transferPage.setAmount(amount);
            transferPage.setFromCardField(DataHelper.getCard3());
            transferPage.getTransfer();
            transferPage.invalidTransfer();
        }

        @Test
        void shouldNotTransferIfCancel() {
            val dashboardPage = new DashboardPage();
            int expectedBalance1 = dashboardPage.getCurrentBalanceCard1();
            int expectedBalance2 = dashboardPage.getCurrentBalanceCard2();
            dashboardPage.depositCard2();
            val transferPage = new TransferPage();
            transferPage.setAmount(amount);
            transferPage.setFromCardField(DataHelper.getCard1());
            transferPage.cancelTransfer();
            assertEquals(expectedBalance1, dashboardPage.getCurrentBalanceCard1());
            assertEquals(expectedBalance2, dashboardPage.getCurrentBalanceCard2());
        }
    }

    @Nested
    public class InvalidLogin {
        @Test
        void shouldInvalidLogin() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getWrongAuthInfo();
            loginPage.invalidLogin(authInfo);
        }
        @Test
        void shouldInvalidCode(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val codeVerify = DataHelper.getWrongVerificationCodeFor();
            verificationPage.invalidVerify(codeVerify);
        }
    }
}

