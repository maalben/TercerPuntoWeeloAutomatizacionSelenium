package com.weelo.millionandup.practice;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static com.weelo.millionandup.util.Utilities.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.ParseException;

public class Newdesign {

    WebDriverWait wait;
    public static WebDriver driver;
    final String videocallDate = "02-04-2022";
    final String videocallTime = "1 PM";
    final String email = "pedro@correo.com";
    final String name = "Pedro Pablo";
    final String phone = "3003121223";
    final String messageValidation = "Your Project presentation was scheduled successfully. One of our agents will contact you shortly to provide details.";

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().maximize();
        driver.get("https://newdesign.millionandup.com/");
    }

    @Test
    public void ValidateSuccessfulVideocallScheduling() throws ParseException {
        moveScreen(driver, "up");
        waitOwn(1);
        moveScreen(driver, "up");
        waitOwn(1);
        driver.findElement(By.xpath("//i[@class='icon btn-icon icon-arrow-forward btn-white__icon']")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//i[@class='icon-arrow-down']")));
        driver.findElement(By.xpath("//i[@class='icon-arrow-down']")).click();
        selectDateVideocall(driver,  videocallDate);
        selectTimeVideoCall(driver,  videocallTime);
        driver.findElement(By.xpath("//div[@class='type-schedule-section']//button[2]")).click();
        driver.findElement(By.xpath("//form[@id='frmScheduleLeadModal']/div[@class='required-inputs-lead lead__container-popup']/div[2]//input[1]")).sendKeys(email);
            driver.findElement(By.id("btnSendModal")).click();
        driver.findElement(By.xpath("//form[@id='frmScheduleLeadModal']/div[3]/div[3]/input")).sendKeys(name);
        driver.findElement(By.xpath("//form[@id='frmScheduleLeadModal']/div[3]/div[4]//input[@name='phone']")).sendKeys(phone);
        driver.findElement(By.id("btnSendModal")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//div[@class='d-flex schedule-response-text']")));
        assertEquals(messageValidation, driver.findElement(By.xpath("//div[@class='d-flex schedule-response-text']")).getText());
    }

    @Test
    public void ValidateErrorMessageSelectingDateBeforeCurrentScheduleVideocall() throws ParseException {
        String videocallDateBeforeCurrent = "02-12-2021";
        final String messageValidationTimeRequired = "Tour time is required";
        moveScreen(driver, "up");
        waitOwn(1);
        moveScreen(driver, "up");
        waitOwn(1);
        driver.findElement(By.xpath("//i[@class='icon btn-icon icon-arrow-forward btn-white__icon']")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//i[@class='icon-arrow-down']")));
        driver.findElement(By.xpath("//i[@class='icon-arrow-down']")).click();
        selectDateVideocall(driver,  videocallDateBeforeCurrent);
        driver.findElement(By.xpath("//div[@class='type-schedule-section']//button[2]")).click();
        driver.findElement(By.xpath("//form[@id='frmScheduleLeadModal']/div[@class='required-inputs-lead lead__container-popup']/div[2]//input[1]")).sendKeys(email);
        driver.findElement(By.id("btnSendModal")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//span[@class='validation-msg-schedule schedule__validation-footer-text']")));
        assertEquals(messageValidationTimeRequired, driver.findElement(By.xpath("//span[@class='validation-msg-schedule schedule__validation-footer-text']")).getText());
    }

    @Test
    public void validateErrorMessageEmailIsNotCompletedScheduleVideocall() throws ParseException {
        String videocallDate = "15-03-2022";
        final String videocallTime = "1 PM";
        final String messageValidationEmailRequired = "Register to enter site";
        moveScreen(driver, "up");
        waitOwn(2);
        moveScreen(driver, "up");
        waitOwn(2);
        driver.findElement(By.xpath("//i[@class='icon btn-icon icon-arrow-forward btn-white__icon']")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//i[@class='icon-arrow-down']")));
        driver.findElement(By.xpath("//i[@class='icon-arrow-down']")).click();
        selectDateVideocall(driver,  videocallDate);
        selectTimeVideoCall(driver,  videocallTime);
        driver.findElement(By.xpath("//div[@class='type-schedule-section']//button[2]")).click();
        driver.findElement(By.id("btnSendModal")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//span[@class='validation-msg lead__validation-footer-text']")));
        assertEquals(messageValidationEmailRequired, driver.findElement(By.xpath("//span[@class='validation-msg lead__validation-footer-text']")).getText());
    }

    @After
    public void close(){
        driver.quit();
    }
}