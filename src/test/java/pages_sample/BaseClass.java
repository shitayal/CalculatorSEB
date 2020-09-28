package pages_sample;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import stepDefinitions.Hooks;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    private WebDriverWait wait;


    public BaseClass() {
        driver = Hooks.driver;
        this.wait = Hooks.wait;

    }


    public void click(WebElement el) {
        try {
            if (el != null) {
                wait.until(ExpectedConditions.visibilityOf(el));
                wait.until(ExpectedConditions.elementToBeClickable(el));
                el.click();
                Assert.assertTrue(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


    public boolean isElementVisible(WebElement el) {
        try {
            boolean flag = false;
            if (el != null) {
                wait.until(ExpectedConditions.visibilityOf(el));
                if (el.isDisplayed()) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Object[][] getDataFromExcelFile(String fileName, String sheetName) {
        try {
            Object[][] excelData = null;
            String localDir = System.getProperty("user.dir");
            File file = new File(localDir + "\\src\\" + fileName + ".xls");
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet(sheetName);
            int lastRow = sheet.getLastRowNum();
            int lastCol = sheet.getRow(0).getLastCellNum();
            excelData = new Object[lastRow][lastCol];

            for (int i = 1; i <= lastRow; i++) {
                for (int j = 0; j < lastCol; j++) {
                    excelData[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }

            }
            return excelData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getProperty(String prop) {
        try {
            String localDir = System.getProperty("user.dir");
            File file = new File(localDir + "\\src\\Property.properties");
            FileInputStream fs = new FileInputStream(file);
            Properties p = new Properties();
            p.load(fs);
            String property = p.getProperty(prop);
            fs.close();
            return property;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
