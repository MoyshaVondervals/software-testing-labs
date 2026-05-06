package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.ContactsPage;
import lab3.pages.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase7ContactsTest extends BaseTest {
    @Test
    public void openContacts() {
        HomePage home = new HomePage(driver);
        home.open();
        ContactsPage contacts = home.goToContacts();

        Assertions.assertTrue(contacts.hasContactInfo(), "Contact info should be visible");
    }
}
