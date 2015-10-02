package com.malski.ocado.web.pages;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import com.malski.ocado.web.api.OcadoPage;
import com.malski.ocado.web.modules.ShopNavigator;
import com.malski.ocado.web.popups.EmptyTrolleyPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TrolleyPage extends WebPage implements OcadoPage {

    private ShopNavigator shopNavigator;

    @FindBy(css = "a.emptyBasket")
    private WebElement emptyBasketLink;

    public TrolleyPage(ShopNavigator navigator, WebBrowser webBrowser) {
        super(webBrowser);
        shopNavigator = navigator;
    }

    @Override
    public void waitToLoad() {
        String appLocationXpath = "//div[@id='content']//ul[@id='breadcrumb']//*[text()='Your trolley']";
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(appLocationXpath)));
    }

    @Override
    public ShopNavigator getNavigator() {
        return shopNavigator;
    }

    public int getItemCountInTrolley(String itemId) {
        return webBrowser.findElements(By.xpath(String.format("//li[contains(@id, '_%s')]", itemId.split("-")[1]))).size();
    }

    public void emptyTrolley() {
        if(emptyBasketLink.isDisplayed()) {
            emptyBasketLink.click();
            EmptyTrolleyPopup emptyTrolleyPopup = new EmptyTrolleyPopup(webBrowser);
            emptyTrolleyPopup.waitToLoad();
            emptyTrolleyPopup.confirm();
        }
    }
}
