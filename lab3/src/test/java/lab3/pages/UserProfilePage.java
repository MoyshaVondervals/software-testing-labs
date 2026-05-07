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
        clickByJs("//tbody/tr[2]/td[3]//a");
    }

    public boolean hasQuestionsHistory() {
        return isVisible("//table//tr");
    }

    public void openUserProfile(String userName) {
        String encoded = java.net.URLEncoder.encode(userName, java.nio.charset.StandardCharsets.UTF_8);
        openPath("/m.exe?a=116&UserName=" + encoded + "&l=1");
    }

    public boolean hasThreadAndReplyStats() {
        String threadsCell = "//tbody/tr[2]/td[3]";
        String repliesCell = "//tbody/tr[2]/td[4]";
        boolean hasThreadsCell = isVisible(threadsCell + "//a") || isVisible(threadsCell + "[normalize-space(.)='-']");
        boolean hasRepliesCell = isVisible(repliesCell + "//a") || isVisible(repliesCell + "[normalize-space(.)='-']");
        return hasThreadsCell && hasRepliesCell;
    }
}
