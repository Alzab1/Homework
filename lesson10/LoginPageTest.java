import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginPageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private int qtySelectLetters = 3;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://mail.ru");
        loginPage = new LoginPage(driver);
        loginPage.enterLoginCredentialsAndLogin("zabavasa", "pvtpvt2020", "@list.ru");
    }

    @Test
    public void loginTest() {
        Assert.assertTrue(loginPage.logoutLinkPresents());
    }

    @Test(priority = 9)
    public void sendLettersTest() {
        String mail1 = "zabavasa@tut.by";
        String mail2 = "zabavasa@list.ru";
        loginPage.sendLetterForGroup(mail1, mail2);
        Assert.assertTrue(loginPage.letterIsSended());
    }

    @Test
    public void addLetterToSpamTest() {
        Assert.assertTrue(loginPage.letterIsAddedToSpam());
    }

    @Test
    public void returnLetterFromSpamTest() {
        Assert.assertTrue(loginPage.letterIsReturnedFromSpam());
    }

    @Test(priority = 8)
    public void flagLetters() {
        loginPage.selectLetters(qtySelectLetters);
        loginPage.flagSelectedLetters();
        Assert.assertTrue(loginPage.lettersIsFlagged(qtySelectLetters));
    }

    @Test(dependsOnMethods = "flagLetters")
    public void deflagAllLettersTest() {
        Assert.assertTrue(loginPage.deFlagAllLetters());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}