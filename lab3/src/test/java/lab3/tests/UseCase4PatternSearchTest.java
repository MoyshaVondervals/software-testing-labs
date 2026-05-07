package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.HomePage;
import lab3.pages.PatternSearchPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase4PatternSearchTest extends BaseTest {
    @Test
    public void searchByPattern() {
        HomePage home = new HomePage(driver);
        home.open();
        home.selectPopularEnglishRussian();
        PatternSearchPage page = new PatternSearchPage(driver);
        page.openDirect();
        page.fillPattern(TestData.PATTERN);
        page.toggleFirstCheckbox();
        page.search();

        Assertions.assertTrue(page.hasResultsOrEmpty(), "Pattern search should return results or empty state");
    }
}
