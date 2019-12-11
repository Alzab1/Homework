import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ParisTest {
    private ChromeDriver driver;
    private int startAfterDays = 3;
    private int stayNights = 7;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
    }

    @BeforeMethod
    public void openHomePage() {
        driver = new ChromeDriver();
        driver.get("https://www.booking.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        LocalDate checkInDate = LocalDate.now().plusDays(startAfterDays);
        LocalDate checkOutDate = checkInDate.plusDays(stayNights);
        driver.findElement(By.xpath(".//input[@type='search']")).sendKeys("Париж");
        driver.findElement(By.xpath(".//div[@data-mode='checkin']")).click();
        driver.findElement(By.xpath(".//td[@data-date='".concat(checkInDate.toString()).concat("']"))).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//td[@data-date='".concat(checkOutDate.toString()).concat("']"))).click();
        driver.findElement(By.xpath(".//button[@data-sb-id='main']")).click();
    }

    @Test
    public void checkLowCostPriceParis() throws InterruptedException {
        int pricePerNight = Integer.parseInt(driver.findElement(By.xpath(".//a[@data-name='pri'][1]"))
                .getText().substring(12, 15));
        driver.findElement(By.xpath(".//a[@data-name='pri'][1]")).click();
        Thread.sleep(5000);
        if (driver.findElements(By.xpath("//a[@class='hotel_name_link url']")).size() == 0) {
            System.out.println("Иди на вокзал");
        } else {
            List<WebElement> elements1 = driver.findElements(By.xpath(".//div[contains(@aria-label,'Оценка')]"));
            Comparator<WebElement> scoreComparator = Comparator.comparing(WebElement::getText);
            elements1.sort(scoreComparator.reversed());
            elements1.get(0).click();
            ArrayList<String> win = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(win.get(1));
            int totalPrice = Integer.parseInt(driver
                    .findElement(By.xpath(".//div[contains(@class,'bui-price-display__value prco')]"))
                    .getText().substring(4).replace(" ", ""));
            Assert.assertTrue(pricePerNight >= totalPrice / stayNights);
            driver.close();
            driver.switchTo().window(win.get(0));
        }
    }

    @Test
    public void checkHighCostPriceParis() throws InterruptedException {
        String adults = "4";
        String rooms = "2";
        new Select(driver.findElement(By.id("group_adults"))).selectByValue(adults);
        new Select(driver.findElement(By.id("no_rooms"))).selectByValue(rooms);
        driver.findElement(By.xpath(".//button[@data-sb-id]")).click();
        int pricePerNight = Integer.parseInt(driver.findElement(By.xpath(".//a[@data-name='pri'][last()]"))
                .getText().substring(4, 7));
        driver.findElement(By.xpath(".//a[@data-name='pri'][last()]")).click();
        driver.findElement(By.xpath(".//a[contains(text(),'Цена (сначала самая низкая)')] ")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//a[@class='hotel_name_link url']")).click();
        ArrayList<String> win = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(win.get(1));
        int totalPrice = Integer.parseInt(driver
                .findElement(By.xpath(".//div[contains(@class,'bui-price-display__value prco-ltr-right')]"))
                .getText().substring(4).replace(" ", ""));
        Assert.assertTrue(pricePerNight <= totalPrice / stayNights);
        driver.close();
        driver.switchTo().window(win.get(0));
    }

    @Test
    public void checkCardNumberParis() throws InterruptedException {
        String cardNumber = "4242 4242 4242 424";
        String adults = "4";
        String rooms = "2";
        new Select(driver.findElement(By.id("group_adults"))).selectByValue(adults);
        new Select(driver.findElement(By.id("no_rooms"))).selectByValue(rooms);
        driver.findElement(By.xpath(".//button[@data-sb-id]")).click();
        driver.findElement(By.xpath(".//a[@data-name='pri'][last()]")).click();
        driver.findElement(By.xpath(".//a[contains(text(),'Цена (сначала самая низкая)')] ")).click();
         Thread.sleep(5000);
        driver.findElement(By.xpath("//a[@class='hotel_name_link url']")).click();
        ArrayList<String> win = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(win.get(1));
        driver.findElement(By.xpath(".//span[contains(text(),'Забронировать эти варианты')]")).click();
        driver.findElement(By.xpath(".//button[contains(text(),'бронирую')]")).click();
        driver.findElement(By.xpath(".//input[@id='lastname']")).sendKeys("Zabauski");
        driver.findElement(By.xpath(".//input[@id='email']")).sendKeys("zabavasa@tut.by");
        driver.findElement(By.xpath(".//input[@id='email_confirm']")).sendKeys("zabavasa@tut.by");
        driver.findElement(By.xpath(".//button[@name='book']")).click();
        driver.findElement(By.xpath(".//input[@id='phone']")).sendKeys("297023568");
        driver.findElement(By.xpath(".//input[@id='pay-now']")).click();
        new Select(driver.findElement(By.id("cc_type"))).selectByValue("MasterCard");
        driver.findElement(By.xpath(".//input[@id='cc_number']")).sendKeys(cardNumber);
        driver.findElement(By.xpath(".//input[@id='cc_cvc']")).sendKeys("123");
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By
                .xpath(".//p[contains(text(),'Введите номер действительной кредитной карты')]")).isDisplayed());
        driver.close();
        driver.switchTo().window(win.get(0));
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    @AfterClass
    public void driverQuite() {
        driver.quit();
    }
}