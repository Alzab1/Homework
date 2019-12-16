import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BookingTest {
    private WebDriver driver;
    private Booking booking;
    private String url;
    private String place = "Париж";
    private int startAfterDays = 3;
    private int stayNights = 7;
    private String adults = "4";
    private String rooms = "2";
    private String lastName = "Zabauski";
    private String email = "zabavasa@tut.by";
    private String phone = "297023568";
    private String ccType = "MasterCard";
    private String ccNumber = "4242 4242 4242 424";
    private String ccCvc = "123";

    @BeforeClass
    public void setProperty() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        Booking bookingWithoutDriver = new Booking();
        url = bookingWithoutDriver.homePageSearchCredentials(place, startAfterDays, stayNights);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
        booking = new Booking(driver);
    }

    @Test
    public void lowPriceHotelsIsExist() {
        booking.selectLowestBudgetPriceHotels();
        Assert.assertTrue(booking.checkAvailability());
    }

    @Test(dependsOnMethods = "lowPriceHotelsIsExist")
    public void checkPriceCheapestHotelWithTopRating() throws InterruptedException {
        booking.selectLowestBudgetPriceHotels();
        Assert.assertTrue(booking.compareTopRatingCheapestHotelPriceWithFilterRange(stayNights));
    }

    @Test
    public void checkPriceCheapestHotelsFromMostExpensive() throws InterruptedException {
        booking.selectAdultsRooms(adults, rooms);
        booking.filterCheapestOfExpensiveHotels();
        Assert.assertTrue(booking.cheapestPriceIsNotLowerFilterRange(stayNights));
    }

    @Test
    public void checkCardNumberAlert() throws InterruptedException {
        booking.selectAdultsRooms(adults, rooms);
        booking.filterCheapestOfExpensiveHotels();
        driver.get(booking.getHotelURL());
        Assert.assertTrue(booking.tryReserve(lastName, email, phone, ccType, ccNumber, ccCvc));
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}