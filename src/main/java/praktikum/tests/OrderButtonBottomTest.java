package praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.pages.MainPage;
import praktikum.pages.OrderPage;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class OrderButtonBottomTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String rentPeriod;
    private final String color;
    private final String comment;

    public OrderButtonBottomTest(String name, String surname, String address, String phone, String rentPeriod, String color, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.rentPeriod = rentPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "Пушкина, 37", "+79000000000", "сутки", "black", "привет"},
                {"Петр", "Петров", "Кузнецкий Мост, 1", "89000000111", "двое суток", "grey", "Привки"}, //второй набор данных пока уберу
        });
    }
    @Before
    public void setUp() {
        //Раскомментируй если работаешь с Firefox
        //WebDriverManager.firefoxdriver().setup();
        //driver = new FirefoxDriver();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //ожидание
        mainPage = new MainPage();

        orderPage = new OrderPage();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        closeCookies();
    }
    // Тест на создание заказа
    @Test
    public void createOrderTestBottom() {
// Прокрутка до кнопки заказа внизу страницы и клик по ней
        scrollToAndClick(mainPage.orderButtonBottom);
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.elementToBeClickable(mainPage.orderButtonBottom));
        element.click();
        fillOrderForm();
        checkOrderCreation();
    }

    private void scrollToAndClick(By by) {
        // Находим элемент по локатору
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(70)).until(
                ExpectedConditions.presenceOfElementLocated(by));

        // Прокручиваем страницу до элемента
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        // Ожидаем, пока элемент станет кликабельным
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(element));

        // Кликаем по элементу
        element.click();
    }
    private void fillOrderForm() {
        driver.findElement(orderPage.nameInput).sendKeys(name);
        driver.findElement(orderPage.surnameInput).sendKeys(surname);
        driver.findElement(orderPage.addressInput).sendKeys(address);
        driver.findElement(orderPage.metroStationInput).click();
        driver.findElement(orderPage.metroStationIndex).click();
        driver.findElement(orderPage.phoneInput).sendKeys(phone);
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(orderPage.nextButton));
        driver.findElement(orderPage.nextButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(orderPage.dateInput));
        driver.findElement(orderPage.dateInput).click();
        driver.findElement(orderPage.datePick).click();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(orderPage.rentPeriodInput));
        driver.findElement(orderPage.rentPeriodInput).click();
        driver.findElement(By.xpath("//div[contains(text(), '" + rentPeriod + "')]")).click();
        if (color.equals("black")) {
            driver.findElement(orderPage.scooterColorBlack).click();
        } else {
            driver.findElement(orderPage.scooterColorGrey).click();
        }
        driver.findElement(orderPage.commentInput).sendKeys(comment);
        driver.findElement(orderPage.orderButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(orderPage.yesButton)); //ожидание для кнопки йес
        driver.findElement(orderPage.yesButton).click();
    }

    private void closeCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='да все привыкли']")));
            cookieButton.click();
        } catch (TimeoutException e) {
            System.out.println("Cookie consent banner not found, proceeding with test.");
        }
    }
    private void checkOrderCreation() {
        // Ожидание появления элемента, подтверждающего создание заказа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement orderConfirmationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Номер заказа:')]")));

            // Проверяем, что элемент найден
            Assert.assertNotNull("Элемент подтверждения отсутствует, заказ не был создан.", orderConfirmationElement);

            // Дополнительно:информация о номере заказа
            String confirmationText = orderConfirmationElement.getText();

            // Проверяем, содержит ли текст необходимую информацию
            Assert.assertTrue("Текст подтверждения не соответствует ожиданиям.", confirmationText.contains("Номер заказа:")
                    && confirmationText.contains("Запишите его:")
                    && confirmationText.contains("пригодится, чтобы отслеживать статус"));

        } catch (TimeoutException e) {
            Assert.fail("Таймаут истек: заказ не был создан или детали заказа не видны.");
        } catch (Exception e) {
            Assert.fail("Произошла ошибка при проверке создания заказа: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
