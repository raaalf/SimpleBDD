package com.malski.ocado.web.pages;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import com.malski.ocado.web.api.OcadoPage;
import com.malski.ocado.web.modules.ShopNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OcadoMainPage extends WebPage implements OcadoPage {

    public OcadoMainPage(ShopNavigator navigator, WebBrowser webBrowser) {
        super(webBrowser);
        shopNavigator = navigator;
    }

    private ShopNavigator shopNavigator;

    private final String welcomeBoxCssSelector = ".welcomeBox";

    @FindBy(css = welcomeBoxCssSelector + " a[href*=\"getCategories\"]")
    private WebElement startShoppingLink;

    @Override
    public void waitToLoad() {
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(welcomeBoxCssSelector)));
    }

    @Override
    public ShopNavigator getNavigator() {
        return shopNavigator;
    }

    public BrowseShopPage startShopping() {
        startShoppingLink.click();
        BrowseShopPage browseShopPage = new BrowseShopPage(getNavigator(), webBrowser);
        browseShopPage.waitToLoad();
        return browseShopPage;
    }
}
