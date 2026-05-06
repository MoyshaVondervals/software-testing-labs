package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ForumPage extends BasePage {
    public ForumPage(WebDriver driver) {
        super(driver);
    }

    public void openEnglishForum() {
        openPath("/m.exe?a=2&l1=1");
    }

    public ForumCreateTopicPage goToCreateTopic() {
        click("//a[contains(normalize-space(.),'Create topic') or contains(normalize-space(.),'New topic') or contains(normalize-space(.),'Создать тему')]");
        return new ForumCreateTopicPage(driver);
    }

    public ForumTopicPage openLatestTopic() {
        click("(//a[contains(@href,'topic') or contains(@href,'m.exe?a=')])[1]");
        return new ForumTopicPage(driver);
    }
}
