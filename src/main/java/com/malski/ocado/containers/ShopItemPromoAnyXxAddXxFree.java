package com.malski.ocado.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopItemPromoAnyXxAddXxFree extends ShopItem {
    public final static String PROMO_MATCHER = "^Buy any (\\d+) add (\\d+) free$";

    public ShopItemPromoAnyXxAddXxFree(String id) {
        super(id);
    }

    @Override
    public void collectItemData(WebElement item) {
        this.typicalPrice = getTypicalPrice(item);
        setPromoTerms(item);
    }

    private void setPromoTerms(WebElement item) {
        String conditionText = item.findElement(By.cssSelector(".shelfTop .onOffer")).getText();
        Pattern pattern = Pattern.compile(PROMO_MATCHER);
        Matcher matcher = pattern.matcher(conditionText);
        if (matcher.find()) {
            //lets check how many items I have to buy to get free ones
            itemsInPromotion = Integer.parseInt(matcher.group(1));
            promoPrice = typicalPrice.multiply(BigDecimal.valueOf(itemsInPromotion));
            //add these free ones to total number of ordered items
            itemsInPromotion += Integer.parseInt(matcher.group(2));
        }
    }
}
