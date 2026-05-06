package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.AddTranslationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase10AddTranslationTest extends BaseTest {
    @Test
    public void addTranslation() {
        login();
        AddTranslationPage page = new AddTranslationPage(driver);
        page.openEnglishDictionary();
        page.fillTranslation(TestData.WORD, TestData.TRANSLATION);
        page.chooseTheme(TestData.THEME);
        page.save();

        Assertions.assertTrue(page.isSavedOrConfirmation(), "Translation should be saved or require confirmation");
    }
}
