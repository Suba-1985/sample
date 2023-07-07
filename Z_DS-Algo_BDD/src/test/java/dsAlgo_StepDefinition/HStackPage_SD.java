package dsAlgo_StepDefinition;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import dsAlgo_Driverfactory.DriverFactory;
import dsAlgo_PageObject.ALandingPage;
import dsAlgo_PageObject.BHomePage;
import dsAlgo_PageObject.DLoginPage;
import dsAlgo_PageObject.HStackPage;
import dsAlgo_Utilities.ExcelReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HStackPage_SD {
	private ALandingPage land=new ALandingPage(DriverFactory.getDriver());
	private BHomePage homepage=new BHomePage (DriverFactory.getDriver());
	private DLoginPage loginpage=new DLoginPage(DriverFactory.getDriver());
	private HStackPage stackPage=new HStackPage(DriverFactory.getDriver());
	private String code;
	private String actualresult;;
	private String expectedresult;
	WebDriver driver=DriverFactory.getDriver();
	

@Given("The user is on the {string} page after logged in")
public void the_user_is_on_the_page_after_logged_in(String string) {
	stackPage=homepage.stack_getstart();
}

@When("The user clicks {string} button in stack page")
public void the_user_clicks_button_in_stack_page(String option) {
	stackPage.stackmenu_click(option);
	}
@Given("The user is in a {string} stack page having an tryEditor with a Run button to test")
public void the_user_is_in_a_page_having_an_try_editor_with_a_run_button_to_test(String option) {
	stackPage=homepage.stack_getstart();
	stackPage.stackmenu_click(option);
	stackPage.tryedit_click();
}
@When("The user enter valid python code in stack tryEditor from sheet {string} and {int}")
public void the_user_enter_valid_python_code_in_stack_try_editor_from_sheet_and(String SheetName, Integer rowno) throws InvalidFormatException, IOException, InterruptedException{
	ExcelReader reader=new ExcelReader();	
	List<Map<String, String>> rdata = reader.getData("./src/test/resources/Excel_Register/programdata.xlsx",SheetName);
	code=rdata.get(rowno).get("Pythoncode");
	expectedresult=rdata.get(rowno).get("Result");
	 System.out.println("Code:"+code);
	stackPage.enter_code(code);
}
@And("The user clicks on stack run button")
public void the_user_clicks_on_run_button() throws InterruptedException {
	stackPage.click_run();
}

@And("The user clicks tryEditor with a Run button to test on stack page")
public void the_user_clicks_tryEditor_with_a_Run_button_to_test_on_stack_page() throws InterruptedException {
	stackPage.tryedit_click();
}

@Then("The user in {string} page")
public void the_user_in_page(String string) {
   Assert.assertTrue(driver.getCurrentUrl().contains(string));
}

@Then("The user should be presented with error message in stack page")
public void the_user_should_be_presented_with_error_message() {
		actualresult=stackPage.present_invalidmsg();
		Assert.assertEquals(actualresult, expectedresult);
}

@Then("The user should be presented with Run result of stack")
public void the_user_should_be_presented_with_run_result() {
	actualresult=stackPage.present_result();
	Assert.assertEquals(actualresult, expectedresult);
}

@When("The user enter python code with invalid syntax in stack tryEditor from sheet {string} and {int}")
public void the_user_enter_python_code_with_invalid_syntax_in_try_editor_from_sheet_and(String SheetName, Integer rowno) throws InvalidFormatException, IOException, InterruptedException{
	 ExcelReader reader=new ExcelReader();
	 List<Map<String, String>> rdata = reader.getData("./src/test/resources/Excel_Register/programdata.xlsx",SheetName);
	 code=rdata.get(rowno).get("Pythoncode");
	 expectedresult=rdata.get(rowno).get("Result");
	 stackPage.enter_code(code);   
}


}



	
	
