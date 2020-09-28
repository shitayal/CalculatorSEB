package stepDefinitions;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages_sample.CalculatorPage;


public class CalculatorSteps {
    private static CalculatorPage calPage;


    public CalculatorSteps() {
        calPage = PageFactory.initElements(Hooks.driver, CalculatorPage.class);

    }
    public void enterUrl()  {

        Assert.assertTrue(calPage.enterUrl(), "Site loaded successfully");
    }


    public void arithmeticOperation(String num1, String num2, String operator)  {

        Assert.assertTrue(calPage.operation(num1, num2, operator), "Operation successful");
    }

    public void table(String divisor)  {

        Assert.assertTrue(calPage.table(divisor), "Table is shown successfully");
    }
    public void labelChange()  {

        Assert.assertTrue(calPage.labelChange(), "Changed label read successfully");
    }
}
