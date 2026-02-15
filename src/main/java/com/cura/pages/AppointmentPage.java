package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor; // Imported this
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.cura.utils.WaitUtils;

public class AppointmentPage {
	
	private final WebDriver driver; 
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
    	  this.driver = driver; 
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
        String formattedProgram = program.trim().toLowerCase();
        System.out.println("DEBUG: Selecting program '" + formattedProgram + "'");

        WebElement radioToClick;
        switch (formattedProgram) {
            case "medicare"  -> radioToClick = wait.clickable(medicareRadio);
            case "medicaid"  -> radioToClick = wait.clickable(medicaidRadio);
            case "none"      -> radioToClick = wait.clickable(noneRadio);
            default -> throw new RuntimeException("Program '" + program + "' is not supported!");
        }
        
        // Use JS Click for radios too, just to be safe against label overlap
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioToClick);
    }

  
  
    
    public void enterVisitDate(String date) {
        WebElement dateField = wait.visible(visitDateInput);
        
        System.out.println("DEBUG: Setting Date via JS: " + date.trim());
        
        // FIX 1: Set date directly using JavaScript. 
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", dateField, date.trim());
    }

    public void enterComment(String comment) {
    	WebElement commentField = wait.visible(commentBox);
        commentField.click(); // Ensure focus is here
        commentField.clear();
        commentField.sendKeys(comment);
    }

    public void bookAppointment() {
    	 WebElement btn = wait.clickable(bookAppointmentBtn);
         
         // CRITICAL FIX: Use JavascriptExecutor
         // This clicks the button directly in the DOM, ignoring any overlapping date pickers.
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("arguments[0].click();", btn);

    }
}
