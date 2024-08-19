package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FAQ {
    private final WebDriver driver;

    public FAQ(WebDriver driver) {
        this.driver = driver;
    }

    private final By questionsLocator = By.cssSelector(".accordion__heading");
    private final By answersLocator = By.cssSelector(".accordion__panel");

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    public void scrollToFAQ() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Ждем, пока один из вопросов о важном станет видимым
        WebElement faqSection = wait.until(ExpectedConditions.visibilityOfElementLocated(questionsLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqSection);
    }

    public List<WebElement> getQuestions() {
        return driver.findElements(questionsLocator);
    }

    public WebElement getAnswer(int index) {
        return driver.findElements(answersLocator).get(index);
    }

    public void clickQuestion(int index) {
        List<WebElement> questions = getQuestions();
        questions.get(index).click();
    }

    public boolean isAnswerVisible(int index) {
        return getAnswer(index).isDisplayed();
    }

    public String getQuestionText(int index) {
        return getQuestions().get(index).getText();
    }

    public String getAnswerText(int index) {
        return getAnswer(index).getText();
    }
}