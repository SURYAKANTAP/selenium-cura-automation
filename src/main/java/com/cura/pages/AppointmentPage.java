package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import com.cura.utils.WaitUtils;

public class AppointmentPage {

    private final WaitUtils wait;

    // ---------- Locators (single source of truth) ----------
    private final By facilityDropdown   = By.id("combo_facility");
    private final By readmissionCheckbox = By.id("chk_hospotal_readmission");

    private final By medicareRadio  = By.id("radio_program_medicare");
    private final By medicaidRadio  = By.id("radio_program_medicaid");
    private final By noneRadio      = By.id("radio_program_none");

    private final By visitDateInput = By.id("txt_visit_date");
    private final By commentBox     = By.id("txt_comment");

    private final By bookAppointmentBtn = By.id("btn-book-appointment");

    public AppointmentPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    // ---------- Page readiness ----------
    public AppointmentPage waitForPageToLoad() {
        wait.visible(facilityDropdown);
        return this;
    }

    // ---------- User actions ----------
    public void selectFacility(String facilityName) {
        Select select = new Select(wait.visible(facilityDropdown));
        select.selectByVisibleText(facilityName);
    }

    public void applyForReadmission() {
        if (!wait.visible(readmissionCheckbox).isSelected()) {
            wait.clickable(readmissionCheckbox).click();
        }
    }

    public void selectHealthcareProgram(String program) {
        switch (program.toLowerCase()) {
            case "medicare"  -> wait.clickable(medicareRadio).click();
            case "medicaid"  -> wait.clickable(medicaidRadio).click();
            default          -> wait.clickable(noneRadio).click();
        }
    }

    public void enterVisitDate(String date) {
        var dateField = wait.visible(visitDateInput);
        dateField.clear();
        dateField.sendKeys(date);
    }

    public void enterComment(String comment) {
        var commentField = wait.visible(commentBox);
        commentField.clear();
        commentField.sendKeys(comment);
    }

    public void bookAppointment() {
        wait.clickable(bookAppointmentBtn).click();
    }
}
