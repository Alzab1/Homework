import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private JavascriptExecutor executor;
    @FindBy(id = "mailbox:login")
    private WebElement loginField;

    @FindBy(id = "mailbox:domain")
    private WebElement mailboxDomain;

    @FindBy(id = "mailbox:password")
    private WebElement emailField;

    @FindBy(xpath = ".//input[contains(@value,'Ввести пароль')]")
    private WebElement buttonEnter;

    @FindBy(xpath = ".//a[contains(text(),'выход')]")
    private WebElement logoutLink;

    @FindBy(xpath = ".//span[@class='ll-av__checkbox']")
    private WebElement letterCheckbox;

    @FindBy(xpath = ".//span[@class='ll-av__checkbox']")
    private List<WebElement> letterCheckboxes;

    @FindBy(xpath = ".//span[contains(text(),'Спам')]")
    private WebElement spamButton;

    @FindBy(xpath = ".//div[contains(text(),'Спам')]")
    private WebElement spamFolder;

    @FindBy(xpath = ".//span[contains(text(),'Не спам')]")
    private WebElement noSpamButton;

    @FindBy(xpath = ".//a[@class='link link_secondary']")
    private WebElement spamFolderText;

    @FindBy(xpath = ".//span[contains(text(),'Написать письмо')]")
    private WebElement createLetterButton;

    @FindBy(xpath = "(//input[contains(@class,'container')])[1]")
    private WebElement toWhomField;

    @FindBy(xpath = ".//span[contains(text(),'Отправить')]")
    private WebElement sendLetterButton;

    @FindBy(xpath = ".//span[contains(text(),'Отправить') and @style='visibility: visible;']")
    private WebElement confirmSendLetterButton;

    @FindBy(xpath = ".//span[@title='Ещё']")
    private WebElement buttonMore;

    @FindBy(xpath = ".//span[contains(text(),'Пометить флажком')]")
    private WebElement flag;

    @FindBy(xpath = ".//span[contains(text(),'Выделить все')]")
    private WebElement selectAllLettersButton;

    @FindBy(xpath = ".//span[contains(text(),'Снять флажок')]")
    private WebElement deFlag;

    @FindBy(xpath = ".//button[@title]")
    private List<WebElement> flagStatus;

    @FindBy(xpath = ".//button[contains(@title,'флаж')]")
    private WebElement flagSign;

    @FindBy(xpath = ".//span[contains(text(),'Снять выделение')]")
    private WebElement deselectAllLetters;

    @FindBy(xpath = ".//span[contains(text(),'Сохранить в шаблон')]")
    private WebElement alertLetterSendedTemplate;

    @FindBy(xpath = ".//a[@data-uidl-id]")
    private WebElement letterID;

    @FindBy(xpath = ".//a[@data-uidl-id]")
    private List<WebElement> listLetterID;

    @FindBy(xpath = ".//div[contains(text(),'Входящие')]")
    private WebElement inboxFolder;

    private String idAddedToSpamLetter;
    private String idLetterReturnedFromSpam;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) this.driver;
        PageFactory.initElements(driver, this);
    }

    public void signIn(String login, String password, String domainName) {
        loginField.clear();
        loginField.sendKeys(login);
        new Select(mailboxDomain).selectByVisibleText(domainName);
        buttonEnter.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.clear();
        emailField.sendKeys(password);
        buttonEnter.click();
        new WebDriverWait(driver, 60, 5000)
                .until(ExpectedConditions.visibilityOf(logoutLink));
    }

    public void addLetterToSpam() {
        Assert.assertFalse(letterCheckboxes.isEmpty(), "No letters found!");
        executor.executeScript("arguments[0].click();", letterCheckbox);
        idAddedToSpamLetter = listLetterID.get(0).getAttribute("data-uidl-id");
        spamButton.click();
    }

    public boolean letterIsAddedToSpam() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(spamFolder));
        spamFolder.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(spamFolderText));
        return listLetterID.stream().map(w -> w.getAttribute("data-uidl-id"))
                .anyMatch(idAddedToSpamLetter::equals);
    }

    public void returnLetterFromSpam() {
        spamFolder.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(spamFolderText));
        Assert.assertFalse(letterCheckboxes.isEmpty(), "No letters found!");
        executor.executeScript("arguments[0].click();", letterCheckbox);
        idLetterReturnedFromSpam = listLetterID.get(0).getAttribute("data-uidl-id");
        noSpamButton.click();
    }

    public boolean letterIsReturnedFromSpam() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(inboxFolder));
        inboxFolder.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(letterID));
        ;
        return listLetterID.stream().map(w -> w.getAttribute("data-uidl-id"))
                .anyMatch(idLetterReturnedFromSpam::equals);
    }

    public void sendLetterForGroup(String mail1, String mail2) {
        createLetterButton.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(toWhomField));
        toWhomField.sendKeys(mail1, ",", mail2);
        sendLetterButton.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(confirmSendLetterButton));
        confirmSendLetterButton.click();
    }

    public boolean letterIsSent() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(alertLetterSendedTemplate));
        return alertLetterSendedTemplate.isDisplayed();
    }

    public void selectLetters(int qtySelectLetters) {
        waitForLoad(driver);
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(inboxFolder));
        inboxFolder.click();
        new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).ignoring(java.lang.AssertionError.class);
        Assert.assertFalse(letterCheckboxes.size() < qtySelectLetters, "No so many letters!");
        for (int i = 0; i < qtySelectLetters; i++) {
            executor.executeScript("arguments[0].click();", letterCheckboxes.get(i));
        }
    }

    public void flagSelectedLetters() {
        executor.executeScript("arguments[0].click();", buttonMore);
        flag.click();
    }

    public boolean lettersIsFlagged(int qtySelectLetters) {
        return flagStatus.stream().limit(qtySelectLetters).map((w) -> w.getAttribute("title"))
                .allMatch("Снять флажок"::equals);
    }

    public void deFlagAllLetters() {
        new WebDriverWait(driver, 10).until(ExpectedConditions
                .elementToBeClickable(selectAllLettersButton));
        selectAllLettersButton.click();
        executor.executeScript("arguments[0].click();", buttonMore);
        deFlag.click();
        deselectAllLetters.click();
    }

    public boolean allLettersAreDeflaged() {
      return flagStatus.stream().map((w) -> w.getAttribute("title")).allMatch("Пометить флажком"::equals);
    }

    public void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(pageLoadCondition);
    }

    public boolean logoutLinkPresents() {
        waitForLoad(driver);
        return logoutLink.isDisplayed();
    }
}