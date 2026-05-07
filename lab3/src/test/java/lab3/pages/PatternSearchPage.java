package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class PatternSearchPage extends BasePage {
    public PatternSearchPage(WebDriver driver) {
        super(driver);
    }

    public void openDirect() {
        openPath("/m.exe?a=133");
    }

    public void fillPattern(String pattern) {
        org.openqa.selenium.WebElement input = waitVisible("//input[@name='s' and (not(@type) or @type='text')]");
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});" +
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
                input,
                pattern
        );
    }

    public void toggleFirstCheckbox() {
        org.openqa.selenium.WebElement checkbox = waitVisible("//input[@name='FindVerbs']");
        if (!checkbox.isSelected()) {
            clickByJs("//input[@name='FindVerbs']");
        }
    }

    public void search() {
        clickByJs("//input[@value='']");
    }

    public boolean hasResultsOrEmpty() {
        String resultsXpath = "//a[starts-with(@href, '/m.exe?') and contains(@href, 's=') and normalize-space(.)!='']";
        return isVisible(resultsXpath)
                || isVisible("//*[contains(normalize-space(.),'Nothing found') or contains(normalize-space(.),'Ничего не найдено')]");
    }
}
