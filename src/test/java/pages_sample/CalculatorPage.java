package pages_sample;



import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Reporter;

import java.text.DecimalFormat;


public class CalculatorPage extends BaseClass {

    @FindBy(how = How.ID, using = "logo")
    private WebElement calLogo;
    @FindBy(how = How.ID, using = "sciOutPut")
    private WebElement output;
    @FindBy(how = How.XPATH, using = "//span[text()='AC']")
    private WebElement clear;
    @FindBy(how = How.XPATH, using = "//span[contains(@onclick,'MR')]")
    private WebElement MRLabel;
    @FindBy(how = How.XPATH, using = "(//span[contains(@onclick,'*')])[1]")
    private WebElement multiply;
    @FindBy(how = How.XPATH, using = "(//span[contains(@onclick,'-')])[1]")
    private WebElement subtraction;

    public boolean enterUrl() {
        driver.get(getProperty("Url"));
        if (isElementVisible(calLogo)){
            Reporter.log("Landed on calculator site successfully",true);
            return true;}
        else{
            Reporter.log("Unable to Land on calculator site",true);
            return false;}

    }

    public boolean labelChange() {
        if (MRLabel.getText().equals("MR")) {
            click(MRLabel);
            String lbl = MRLabel.getText();
            boolean flag = lbl.equals("MC");
            if (flag){
                Reporter.log("MR is clicked and MC is visble",true);
                return true;}
            else{
                Reporter.log("MR is not clicked and MC is not visble",true);
                return false;
            }
        }
        Reporter.log("MR button is not visible",true);
        return false;
    }

    public boolean table(String divisor) {

        boolean flag = true;
        int div =Integer.parseInt(divisor);
        String [] arrNum = divisor.split("");
        for (int i = 1; i <= 10; i++) {
            click(clear);
            for (String num :arrNum) {
                click(driver.findElement(By.xpath("//span[text()='"+num+"']")));
            }
            click(multiply);
            if (i==10){
                click(driver.findElement(By.xpath("//span[text()='1']")));
                click(driver.findElement(By.xpath("//span[text()='0']")));
            }
            else{
                click(driver.findElement(By.xpath("//span[text()='"+i+"']")));
            }
            String result = String.valueOf(div * i);
            if (!result.equals(output.getText().trim())){
                Reporter.log("Table result is incorrect",true);
                flag = false;
                break;
            }

        }
        if (flag){
            Reporter.log("Table result is correct",true);
        }
        return  flag;
    }

    public boolean operation(String num1, String num2, String operator)  {

        String [] arrNum1 = num1.split("");
        String [] arrNum2 = num2.split("");
        for (String num :arrNum1) {
            if (num.equals("-")){
                click(subtraction);
            }
            else if (num.equals("*")){
                click(multiply);
            }
            else{
                click(driver.findElement(By.xpath("//span[text()='"+num+"']")));
            }
        }
        click(driver.findElement(By.xpath("(//span[contains(@onclick , '"+operator+"')and (@class = 'sciop')])[1]")));
        for (String num :arrNum2) {
            if (num.equals("-")){
                click(subtraction);
            }
            else if (num.equals("*")){
                click(multiply);
            }
            else{
                click(driver.findElement(By.xpath("//span[text()='"+num+"']")));
            }
        }

        double newNum1 = Double.parseDouble(num1);
        double newNum2 = Double.parseDouble(num2);
        double result=0d;
        switch (operator) {
            case "+":
                result = newNum1 + newNum2;
                break;
            case "-":
                result = newNum1 - newNum2;
                break;
            case "*":
                result = newNum1 * newNum2;
                break;
            case "/":
                if (newNum2==0)
                    throw new ArithmeticException("Division by zero is not allowed");
                else
                    result = newNum1 / newNum2;

                break;
        }
        DecimalFormat df = new DecimalFormat("###.##");
        String finalRes =df.format(result);
        String outputResult =df.format( Double.parseDouble(output.getText().trim()));
        if (finalRes.equals(outputResult)){
            Reporter.log("Arithmetic Oeration is successful",true);
            return true;}
        else{
            Reporter.log("Arithmetic Operation is not successful",true);
            return false;}

    }

}
