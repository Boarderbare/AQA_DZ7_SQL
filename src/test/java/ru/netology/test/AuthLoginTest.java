package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthLoginTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @AfterAll
    public static void cleanData() {
        DataHelper.cleanData();
    }

    @Test
    void shouldValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.validLogin(DataHelper.getUser())
                .codeVerify();
    }

    @Test
    void shouldNotLoginFakerUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.authentification(DataHelper.getFakerUser());
        loginPage.errorMessage();
    }

    @Test
    void shouldBlockUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.authentification(DataHelper.getUserWithInvalidPassword());
        loginPage.errorMessage();
        loginPage.errorMessageClose();

        loginPage.cleanField();
        loginPage.authentification(DataHelper.getUserWithInvalidPassword());
        loginPage.errorMessage();
        loginPage.errorMessageClose();

        loginPage.cleanField();
        loginPage.authentification(DataHelper.getUserWithInvalidPassword());
        loginPage.errorMessage();
        loginPage.errorMessageClose();

        var actual = DataHelper.getStatusUser();
        Assertions.assertEquals("blocked", actual);
    }
}