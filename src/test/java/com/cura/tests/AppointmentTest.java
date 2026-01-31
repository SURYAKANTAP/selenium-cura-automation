package com.cura.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cura.base.BaseTest;
import com.cura.pages.*;
import com.cura.utils.ConfigReader;
import com.cura.utils.ExcelUtils;

public class AppointmentTest extends BaseTest {

	 @DataProvider(name = "appointmentData")
	    public Object[][] appointmentData() {

	        ExcelUtils excel = new ExcelUtils(
	                "src/test/resources/testdata/AppointmentData.xlsx",
	                "Sheet1"
	        );

	        int rows = excel.getRowCount();   // excludes header
	        int cols = 5;

	        Object[][] data = new Object[rows][cols];

	        for (int i = 1; i <= rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                data[i - 1][j] = excel.getCellData(i, j);
	            }
	        }

	        excel.close();
	        return data;
	    }

    @Test(dataProvider = "appointmentData")
    public void bookAppointmentTest(String facility,
                                    String readmission,
                                    String program,
                                    String visitDate,
                                    String comment) {

        HomePage homePage = new HomePage(driver).waitForPageToLoad();
        homePage.clickMakeAppointment();

        LoginPage loginPage = new LoginPage(driver).waitForPageToLoad();
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        AppointmentPage appointmentPage =
                new AppointmentPage(driver).waitForPageToLoad();

        appointmentPage.selectFacility(facility);

        if (readmission.equalsIgnoreCase("yes")) {
            appointmentPage.applyForReadmission();
        }

        appointmentPage.selectHealthcareProgram(program);
        appointmentPage.enterVisitDate(visitDate);
        appointmentPage.enterComment(comment);
        appointmentPage.bookAppointment();

        ConfirmationPage confirmationPage =
                new ConfirmationPage(driver).waitForPageToLoad();

        String header = confirmationPage.getHeaderText();

        Assert.assertEquals(
               header,
                "Make Appointment"
        );
        System.out.println("Appointment booked successfully. Confirmation message: "
                + header);
    }
}
