package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.ForumPage;
import lab3.pages.ForumTopicPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase8ReplyTest extends BaseTest {
    @Test
    public void replyToTopic() {
        login();
        ForumPage forum = new ForumPage(driver);
        forum.openEnglishForum();
        ForumTopicPage topic = forum.openLatestTopic();
        topic.reply(TestData.REPLY_BODY);
        topic.preview();
        topic.save();

        Assertions.assertTrue(topic.isReplyVisible(TestData.REPLY_BODY), "Reply should be visible in the topic");
    }
}
