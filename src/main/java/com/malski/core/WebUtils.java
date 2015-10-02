package com.malski.core;

import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

public class WebUtils {

    public static void fillInput(WebElement input, String value) {
        input.clear();
        input.sendKeys(value);
    }

    public static BigDecimal parsePrice2BigDecimal(String price) {
        price = price.replaceAll("£", "").replaceAll(",", "");
        return new BigDecimal(price);
    }
}
