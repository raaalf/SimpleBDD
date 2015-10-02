package com.malski.ocado.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopItemPromoAnyXxForXx extends ShopItem {
    public final static String PROMO_MATCHER = "^Buy any (\\d+) for £?(\\d+(\\.\\d{2})?)p?$";

    public ShopItemPromoAnyXxForXx(String id) {
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
            //check how many items I have to buy to get promo
            itemsInPromotion = Integer.parseInt(matcher.group(1));
            //get total promo price
            promoPrice = new BigDecimal(matcher.group(2));
        }
    }
}
