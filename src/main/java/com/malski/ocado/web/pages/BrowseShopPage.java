package com.malski.ocado.web.pages;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import com.malski.ocado.web.api.OcadoOffersPage;
import com.malski.ocado.web.api.OcadoPage;
import com.malski.ocado.web.modules.OffersContainer;
import com.malski.ocado.web.modules.ShopNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BrowseShopPage extends WebPage implements OcadoPage, OcadoOffersPage {

    @FindBy(id = "boxes")
    private WebElement itemsContainer;

    private OffersContainer offersContainer;

    private ShopNavigator shopNavigator;

    public BrowseShopPage(ShopNavigator navigator, WebBrowser webBrowser) {
        super(webBrowser);
        shopNavigator = navigator;
        offersContainer = new OffersContainer(webBrowser, itemsContainer);
    }

    @Override
    public void waitToLoad() {
        String appLocationXpath = "//div[@id='content']//ul[@id='breadcrumb']//*[text()='Shop']";
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(appLocationXpath)));
    }

    @Override
    public ShopNavigator getNavigator() {
        return shopNavigator;
    }

    @Override
    public OffersContainer getOffersContainer() {
        return offersContainer;
    }
}