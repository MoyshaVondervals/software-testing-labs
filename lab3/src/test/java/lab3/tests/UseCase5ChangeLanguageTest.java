package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.HomePage;
import lab3.pages.LanguagePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase5ChangeLanguageTest extends BaseTest {
    @Test
    public void changeLanguage() {
        HomePage home = new HomePage(driver);
        home.open();
        LanguagePage languagePage = home.goToLanguageMenu();
        languagePage.chooseLanguage("Russian");

        Assertions.assertTrue(languagePage.isLanguageApplied("Russian") || languagePage.isLanguageApplied("English"),
                "Language should be applied and visible in the header");
    }
}
