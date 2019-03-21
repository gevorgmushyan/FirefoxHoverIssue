package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class GoogleExampleIT extends DriverBase {

    public static List<String> partnerPages = List.of(
            "vetstreet", "adoptapet", "banfield", "homeagain", "aarp", "act"
    );

    public static void scrollElementIntoMiddle(WebDriver driver, WebElement element) {
        String script = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    @Test(dataProvider = "paths")
    public void buttonHoverTest(String path) throws Exception {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.get("http://gopetplan-static-dev.appspot.com/partners/" + path + "/standard/FAQ");

        Actions action = new Actions(driver);
        WebElement button = driver.findElement(By.cssSelector("div.page div.btn.is-hidden-mobile > a"));

        scrollElementIntoMiddle(driver, button);
        action.moveToElement(button).build().perform();

        Thread.sleep(500);
        String backgroundColour = button.getCssValue("background-color");
        assertTrue(
                backgroundColour.equals("rgb(252, 179, 22)")
                        || backgroundColour.equals("rgba(252, 179, 22, 1)")

        );
    }

    @Test(dataProvider = "paths")
    public void googleTest(String path) throws Exception {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/");

        Actions action = new Actions(driver);
        WebElement button = driver.findElement(By.cssSelector(" div > div.FPdoLc.VlcLAe > center > input[type=\"submit\"]:nth-child(1)"));

        scrollElementIntoMiddle(driver, button);
        action.moveToElement(button).build().perform();

        Thread.sleep(500);
        String backgroundColour = button.getCssValue("background-color");
        assertTrue(
                backgroundColour.equals("rgb(248, 248, 248)")
                        || backgroundColour.equals("rgba(248, 248, 248, 1)")

        );
    }

    @DataProvider
    public static Iterator<Object[]> paths() {
        return partnerPages.stream().map(e -> new Object[]{e}).iterator();
    }
}