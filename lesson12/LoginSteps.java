import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LoginSteps {
    private static final String MAIN_URL = "http://mail.ru";
    private static final String LOGIN = "zabavasa";
    private static final String PASSWORD = "pvtpvt2020";
    private static final String DOMAIN_NAME = "@list.ru";
    private LoginPage loginPage;
    private WebDriver driver;

    public LoginSteps() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Given("^I am on main application page$")
    public void loadMainPage() {
        driver.get(MAIN_URL);
    }

    @When("^I login as correct user$")
    public void loginAsCorrectUser() {
        loginPage.signIn(LOGIN, PASSWORD, DOMAIN_NAME);
    }

    @Then("^I see logout link$")
    public void seeLogoutLink() {
        Assert.assertTrue(loginPage.logoutLinkPresents());
    }

    @When("^I add letter to spam$")
    public void addLetterToSpam() {
        loginPage.addLetterToSpam();
    }

    @Then("^I see letter in spam folder$")
    public void letterIsAddedToSpam() {
        loginPage.letterIsAddedToSpam();
    }

    @When("^I return letter from spam$")
    public void returnLetterFromSpam() {
        loginPage.returnLetterFromSpam();
    }

    @Then("^I see letter in inbox folder$")
    public void letterIsReturnedFromSpam() {
        loginPage.letterIsReturnedFromSpam();
    }

    @When("^I select (-?\\d+) letters$")
    public void selectLetters(int qtySelectLetters) {
        loginPage.selectLetters(qtySelectLetters);
    }

    @And("^I flag selected letters$")
    public void flagSelectedLetters() {
        loginPage.flagSelectedLetters();
    }

    @Then("^I see selected (-?\\d+) letters are flagged$")
    public void lettersAreFlagged(int qtySelectLetters) {
        loginPage.lettersIsFlagged(qtySelectLetters);
    }

    @When("^I deflag all letters$")
    public void deflagAllLetters() {
        loginPage.deFlagAllLetters();
    }

    @Then("^I see all letters are deflaged$")
    public void allLettersAreDeflaged() {
        loginPage.allLettersAreDeflaged();
    }

    @When("^I send letter for group$")
    public void sendLetterForGroup() {
        String mail1 = "zabavasa@tut.by";
        String mail2 = "zabavasa@list.ru";
        loginPage.sendLetterForGroup(mail1, mail2);
    }

    @Then("^I see window that letters are sent$")
    public void lettersAreSent() {
        loginPage.letterIsSent();
    }

    @After
    public void afterClass() {
        driver.quit();
    }
}