package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ReadingRoomPage extends BasePage {
    public ReadingRoomPage(WebDriver driver) {
        super(driver);
    }

    public void openDirect() {
        openPath("/m.exe?a=96");
    }

    public void openFirstAuthor() {
        click("(//a[contains(@href,'a=') and not(contains(@href,'l1='))])[1]");
    }

    public void openFirstMaterial() {
        click("(//a[contains(@href,'a=') and (contains(@href,'txt') or contains(@href,'text') or contains(@href,'book'))])[1]");
    }

    public boolean isTextVisible() {
        return isVisible("//body//*[string-length(normalize-space(.))>50]");
    }
}
