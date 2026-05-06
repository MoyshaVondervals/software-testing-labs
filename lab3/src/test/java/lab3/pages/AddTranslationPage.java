package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class AddTranslationPage extends BasePage {
    public AddTranslationPage(WebDriver driver) {
        super(driver);
    }

    public void openEnglishDictionary() {
        openPath("/m.exe?a=486&l1=1");
    }

    public void fillTranslation(String word, String translation) {
        type("(//input[@type='text'])[1]", word);
        type("(//input[@type='text'])[2]", translation);
    }

    public void chooseTheme(String theme) {
        if (isVisible("//select")) {
            selectByVisibleText("//select", theme);
        }
    }

    public void save() {
        click("//button[contains(normalize-space(.),'Save') or contains(normalize-space(.),'Сохранить')] | //input[@type='submit' and (contains(@value,'Save') or contains(@value,'Сохранить'))]");
    }

    public boolean isSavedOrConfirmation() {
        return isVisible("//*[contains(normalize-space(.),'saved') or contains(normalize-space(.),'Сохранено') or contains(normalize-space(.),'confirm') or contains(normalize-space(.),'подтверд')]");
    }
}
