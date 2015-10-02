package com.malski.ocado.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

public class ShopItem {
    protected String id;
    protected BigDecimal typicalPrice;
    protected int quantity;
    protected int itemsInPromotion;
    protected BigDecimal promoPrice;

    public ShopItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getTypicalPrice() {
        return typicalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getItemsInPromotion() {
        return itemsInPromotion;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void collectItemData(WebElement item) {
        typicalPrice = getTypicalPrice(item);
        itemsInPromotion = 1;
        promoPrice = typicalPrice;
    }

    public void setItemQuantity(WebElement item) {
        quantity = Integer.parseInt(item.findElement(By.cssSelector(".productCount")).getText().replaceAll(" in trolley", ""));
    }

    public BigDecimal getTotalPromoSavings() {
        return getTypicalPrice().multiply(new BigDecimal(getItemsInPromotion())).subtract(getPromoPrice());
    }

    protected BigDecimal getTypicalPrice(WebElement item) {
        return new BigDecimal(item.findElement(By.cssSelector(".typicalPrice meta[itemprop='price']")).getAttribute("content"));
    }
}
