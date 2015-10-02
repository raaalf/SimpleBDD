package com.malski.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;

/**
 * Class which is representing displayed page and allow to performing basic actions on it
 */
public class WebPage {
    protected final WebBrowser webBrowser;

    public WebPage(WebBrowser webBrowser) {
        this.webBrowser = webBrowser;
        this.initWebElements();
    }

    public WebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    public FluentWait<WebDriver> getWait() {
        return getWebBrowser().getWait();
    }

    public WebElement getElement(By by) {
        return getWebBrowser().findElement(by);
    }

    public WebElement getElementById(String id) {
        return this.getElement(By.id(id));
    }

    public WebElement getElementByXpath(String xpath) {
        return this.getElement(By.xpath(xpath));
    }

    public List<WebElement> getElements(By by) {
        return getWebBrowser().findElements(by);
    }

    public List<WebElement> getElementsByXpath(String xpath) {
        return this.getElements(By.xpath(xpath));
    }

    private void initWebElements() {
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webBrowser.getWebDriver(), 30);
        PageFactory.initElements(factory, this);
    }
}
