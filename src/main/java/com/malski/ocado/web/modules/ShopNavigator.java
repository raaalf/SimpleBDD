package com.malski.ocado.web.modules;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import com.malski.core.WebUtils;
import com.malski.ocado.web.pages.BrowseShopPage;
import com.malski.ocado.web.pages.OffersPage;
import com.malski.ocado.web.pages.TrolleyPage;
import com.malski.ocado.web.popups.LoginPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;

public class ShopNavigator extends WebPage {

    public ShopNavigator(WebBrowser webBrowser) {
        super(webBrowser);
    }

    private final String loginButtonId = "loginButton";

    @FindBy(id = loginButtonId)
    private WebElement loginButton;

    @FindBy(css = "#browseShopContainer a.shopLink[href*='getCategories'")
    private WebElement browseShopLink;

    @FindBy(css = "#offers a[href*='getOffers']")
    private WebElement offersLink;

    @FindBy(css = ".viewTrolley.button")
    private WebElement viewTrolleyButton;

    @FindBy(id = "basketSummaryTotal")
    private WebElement basketSummaryTotal;

    @FindBy(id = "basketSummaryCount")
    private WebElement basketSummaryCount;

    @FindBy(id = "basketSummarySavings")
    private WebElement basketSummarySavings;

    public LoginPopup openLoginPopup() {
        loginButton.click();
        LoginPopup loginPopup = new LoginPopup(webBrowser);
        loginPopup.waitToLoad();
        return loginPopup;
    }

    public BrowseShopPage goToBrowseShop() {
        browseShopLink.click();
        BrowseShopPage browseShopPage = new BrowseShopPage(this, webBrowser);
        browseShopPage.waitToLoad();
        return browseShopPage;
    }

    public OffersPage goToOffers() {
        offersLink.click();
        OffersPage offersPage = new OffersPage(this, webBrowser);
        offersPage.waitToLoad();
        return offersPage;
    }

    public TrolleyPage viewTrolley() {
        viewTrolleyButton.click();
        TrolleyPage trolleyPage = new TrolleyPage(this, webBrowser);
        trolleyPage.waitToLoad();
        return trolleyPage;
    }

    public boolean isLoggedIn() {
        return webBrowser.elementExists(By.xpath("//li[@id='myShopNav']//a[contains(text(),'My Ocado')]"));
    }

    public BigDecimal getCurrentBasketSummaryTotal() {
        return WebUtils.parsePrice2BigDecimal(basketSummaryTotal.getText());
    }

    public int getCurrentBasketSummaryCount() {
        return Integer.parseInt(basketSummaryCount.getText());
    }

    public BigDecimal getCurrentBasketSummarySavings() {
        return WebUtils.parsePrice2BigDecimal(basketSummarySavings.getText());
    }

    public String getCurrentBasketSummarySavingsText() {
        return basketSummarySavings.getText();
    }
}
