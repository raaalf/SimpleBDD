package com.malski.core;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * WebBrowser class is my wrapper for WebDriver to execute basic actions using it
 */
public class WebBrowser {
    private WebDriver driver;
    private String baseUrl;
    public Wait<WebDriver> wait;

    private static final long TIMEOUT_SECONDS = 60;

    public FluentWait<WebDriver> getWait() {
        return getWait(TIMEOUT_SECONDS);
    }

    public FluentWait<WebDriver> getWait(long timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebBrowser(String browserType, String baseUrl) {
        this.driver = getWebDriver(browserType.toLowerCase());
        this.baseUrl = baseUrl;
        this.setDefaultWaitTimeout();
        get(baseUrl);
    }

    public void get(String url) {
        this.driver.get(url);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void close() {
        this.driver.close();
    }

    public void quit() {
        this.driver.quit();
    }

    public void setDefaultWaitTimeout() {
        this.wait = new WebDriverWait(this.driver, 30L);
    }

    public void setWaitTimeout(long milliseconds) {
        this.wait = new WebDriverWait(this.driver, milliseconds / 1000L);
    }

    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean elementExists(By locator) {
        try {
            driver.findElement(locator);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private WebDriver getWebDriver(String browser) {
        try {
            WebDriver driver;
            switch (browser) {
                case "chrome":
                    ChromeDriverManager.getInstance().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    driver = new HtmlUnitDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            return driver;
        } catch (IllegalArgumentException ex) {
            // do nothing
            return null;
        }
    }
}