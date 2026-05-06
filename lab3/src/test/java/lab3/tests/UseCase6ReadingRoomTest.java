package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.ReadingRoomPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase6ReadingRoomTest extends BaseTest {
    @Test
    public void readTextFromReadingRoom() {
        ReadingRoomPage readingRoom = new ReadingRoomPage(driver);
        readingRoom.openDirect();
        readingRoom.openFirstAuthor();
        readingRoom.openFirstMaterial();

        Assertions.assertTrue(readingRoom.isTextVisible(), "Reading room material should be visible");
    }
}
