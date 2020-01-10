import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

public class LoginPageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private String mail1 = "zabavasa@tut.by";
    private String mail2 = "zabavasa@list.ru";

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
    public void sendLetterTestStepsUI() {
        EyesRunner runner = new ClassicRunner();
        Eyes eyes = new Eyes(runner);
        eyes.setApiKey("MFucFKpirVJpooQHQ4tOSSGBDv9feJNPJIOLEQdr8rY110");
        eyes.open(driver, "Mail", "StepsSendingLetter");
        eyes.setMatchLevel(MatchLevel.LAYOUT);
        eyes.checkWindow();
        loginPage.createLetter();
        eyes.checkWindow();
        loginPage.setAddress(mail2);
        eyes.checkWindow();
        loginPage.sendLetter();
        eyes.checkWindow();
        loginPage.confirmSendLetter();
        eyes.checkWindow();
        eyes.closeAsync();
        eyes.abortIfNotClosed();
        TestResultsSummary allTestResults = runner.getAllTestResults();
        System.out.println(allTestResults);
        allTestResults.getAllResults()[0].getTestResults().isPassed();
    }

    @Test
    public void loginTest() {
        Assert.assertTrue(loginPage.logoutLinkPresents());
    }

    @Test(priority = 9)
    public void sendLettersTest() {
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
        int qtySelectLetters = 3;
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