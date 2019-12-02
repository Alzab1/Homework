import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StringOperation2Test {
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

    @Test
    public void testTrimmer() {
        String actual = stringOperation.trimmer("  Alex ");
        String expected = "Alex";
        assertEquals(actual, expected);
    }

    @Test
    public void testCharByIndex() {
        char actual = stringOperation.charByIndex("Alex", 2);
        char expected = 'e';
        assertEquals(actual, expected);
    }
}