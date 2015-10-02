package com.malski.ocado.glue;

import com.malski.core.WebBrowser;
import com.malski.ocado.containers.ShopItem;
import com.malski.ocado.validator.ShopValidator;
import com.malski.ocado.web.api.OcadoOffersPage;
import com.malski.ocado.web.modules.ShopNavigator;
import com.malski.ocado.web.pages.BrowseShopPage;
import com.malski.ocado.web.pages.OcadoMainPage;
import com.malski.ocado.web.pages.OffersPage;
import com.malski.ocado.web.pages.TrolleyPage;
import com.malski.ocado.web.popups.LoginPopup;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OcadoStepDefinitions {
    private ShopValidator shopValidator;
    private WebBrowser browser;
    private String url = "https://www.ocado.com";
    private OcadoMainPage mainPage;
    private OffersPage offersPage;
    private BrowseShopPage browseShopPage;
    private List<ShopItem> promoItems;
    private BigDecimal totalPrice = new BigDecimal("0.00");
    private BigDecimal totalSavings = new BigDecimal("0.00");
    private int totalItemsCount = 0;

    @Before
    public void beforeScenario() {
        this.browser = new WebBrowser(System.getProperty("TEST_BROWSER"), url);
        mainPage = new OcadoMainPage(new ShopNavigator(this.browser), this.browser);
        mainPage.waitToLoad();
        shopValidator = new ShopValidator();
    }

    // steps for scenario 1

    @Given("^a customer on Browse Shop page$") // this one is also for scenario 3
    public void a_customer_on_Browse_Shop_page() {
        browseShopPage = mainPage.getNavigator().goToBrowseShop();
        shopValidator.checkBasketSummarySavings(totalSavings, browseShopPage.getNavigator());
    }

    @When("^'Buy any XX for XX' promo is available$")
    public void Buy_any_XX_for_XX_promo_is_available() {
        promoItems = browseShopPage.getOffersContainer().getPromoItemsAnyXxForXxIds();
        shopValidator.checkIfThereAreItemsInPromo(promoItems);
    }

    @And("^promotion conditions are met$")
    public void promotion_conditions_are_met() {
        promoItems.forEach(item -> {
            updateItem(item, browseShopPage);
            totalPrice = totalPrice.add(item.getPromoPrice());
        });
    }

    @Then("^total amount in basket is calculated correct$")
    public void total_amount_in_basket_is_calculated_correct() {
        shopValidator.checkBasketSummaryTotal(totalPrice, browseShopPage.getNavigator());
    }

    // steps for scenario 2

    @Given("^a logged in customer$")
    public void a_logged_in_customer() {
        LoginPopup loginPopup = mainPage.getNavigator().openLoginPopup();
        loginPopup.login("rafalmalski@gmail.com", "Test_123");

    }

    @When("^Offers page is displayed$")
    public void Offers_page_is_displayed() {
        offersPage = mainPage.getNavigator().goToOffers();
        // for full checkout
        promoItems = offersPage.getOffersContainer().getPromoItemsAnyXxForXxIds();
        promoItems.addAll(offersPage.getOffersContainer().getPromoItemsHalfPriceIds());
        promoItems.addAll(offersPage.getOffersContainer().getPromoItemsAnyXxAddXxFreeIds());
        promoItems.addAll(offersPage.getOffersContainer().getPromoItemsSaveXxWasXxIds());
        promoItems.addAll(offersPage.getOffersContainer().getPromoItemsNowXxWasXxIds());
        // for quick checkout
        /*promoItems = new ArrayList<>();
        promoItems.add(offersPage.getOffersContainer().getPromoItemsAnyXxForXxIds().get(0));
        promoItems.add(offersPage.getOffersContainer().getPromoItemsHalfPriceIds().get(0));
        promoItems.add(offersPage.getOffersContainer().getPromoItemsAnyXxAddXxFreeIds().get(0));
        promoItems.add(offersPage.getOffersContainer().getPromoItemsSaveXxWasXxIds().get(0));
        promoItems.add(offersPage.getOffersContainer().getPromoItemsNowXxWasXxIds().get(0));*/
        shopValidator.checkIfThereAreItemsInPromo(promoItems);
    }

    @Then("^add items met promo conditions to trolley$")
    public void add_items_met_promo_conditions_to_trolley() {
        // shopValidator.checkBasketSummaryCount(totalItemsCount, shopNavigator);
        promoItems.forEach(item -> {
            updateItem(item, offersPage);
            totalItemsCount += item.getQuantity();
        });
        shopValidator.checkBasketSummaryCount(totalItemsCount, offersPage.getNavigator());

        TrolleyPage trolleyPage = offersPage.getNavigator().viewTrolley();
        shopValidator.checkBasketSummaryCountInTrolley(promoItems, trolleyPage);
    }

    // steps for scenario 3

    @When("^customer add item in Half price promo to basket$")
    public void customer_add_item_in_Half_price_promo_to_basket() {
        promoItems = browseShopPage.getOffersContainer().getPromoItemsHalfPriceIds();
        shopValidator.checkIfThereAreItemsInPromo(promoItems);

        Random random = new Random();
        ShopItem item = promoItems.get(random.nextInt(promoItems.size()));
        updateItem(item, browseShopPage);
        totalSavings = totalSavings.add(item.getTotalPromoSavings());
    }

    @Then("^saving field is updated$")
    public void saving_field_is_updated() {
        shopValidator.checkIfBasketSummarySavingsWasUpdated(browseShopPage.getNavigator());
    }

    @And("^contains correct savings value$")
    public void contains_correct_savings_value() {
        shopValidator.checkBasketSummarySavings(totalSavings, browseShopPage.getNavigator());
    }

    @After
    public void afterScenario() {
        if(mainPage.getNavigator().isLoggedIn()) {
            TrolleyPage trolleyPage = mainPage.getNavigator().viewTrolley();
            trolleyPage.emptyTrolley();
        }
        browser.quit();
    }

    private void updateItem(ShopItem item, OcadoOffersPage ocadoOffersPage) {
        WebElement itemElem = ocadoOffersPage.getOffersContainer().getSpecificItem(item.getId());
        browser.scrollIntoView(itemElem);
        item.collectItemData(itemElem);
        ocadoOffersPage.getOffersContainer().addSpecificItemToBasket(itemElem, item.getId(), item.getItemsInPromotion());
        itemElem = ocadoOffersPage.getOffersContainer().getSpecificItem(item.getId());
        item.setItemQuantity(itemElem);
    }

}
