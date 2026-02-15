package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.cura.utils.WaitUtils;

public class ConfirmationPage {

    private final WaitUtils wait;

    private final By confirmationHeader =
            By.cssSelector("section#summary h2");

    public ConfirmationPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    public ConfirmationPage waitForPageToLoad() {
        // THIS IS THE KEY FIX
        wait.urlContains("appointment.php#summary");
        wait.visible(confirmationHeader);
        return this;
    }

    public String getHeaderText() {
        return wait.visible(confirmationHeader).getText().trim();
    }
}
