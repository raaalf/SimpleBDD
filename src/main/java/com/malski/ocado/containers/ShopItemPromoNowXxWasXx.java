package com.malski.ocado.containers;

import com.malski.core.WebUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

public class ShopItemPromoNowXxWasXx extends ShopItem {
    public final static String PROMO_MATCHER = "^Now £?(\\d+(\\.\\d{2})?)p?, was £?(\\d+(\\.\\d{2})?)p?$";

    public ShopItemPromoNowXxWasXx(String id) {
        super(id);
    }

    @Override
    public void collectItemData(WebElement item) {
        this.typicalPrice = getTypicalPrice(item);
        //in this kind of promotion I have to buy one item to meet promo condition
        itemsInPromotion = 1;
        promoPrice = getPromoPrice(item);
    }

    @Override
    protected BigDecimal getTypicalPrice(WebElement item) {
        return WebUtils.parsePrice2BigDecimal(item.findElement(By.cssSelector(".typicalPrice .wasPrice")).getText());
    }

    private BigDecimal getPromoPrice(WebElement item) {
        return new BigDecimal(item.findElement(By.cssSelector(".typicalPrice meta[itemprop='price']")).getAttribute("content").replaceAll(",", "."));
    }
}
