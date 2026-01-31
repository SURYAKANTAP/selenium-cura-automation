package com.cura.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private final Workbook workbook;
    private final Sheet sheet;

    public ExcelUtils(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath)) {

            this.workbook = new XSSFWorkbook(fis);
            this.sheet = workbook.getSheet(sheetName);

            if (this.sheet == null) {
                throw new IllegalArgumentException(
                    "Sheet '" + sheetName + "' not found in Excel file"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + filePath, e);
        }
    }

    public int getRowCount() {
        return sheet.getLastRowNum(); // excludes header row by default
    }

    public String getCellData(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Excel workbook", e);
        }
    }
}
