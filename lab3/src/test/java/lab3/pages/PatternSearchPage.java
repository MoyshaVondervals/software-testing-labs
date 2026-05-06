package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class PatternSearchPage extends BasePage {
    public PatternSearchPage(WebDriver driver) {
        super(driver);
    }

    public void openDirect() {
        openPath("/m.exe?a=228");
    }

    public void fillPattern(String pattern) {
        type("//textarea | //input[@type='text']", pattern);
    }

    public void toggleFirstCheckbox() {
        click("(//input[@type='checkbox'])[1]");
    }

    public void search() {
        click("//button[contains(normalize-space(.),'Search') or contains(normalize-space(.),'Поиск')] | //input[@type='submit' and (contains(@value,'Search') or contains(@value,'Поиск'))]");
    }

    public boolean hasResultsOrEmpty() {
        return isVisible("//table//tr//td") || isVisible("//*[contains(normalize-space(.),'Nothing found') or contains(normalize-space(.),'Ничего не найдено')]");
    }
}
