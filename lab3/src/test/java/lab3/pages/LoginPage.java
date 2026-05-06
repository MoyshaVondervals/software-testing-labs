package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String login, String password) {
        type("//td[normalize-space(.)='Name']/following-sibling::td//input", login);
        type("//td[normalize-space(.)='Password']/following-sibling::td//input", password);
        click("//button[normalize-space(.)='Sign in'] | //input[@type='submit' and (contains(@value,'Sign in') or contains(@value,'Войти'))]");
    }

    public boolean isLoggedIn() {
        return isVisible("//a[contains(normalize-space(.),'Sign out') or contains(normalize-space(.),'Выйти')]");
    }
}
