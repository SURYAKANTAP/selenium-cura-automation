package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.cura.utils.WaitUtils;

public class HomePage {

    private final WaitUtils wait;

    // Single source of truth for locator
    private final By makeAppointmentBtn = By.id("btn-make-appointment");

    public HomePage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    // Page load validation (explicit and intentional)
    public HomePage waitForPageToLoad() {
        wait.visible(makeAppointmentBtn);
        return this;
    }

    // User action
    public void clickMakeAppointment() {
        wait.clickable(makeAppointmentBtn).click();
    }
}
