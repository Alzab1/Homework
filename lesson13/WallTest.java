import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class WallTest {
    static Wall wall;
    private static final String token =
            "9d40074b85ac5cf5693eb143813a9ee20a5f570c353707fdf60354510b41df3741a68711c8cf32dbe4ba6";
    private static final String postText = "Good evening";
    private static final String editText = "Good morning";

    @BeforeAll
    static void setUp() {
        wall = new Wall();
    }

    @Test
    void messageIsPostedTest()
            throws IOException, URISyntaxException {
        Assertions.assertTrue
                (wall.messageIsPosted(postText, token));
    }

    @Test
    void messageIsEditedTest() throws IOException, URISyntaxException {
        Assertions.assertTrue
                (wall.messageIsEdited(token, postText, editText));
    }

    @Test
    void postIsDeletedTest() throws IOException, URISyntaxException {
        Assertions.assertTrue(
                wall.postIsDeleted(token, postText));
    }
}