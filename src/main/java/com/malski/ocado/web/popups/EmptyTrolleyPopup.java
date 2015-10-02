package com.malski.ocado.web.popups;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EmptyTrolleyPopup extends WebPage {
    private final String yesButtonId = "empty-trolley-popup-btn";

    @FindBy(id = "empty-trolley-popup-btn")
    private WebElement yesButton;

    @FindBy(css = "a.button[href*='getPreviousPage']")
    private WebElement noButton;

    public EmptyTrolleyPopup(WebBrowser webBrowser) {
        super(webBrowser);
    }


    public void waitToLoad() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(yesButtonId)));
    }

    public void confirm() {
        yesButton.click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.id(yesButtonId)));
    }

    public void decline() {
        noButton.click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.id(yesButtonId)));
    }
}