package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.ForumPage;
import lab3.pages.UserProfilePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase9UserProfileTest extends BaseTest {
    @Test
    public void viewUserQuestionsHistory() {
        ForumPage forum = new ForumPage(driver);
        forum.openEnglishForum();

        UserProfilePage profile = new UserProfilePage(driver);
        profile.openFirstUserLink();

        Assertions.assertTrue(profile.hasForumParticipation(), "Forum participation section should be visible");
        profile.openQuestionsHistory();
        Assertions.assertTrue(profile.hasQuestionsHistory(), "Questions history should be visible");
    }
}
