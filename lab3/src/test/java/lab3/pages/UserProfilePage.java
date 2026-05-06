package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class UserProfilePage extends BasePage {
    public UserProfilePage(WebDriver driver) {
        super(driver);
    }

    public void openFirstUserLink() {
        click("(//a[contains(@href,'user') or contains(@href,'profile') or contains(@href,'a=')])[1]");
    }

    public boolean hasForumParticipation() {
        return isVisible("//*[contains(normalize-space(.),'Forum participation') or contains(normalize-space(.),'Участие в форумах')]");
    }

    public void openQuestionsHistory() {
        click("(//a[contains(@href,'questions') or contains(@href,'a=')])[1]");
    }

    public boolean hasQuestionsHistory() {
        return isVisible("//table//tr");
    }
}
