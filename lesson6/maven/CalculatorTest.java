package pvt;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CalculatorTest {
    @DataProvider(name = "testAddData")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{7.0, 2.0, 9.0}, {0.0, 10.0, 10.0}};
    }

    static Calculator calculator;

    @BeforeClass
    public static void setUp() {
        calculator = new Calculator();
    }

    @Test(dataProvider = "testAddData")
    public void testAdd(double a, double b, double expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    @Test
    public void testDeduct() {
        assertEquals(13.5, calculator.deduct(17.5, 4.0));
    }

    @Test
    public void testMultiply() {
        assertEquals(10.0, calculator.multiply(2.5, 4.0));
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivision() {
        assertEquals(0, calculator.division(2, 0));
    }

    @Test
    @Parameters({"eData", "expResult"})
    public void testExponent(double eData, double expResult) {
        assertEquals(expResult, calculator.exponent(eData));
    }
}