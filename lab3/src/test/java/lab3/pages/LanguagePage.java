package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class LanguagePage extends BasePage {
    public LanguagePage(WebDriver driver) {
        super(driver);
    }

    public void chooseLanguage(String languageText) {

        clickByJs("//a[normalize-space(.)='" + languageText + "']");

    }

    public boolean isLanguageApplied(String expected) {
        return isVisible("//a[normalize-space(.)='" + expected + "']");
    }
}
