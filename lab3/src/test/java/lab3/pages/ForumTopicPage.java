package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ForumTopicPage extends BasePage {
    public ForumTopicPage(WebDriver driver) {
        super(driver);
    }

    public void reply(String text) {
        type("//textarea", text);
    }

    public void preview() {
        click("//button[contains(normalize-space(.),'Preview') or contains(normalize-space(.),'Предварительный')]");
    }

    public void save() {
        click("//button[contains(normalize-space(.),'Save') or contains(normalize-space(.),'Сохранить')]");
    }

    public boolean isReplyVisible(String snippet) {
        return isVisible("//*[contains(normalize-space(.),'" + snippet + "')]");
    }
}
