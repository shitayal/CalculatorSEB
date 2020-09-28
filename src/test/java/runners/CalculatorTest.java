package runners;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages_sample.BaseClass;
import stepDefinitions.*;


public class CalculatorTest extends Hooks {


    @DataProvider(name = "getAllDataFromExcelSheet")
    public Object[] getAllDataFromExcelSheet() {
        BaseClass bc = new BaseClass();
        return bc.getDataFromExcelFile("Calculator", "Calculator");
    }


    @Test(dataProvider = "getAllDataFromExcelSheet")
    public void calculator(String Num1,String Num2, String Operator, String Divisor) {
        test = extent.createTest("Test Case 1: Calculator Functions");
        CalculatorSteps calsteps = new CalculatorSteps();
        calsteps.enterUrl();
        calsteps.labelChange();
        calsteps.arithmeticOperation(Num1, Num2, Operator);
        calsteps.table(Divisor);


    }
}
