import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class BookingTest {
    public WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
    }

    @BeforeMethod
    public void openHomePage() {
        driver = new ChromeDriver();
        driver.get("https://www.booking.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void checkAvailabilityMoscow() {
        driver.findElement(By.xpath(".//input[@type='search']")).sendKeys("Москва");
        driver.findElement(By.xpath(".//div[@data-mode='checkin']")).click();
        driver.findElement(By.xpath(".//td[@data-date='2019-12-14']")).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//td[@data-date='2019-12-27']")).click();
        driver.findElement(By.xpath(".//button[@data-sb-id='main']")).click();
        System.out.println(driver.findElement(By.xpath(".//h1")).getText());
        System.out.println(driver.findElement(By.xpath(".//h2")).getText());
    }

    @Test
    public void filterByScore() throws InterruptedException {
        driver.findElement(By.xpath(".//input[@type='search']")).sendKeys("Москва");
        driver.findElement(By.xpath(".//div[@data-mode='checkin']")).click();
        driver.findElement(By.xpath(".//td[@data-date='2019-12-14']")).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//td[@data-date='2019-12-27']")).click();
        driver.findElement(By.xpath(".//button[@data-sb-id='main']")).click();
        driver.findElement(By.xpath(".//a[@data-category='review_score_and_price']")).click();
        Thread.sleep(10000);
        List<WebElement> webElements = driver.findElements(By.xpath(".//a[contains(@class,'sr_cta_button')]"));
        WebElement element = webElements.get(0);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        ArrayList<String> win2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(win2.get(1));
        String score = driver.findElement(By.xpath(".//a//div[@class='bui-review-score__badge']")).getText();
        int scoreInt = Integer.parseInt(score);
        System.out.println("Рейтинг лучшего отеля " + score);
        System.out.println(scoreInt >= 9 ? "То что надо!" : "Лучше на вокзале");
        System.out.println(driver.findElement(By.xpath(".//h1")).getText());
        System.out.println(driver.findElement(By.xpath(".//h2")).getText());
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