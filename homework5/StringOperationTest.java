import org.testng.annotations.*;

import static org.testng.Assert.*;

public class StringOperationTest {
    @DataProvider(name = "testNameCompareIgnoreCaseData")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"Alex", "ALEX"}, {"alex", "Alex"}, {"ALEX", "alex"}};
    }

    static StringOperation stringOperation;

    @BeforeClass
    public void setUp() {
        stringOperation = new StringOperation();
    }

    @Test
    public void testIsStartWith() {
        assertTrue(stringOperation.isStartWith("Alex", "A"));
    }

    @Test
    public void testIsStartWithNegative() {
        assertFalse(stringOperation.isStartWith("Peter", "A"));
    }

    @Test
    public void testSuperAlexMaker() {
        String actual = stringOperation.superAlexMaker("I am Alex");
        String expected = "I am SuperAlex";
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "testNameCompareIgnoreCaseData")
    public void testNameCompareIgnoreCase(String name1, String name2) {
        assertTrue(stringOperation.nameCompareIgnoreCase(name1, name2));
    }

    @Test(enabled = false)
    public void testTrimmer() {
        System.out.println("I'm Not Ready, please don't execute me");
    }

    @Test(expectedExceptions = StringIndexOutOfBoundsException.class)
    public void testCharByIndex() {
        char actual = stringOperation.charByIndex("Alex", 10);
        char expected = ' ';
        assertEquals(actual, expected);
    }
}