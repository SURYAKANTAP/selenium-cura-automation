package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.cura.utils.WaitUtils;

public class ConfirmationPage {

    private final WaitUtils wait;

    // Single source of truth for locator
    private final By confirmationHeader = By.tagName("h2");

    public ConfirmationPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    // Page readiness check
    public ConfirmationPage waitForPageToLoad() {
        wait.visible(confirmationHeader);
        return this;
    }

    // Data retrieval (no direct element access)
    public String getHeaderText() {
        return wait.visible(confirmationHeader).getText();
    }
}
