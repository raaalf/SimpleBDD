package com.malski.ocado.validator;

import com.malski.ocado.containers.ShopItem;
import com.malski.ocado.web.modules.ShopNavigator;
import com.malski.ocado.web.pages.TrolleyPage;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ShopValidator {

    public void checkBasketSummaryTotal(BigDecimal countedBasketSummaryTotal, ShopNavigator navigator) {
        assertThat(countedBasketSummaryTotal, equalTo(navigator.getCurrentBasketSummaryTotal()));
    }

    public void checkBasketSummaryCount(int itemsTotalCount, ShopNavigator navigator) {
        assertThat(itemsTotalCount, equalTo(navigator.getCurrentBasketSummaryCount()));
    }

    public void checkIfBasketSummarySavingsWasUpdated(ShopNavigator navigator) {
        assertThat("£0.00", not(equalTo(navigator.getCurrentBasketSummarySavingsText())));
    }

    public void checkBasketSummarySavings(BigDecimal basketSummarySavings, ShopNavigator navigator) {
        assertThat(basketSummarySavings, equalTo(navigator.getCurrentBasketSummarySavings()));
    }

    public void checkIfThereAreItemsInPromo(List<ShopItem> items) {
        assertThat(items != null && items.size() > 0, is(true));
    }

    public void checkBasketSummaryCountInTrolley(List<ShopItem> items, TrolleyPage trolleyPage) {
        items.forEach( item -> {
            assertThat(item.getQuantity(), equalTo(trolleyPage.getItemCountInTrolley(item.getId())));
        });

    }
}
