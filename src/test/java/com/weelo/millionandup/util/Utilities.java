package com.weelo.millionandup.util;

import org.openqa.selenium.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.lang.Thread.sleep;

public class Utilities {

    public static void waitOwn(int seconds){
        long time = seconds * 1000;
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void selectDateVideocall(WebDriver driver, String date) throws ParseException {
        boolean flag = false;

        String[] splitDate = date.split("-");
        String day = splitDate[0];
        int value = Integer.parseInt(day);
        day = String.valueOf(value);
        String month = splitDate[1];
        String year = splitDate[2];

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = format.parse(date);
        Date date2 = format.parse(timeStamp);

        if(date1.before(date2)){
            String[] splitDate1 = date.split("-");
            String[] splitDate2 = timeStamp.split("-");
            if(!splitDate1[2].equals(splitDate2[2])){
                flag = true;
            }
        }

        switch (month){
            case "01":
                month = "JANUARY";
                break;

            case "02":
                month = "FEBRUARY";
                break;

            case "03":
                month = "MARCH";
                break;

            case "04":
                month = "APRIL";
                break;

            case "05":
                month = "MAY";
                break;

            case "06":
                month = "JUNE";
                break;

            case "07":
                month = "JULY";
                break;

            case "08":
                month = "AUGUST";
                break;

            case "09":
                month = "SEPTEMBER";
                break;

            case "10":
                month = "OCTOBER";
                break;

            case "11":
                month = "NOVEMBER";
                break;

            case "12":
                month = "DECEMBER";
                break;
        }

        String dateSelected = month+" "+year;

        while(!dateSelected.equals(driver.findElement(By.xpath("//h2[@class='fc-toolbar-title']")).getText())){
            if(flag){
                driver.findElement(
                        By
                                .xpath("//button[@class='fc-prev-button fc-button fc-button-primary']"))
                        .click();
            }else{
                driver.findElement(
                        By
                                .xpath("//button[@class='fc-next-button fc-button fc-button-primary']"))
                        .click();
            }

            waitOwn(1);
        }

        List<WebElement> horizontalDay = driver.findElements(By.xpath("//table[@class='fc-scrollgrid-sync-table']//td"));
        for(int horizontal=0; horizontal<horizontalDay.size(); horizontal++){
            if(horizontal < 11 && horizontalDay.get(horizontal).getText().equals(day) && Integer.parseInt(day) > 10){
                continue;
            }else{
                if(horizontalDay.get(horizontal).getText().equals(day)){
                    horizontalDay.get(horizontal).click();
                    break;
                }
            }
        }
    }

    public static void selectTimeVideoCall(WebDriver driver, String time){
        String xpathBase = "//ul[@class='schedule-time-ul']/li";
        List<WebElement> horizontalTime = driver.findElements(By.xpath(xpathBase));
        for(int horizontal=0; horizontal<horizontalTime.size(); horizontal++){
            if(driver.findElement(By.xpath(xpathBase+"["+(horizontal+1)+"]/span[1]")).getText().equals(time)){
                driver.findElement(By.xpath(xpathBase+"["+(horizontal+1)+"]/span[1]")).click();
            }
        }
    }

    public static void moveScreen(WebDriver driver, String direction){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        switch (direction){
            case "up":
                js.executeScript("javascript:window.scrollTo(0,document.body.scrollHeight)");
                break;

            case "down":
                js.executeScript("javascript:window.scrollBy(0,document.body.scrollTop)");
                break;
        }
    }
}
