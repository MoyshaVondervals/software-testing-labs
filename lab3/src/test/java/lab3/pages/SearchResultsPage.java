package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasResults() {
        return isVisible("//table//tr//td") || isVisible("//div[contains(@class,'result')]//table");
    }

    public boolean hasSuggestionsOrEmptyMessage() {
        return isVisible("//*[contains(normalize-space(.),'Nothing found') or contains(normalize-space(.),'not found') or contains(normalize-space(.),'Ничего не найдено') or contains(normalize-space(.),'Предлагается')]");
    }
}
