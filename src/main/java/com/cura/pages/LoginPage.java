package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.cura.utils.WaitUtils;

public class LoginPage {

    private final WaitUtils wait;

    // Single source of truth for locators
    private final By usernameField = By.id("txt-username");
    private final By passwordField = By.id("txt-password");
    private final By loginButton   = By.id("btn-login");

    public LoginPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    // Explicit page readiness check
    public LoginPage waitForPageToLoad() {
        wait.visible(usernameField);
        return this;
    }

    // User action
    public void login(String user, String pass) {
        wait.visible(usernameField).sendKeys(user);
        wait.visible(passwordField).sendKeys(pass);
        wait.clickable(loginButton).click();
    }
}
