import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;

class WallTest {
    private static final Logger log = Logger.getLogger(WallTest.class);
    private static Wall wall;
    private static final String token =
            "30f2cd1caf08162a27c4b477a3abfeac088d7472753c394fd5f1e0f7af418a70522222b36a00e1ddb4460";
    private static final String postText = "Good evening";
    private static final String editText = "Good morning";

    WallTest() throws IOException, URISyntaxException {
    }

    @BeforeAll
    static void setUp() {
        wall = new Wall();
    }

    private String postId = wall.wallPost(token, postText);

    @Test
    void messageIsPostedTest() throws IOException, URISyntaxException {
        Assertions.assertTrue(wall.messageIsPosted(token, postId, postText));
        log.info("info message");
    }

    @Test
    void messageIsEditedTest() throws IOException, URISyntaxException {
        wall.wallEditPost(token, postId, editText);
        Assertions.assertTrue(wall.messageIsEdited(token, postId, editText));
    }

    @Test
    void postIsDeletedTest() throws IOException, URISyntaxException {
        wall.wallDeletePost(token, postId);
        Assertions.assertTrue(wall.postIsDeleted(token, postId, postText));
        log.error("error message");
    }
}