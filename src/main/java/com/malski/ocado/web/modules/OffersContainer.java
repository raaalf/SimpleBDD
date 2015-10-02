package com.malski.ocado.web.modules;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import com.malski.core.WebUtils;
import com.malski.ocado.containers.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OffersContainer extends WebPage {
    public OffersContainer(WebBrowser webBrowser, WebElement container) {
        super(webBrowser);
        this.container = container;
    }

    private WebElement container;

    public List<ShopItem> getPromoItemsAnyXxForXxIds() {
        return getPromoItemsIds(ShopItemPromoAnyXxForXx.PROMO_MATCHER, ShopItemPromoAnyXxForXx::new);
    }

    public List<ShopItem> getPromoItemsHalfPriceIds() {
        return getPromoItemsIds(ShopItemPromoHalfPrice.PROMO_MATCHER, ShopItemPromoHalfPrice::new);
    }

    public List<ShopItem> getPromoItemsAnyXxAddXxFreeIds() {
        return getPromoItemsIds(ShopItemPromoAnyXxAddXxFree.PROMO_MATCHER, ShopItemPromoAnyXxAddXxFree::new);
    }

    public List<ShopItem> getPromoItemsSaveXxWasXxIds() {
        return getPromoItemsIds(ShopItemPromoSaveXxWasXx.PROMO_MATCHER, ShopItemPromoSaveXxWasXx::new);
    }

    public List<ShopItem> getPromoItemsNowXxWasXxIds() {
        return getPromoItemsIds(ShopItemPromoNowXxWasXx.PROMO_MATCHER, ShopItemPromoNowXxWasXx::new);
    }

    public List<ShopItem> getPromoItemsIds(String promoMatcher, Function<String,ShopItem> constructor) {
        return container.findElements(By.cssSelector(".productDetails")).stream()
                .filter(elem -> {
                    boolean hasPromo = elem.findElement(By.cssSelector(".shelfTop .onOffer")).getText().matches(promoMatcher);
                    boolean outOfStock = webBrowser.elementExists(By.cssSelector(String.format("#%s .oos", elem.getAttribute("id"))));
                    return hasPromo && !outOfStock;
                })
                .map(elem -> constructor.apply(elem.getAttribute("id")))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public WebElement getSpecificItem(String id) {
        return getElementById(id);
    }

    public void addSpecificItemToBasket(WebElement itemElement, String id) {
        itemElement.findElement(By.cssSelector(".addBtn input.productAdd")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(String.format("li#%s .productCount", id))));
    }

    public void addSpecificItemToBasket(WebElement itemElement, String id, int quantity) {
        WebUtils.fillInput(itemElement.findElement(By.cssSelector(".productQuantity > input.quantity")), String.valueOf(quantity));
        addSpecificItemToBasket(itemElement, id);
    }
}