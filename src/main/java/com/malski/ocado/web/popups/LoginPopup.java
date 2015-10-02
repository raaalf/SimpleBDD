package com.malski.ocado.web.popups;

import com.malski.core.WebBrowser;
import com.malski.core.WebPage;
import com.malski.core.WebUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPopup extends WebPage {

    public LoginPopup(WebBrowser webBrowser) {
        super(webBrowser);
    }

    private final String loginButtonId = "js-popupLoginButton";

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = loginButtonId)
    private WebElement loginButton;

    public void waitToLoad() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(loginButtonId)));
    }

    public void login(String username, String password) {
        WebUtils.fillInput(usernameInput, username);
        WebUtils.fillInput(passwordInput, password);
        loginButton.click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.id(loginButtonId)));
    }
}