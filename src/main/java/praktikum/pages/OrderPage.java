package praktikum.pages;

import org.openqa.selenium.By;

public class OrderPage {

    public OrderPage() {}

    public By nameInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/input");
    public By surnameInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/input");
    public By addressInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[3]/input");
    public By metroStationInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/div/div/input");
    public By metroStationIndex = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/div/div[2]/ul/li[1]");
    public By phoneInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[5]/input");
    public By nextButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button");
    public By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    public By datePick = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/div[2]/div[3]/div[6]"); //добавила для метода как с метро
    public By rentPeriodInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div[1]/div[1]");
    public By scooterColorBlack = By.xpath("//*[@id=\"black\"]");
    public By scooterColorGrey = By.xpath("//*[@id=\"grey\"]");
    public By commentInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/input");
    public By orderButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]");
    //добавляю кнопку йес
    public By yesButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div[2]/button[2]");

}