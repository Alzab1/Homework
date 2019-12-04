package pvt;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AppTest {
    @DataProvider(name = "testNameCompareIgnoreCaseData")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"Alex", "ALEX"}, {"alex", "Alex"}, {"ALEX", "alex"}};
    }

    static App app;

    @BeforeClass
    public void setUp() {
        app = new App();
    }

    @Test
    public void testIsStartWith() {
        assertTrue(app.isStartWith("Alex", "A"));
    }

    @Test
    public void testIsStartWithNegative() {
        assertFalse(app.isStartWith("Peter", "A"));
    }

    @Test
    public void testSuperAlexMaker() {
        assertEquals("I am SuperAlex", app.superAlexMaker("I am Alex"));
    }

    @Test(dataProvider = "testNameCompareIgnoreCaseData")
    public void testNameCompareIgnoreCase(String name1, String name2) {
        assertTrue(app.nameCompareIgnoreCase(name1, name2));
    }

    @Test(enabled = false)
    public void testTrimmer() {
        System.out.println("I'm Not Ready, please don't execute me");
    }

    @Test(expectedExceptions = StringIndexOutOfBoundsException.class)
    public void testCharByIndex() {
        assertEquals(' ', app.charByIndex("Alex", 10));
    }
}