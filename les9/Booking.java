import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Booking {
    @FindBy(xpath = ".//a[@data-name='pri']/label/div/span[1]")
    private WebElement yourBudgetFirstCheckBoxPrice;
    @FindBy(xpath = ".//a[@data-name='pri']")
    private WebElement yourBudgetFirstCheckBox;
    @FindBy(xpath = ".//a[@class='hotel_name_link url']")
    private List<WebElement> hotelURLList;
    @FindBy(xpath = ".//a[@class='hotel_name_link url']")
    private WebElement hotelURL;
    @FindBy(xpath = ".//div[contains(@aria-label,'Оценка')]")
    private List<WebElement> hotelRatings;
    @FindBy(id = "group_adults")
    private WebElement groupAdults;
    @FindBy(id = "no_rooms")
    private WebElement noRooms;
    @FindBy(xpath = ".//button[@data-sb-id]")
    private WebElement searchButtonSearchResultsPage;
    @FindBy(xpath = ".//a[@data-name='pri'][last()]/label/div/span[1]")
    private WebElement yourBudgetLastCheckBoxTopPrice;
    @FindBy(xpath = ".//a[@data-name='pri'][last()]")
    private WebElement yourBudgetLastCheckBox;
    @FindBy(xpath = ".//a[contains(text(),'Цена (сначала самая низкая)')]")
    private WebElement filterPriceAscendButton;
    @FindBy(xpath = ".//span[contains(text(),'Цена')]")
    private WebElement hotelPrice;
    @FindBy(xpath = ".//span[contains(text(),'Забронировать эт')]")
    private WebElement reserveButton;
    @FindBy(xpath = ".//button[contains(text(),'бронирую')]")
    private WebElement iWillReserveButton;
    @FindBy(xpath = ".//input[@id='lastname']")
    private WebElement lastNameField;
    @FindBy(xpath = ".//input[@id='email']")
    private WebElement emailField;
    @FindBy(xpath = ".//input[@id='email_confirm']")
    private WebElement emailConfirmField;
    @FindBy(xpath = ".//button[@name='book']")
    private WebElement nextFinalDetailsButton;
    @FindBy(xpath = ".//input[@id='phone']")
    private WebElement phoneField;
    @FindBy(xpath = ".//input[@id='pay-now']")
    private WebElement payNowRadioButton;
    @FindBy(id = "cc_type")
    private WebElement creditCardTypeSelect;
    @FindBy(xpath = ".//input[@id='cc_number']")
    private WebElement creditCardNumberField;
    @FindBy(xpath = ".//input[@id='cc_cvc']")
    private WebElement creditCardCVCNumberField;
    @FindBy(xpath = ".//p[contains(text(),'Введите номер действительной кредитной карты')]")
    private WebElement alertInvalidCardNumber;
    private String pathFromRatingToPrice =
            "./ancestor::div[contains(@class,'sr_item_content_slider_wrapper')]//span[contains(text(),'Цена')]";

    public Booking(WebDriver webdriver) {
        PageFactory.initElements(webdriver, this);
    }

    public Booking() {
    }

    public String homePageSearchCredentials(String place, int startAfterDays, int stayNights) {
        LocalDate checkInDate = LocalDate.now().plusDays(startAfterDays);
        LocalDate checkOutDate = checkInDate.plusDays(stayNights);
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.booking.com/");
        driver.findElement(By.xpath(".//input[@type='search']")).sendKeys(place);
        driver.findElement(By.xpath(".//div[@data-mode='checkin']")).click();
        driver.findElement(By.xpath(".//td[@data-date='".concat(checkInDate.toString()).concat("']"))).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//div[@data-mode='checkout']")).click();
        driver.findElement(By.xpath(".//td[@data-date='".concat(checkOutDate.toString()).concat("']"))).click();
        driver.findElement(By.xpath(".//button[@data-sb-id='main']")).click();
        String currentURL = driver.getCurrentUrl();
        driver.quit();
        return currentURL;
    }

    public void selectLowestBudgetPriceHotels() {
        yourBudgetFirstCheckBox.click();
    }

    public boolean checkAvailability() {
        return hotelURLList.size() > 0;
    }

    public boolean compareTopRatingCheapestHotelPriceWithFilterRange(int stayNights) throws InterruptedException {
        Thread.sleep(3000);
        int totalPrice;
        int pricePerNight = Integer.parseInt(yourBudgetFirstCheckBoxPrice.getText()
                .replaceAll("\\D", ""));
        Thread.sleep(8000);
        WebElement rating = Collections.max(hotelRatings, Comparator.comparing(WebElement::getText));
        System.out.println(rating.getText());
        try {
            totalPrice = Integer.parseInt(rating.findElement(By.xpath(pathFromRatingToPrice)).getText()
                    .replaceAll("\\D", ""));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            totalPrice = 0;
            System.out.println("This hotel has not price");
        }
        System.out.println(pricePerNight + " " + totalPrice + " " + stayNights);
        return pricePerNight >= totalPrice / stayNights;
    }

    public void selectAdultsRooms(String adults, String rooms) {
        new Select(groupAdults).selectByValue(adults);
        new Select(noRooms).selectByValue(rooms);
        searchButtonSearchResultsPage.click();
    }

    public void filterCheapestOfExpensiveHotels() {
        yourBudgetLastCheckBox.click();
        filterPriceAscendButton.click();
    }

    public boolean cheapestPriceIsNotLowerFilterRange(int stayNights) throws InterruptedException {
        int pricePerNight = Integer.parseInt(yourBudgetLastCheckBoxTopPrice.getText()
                .replaceAll("\\D", ""));
        Thread.sleep(5000);
        int totalPrice = Integer.parseInt(hotelPrice.getText().replaceAll("\\D", ""));
        return pricePerNight <= totalPrice / stayNights;
    }

    public String getHotelURL() throws InterruptedException {
        Thread.sleep(3500);
        return hotelURL.getAttribute("href");
    }

    public boolean tryReserve(String lastName, String email, String phone, String ccType, String ccNumber, String ccCvc)
            throws InterruptedException {
        reserveButton.click();
        iWillReserveButton.click();
        lastNameField.sendKeys(lastName);
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        nextFinalDetailsButton.click();
        phoneField.sendKeys(phone);
        new Select(creditCardTypeSelect).selectByValue(ccType);
        creditCardNumberField.sendKeys(ccNumber);
        creditCardCVCNumberField.sendKeys(ccCvc);
        Thread.sleep(2000);
        return alertInvalidCardNumber.isDisplayed();
    }
}


