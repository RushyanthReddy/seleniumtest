package seleniumcode;

import static org.junit.Assert.assertTrue;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class Test {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions act;

	private static final String firefoxDriverPath = "/src/test/resources/geckodriver";

	private void clickOnCreateApp() {
		driver.findElement(By.id("link-create")).click();
	}

	private void clickOnCreatePage() {
		driver.findElement(By
				.xpath("//div[@aria-labelledby=\"ui-dialog-title-create-dialog\"]//button[contains(text(),'Create')]"))
				.click();
	}

	private void enterNewName() {
		driver.findElement(By.xpath("//input[@name='name' and @class = 'indented submitonenter']")).sendKeys("test");

	}

	private void clickOnAddNewPage() {
		driver.findElement(By.id("add-page")).click();

	}

	private void checkForTab(String tabname) {
		WebElement tab = driver.findElement(By.xpath("//a[contains(text(),'" + tabname + "')]"));
		assertTrue(tab.isDisplayed());
	}

	private void checkForStartNode() {
		Assert.assertTrue(driver.findElement(By.id("module-1")).isDisplayed());

	}

	private void clickOnMessagingMenu() {
		driver.findElement(By.xpath("//a[contains(text(),'Messaging')]")).click();

	}

	private void dragAndDrop(WebElement start, WebElement end, int node_x, int node_y) {
		act.moveToElement(start).pause(Duration.ofSeconds(1)).clickAndHold(start).pause(Duration.ofSeconds(1))
				.moveByOffset(1, 0).moveToElement(end, node_x, node_y).pause(Duration.ofSeconds(1)).release().perform();
	}

	private Point getLocationOfNode(WebElement start_node) {
		Point node = start_node.getLocation();
		return node;
	}

	private void dragAndDrop(WebElement start, int i, int j) {
		act.moveToElement(start).pause(Duration.ofSeconds(1)).clickAndHold(start).pause(Duration.ofSeconds(1))
				.moveByOffset(0, 1).moveToElement(start, i, j).pause(Duration.ofSeconds(1)).release().perform();
	}

	private int calculateOffsetY(WebElement start_node, WebElement exit_upper_node_1) {
		Point start_node_loc = getLocationOfNode(start_node);
		Point sms_upper_node_loc = getLocationOfNode(exit_upper_node_1);

		int start_node_y = start_node_loc.getY();
		int sms_upper_node_y = sms_upper_node_loc.getY();

		return sms_upper_node_y - start_node_y;

	}

	private int calculateOffsetX(WebElement start_node, WebElement exit_upper_node_1) {
		Point start_node_loc = getLocationOfNode(start_node);
		Point sms_upper_node_loc = getLocationOfNode(exit_upper_node_1);

		int start_node_x = start_node_loc.getX();
		int sms_upper_node_x = sms_upper_node_loc.getX();

		return sms_upper_node_x - start_node_x;
	}

	private void clickOnBasicMenu() {
		driver.findElement(By.xpath("//a[contains(text(),'Basic')]")).click();

	}

	private void fillTheSmsDetails() {
		driver.findElement(By.xpath("//*[@name='phone_constant']")).sendKeys("8861030744");
		driver.findElement(By.xpath(
				"//*[@id='module-2']//*[@name='message_phrase[]' and @class='syn-autoexpand syn-constant syn-autogrow']"))
				.sendKeys("Hi!!!");
	}

	private void filTheMailDetails() {
		driver.findElement(By.name("smtp_url")).sendKeys("abc");
		driver.findElement(By.name("port")).sendKeys("5050");
		driver.findElement(By.name("username")).sendKeys("rushi");
		driver.findElement(By.name("password")).sendKeys("12345678");
		driver.findElement(By.name("from_constant")).sendKeys("abc@gmail.com");
		driver.findElement(By.name("to_constant")).sendKeys("def@gmail.com");
		driver.findElement(By.name("subject_constant")).sendKeys("hi hi hi");

		driver.findElement(By.xpath(
				"//*[@id='module-3']//*[@name='message_phrase[]' and @class='syn-autoexpand syn-constant syn-autogrow']"))
				.sendKeys("Hello World!!");
	}

	// Step definitions for Scenario 1

	@Given("^Go to quickfuse$")
	public void go_to_quickfuse() throws Throwable {
		String rootPath = System.getProperty("user.dir");
		System.setProperty("webdriver.gecko.driver", rootPath + firefoxDriverPath);
		driver = new FirefoxDriver();
		driver.get("http://quickfuseapps.com");
		act = new Actions(driver);

	}

	@Given("^Create a new app$")
	public void create_a_new_app() throws Throwable {
		clickOnCreateApp();
	}

	@When("^We get started$")
	public void we_get_started() throws Throwable {
		wait = new WebDriverWait(driver, 10);
		WebElement get_started_button = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='intro-dialog-cont']//button")));
		get_started_button.click();
	}

	@When("^Create a new page$")
	public void create_a_new_page() throws Throwable {
		clickOnAddNewPage();
		enterNewName();
		clickOnCreatePage();
	}

	@Then("^A new page is created$")
	public void a_new_page_is_created() throws Throwable {
		checkForTab("test");
		checkForStartNode();
	}

	// Step Definitions for Scenario 2

	@Given("^Go to Messaging$")
	public void go_to_Messaging() throws Throwable {
		clickOnMessagingMenu();
	}

	@When("^We add a SMS method$")
	public void we_add_a_SMS_method() throws Throwable {
		WebElement start_node = driver
				.findElement(By.xpath("//*[@id='module-1']//div[@class='syn-node ui-draggable syn-node-active']"));
		WebElement sms = driver.findElement(By.xpath(
				"//*[@class='ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom  ui-accordion-content-active']//li[text()='Send an SMS']"));
		dragAndDrop(sms, start_node, 0, 100);

	}

	@When("^Connect the start to SMS method$")
	public void connect_the_start_to_SMS_method() throws Throwable {
		WebElement start_node = driver
				.findElement(By.xpath("//*[@id='module-1']//div[@class='syn-node ui-draggable syn-node-active']"));
		WebElement sms_upper_node = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//*[@id='module-2']//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']")));

		Point start_node_loc = getLocationOfNode(start_node);
		Point sms_upper_node_loc = getLocationOfNode(sms_upper_node);

		int start_node_x = start_node_loc.getX();
		int start_node_y = start_node_loc.getY();

		int sms_upper_node_x = sms_upper_node_loc.getX();
		int sms_upper_node_y = sms_upper_node_loc.getY();

		int offset_x = sms_upper_node_x - start_node_x;
		int offset_y = sms_upper_node_y - start_node_y;

		dragAndDrop(start_node, offset_x, offset_y);
		fillTheSmsDetails();
	}

	@When("^If SMS is not sent add mail$")
	public void if_SMS_is_not_sent_add_mail() throws Throwable {
		WebElement start_node = driver
				.findElement(By.xpath("//div[@class='syn-node syn-node-attached-e ui-draggable syn-node-active']"));
		WebElement email = driver.findElement(By.xpath(
				"//*[@class='ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom  ui-accordion-content-active']//li[text()='Send an Email']"));
		dragAndDrop(email, start_node, 0, 100);
	}

	@When("^Connect SMS to mail$")
	public void connect_SMS_to_mail() throws Throwable {
		WebElement start_node = driver
				.findElement(By.xpath("//div[@class='syn-node syn-node-attached-e ui-draggable syn-node-active']"));
		WebElement email_upper_node = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//*[@id='module-3']//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']")));

		Point start_node_loc = getLocationOfNode(start_node);
		Point sms_upper_node_loc = getLocationOfNode(email_upper_node);

		int start_node_x = start_node_loc.getX();
		int start_node_y = start_node_loc.getY();

		int sms_upper_node_x = sms_upper_node_loc.getX();
		int sms_upper_node_y = sms_upper_node_loc.getY();

		int offset_x = sms_upper_node_x - start_node_x;
		int offset_y = sms_upper_node_y - start_node_y;

		dragAndDrop(start_node, offset_x, offset_y);

		filTheMailDetails();

	}

	@Then("^Add exit at all end points$")
	public void add_exit_at_all_end_points() throws Throwable {
		clickOnBasicMenu();
		WebElement exit = driver.findElement(By.xpath("//*[@id=\"accordion\"]//li[text()='Hang Up or Exit']"));

		WebElement start_node_1 = driver.findElement(By.xpath(
				"//div[@id='module-2']//div[@class='syn-node syn-node-attached-w ui-draggable syn-node-active']"));
		dragAndDrop(exit, start_node_1, -100, 50);

		WebElement start_node_2 = driver.findElement(By.xpath(
				"//div[@id='module-3']//div[@class='syn-node syn-node-attached-w ui-draggable syn-node-active']"));
		dragAndDrop(exit, start_node_2, -100, -50);

		WebElement start_node_3 = driver.findElement(By.xpath(
				"//div[@id='module-3']//div[@class='syn-node syn-node-attached-e ui-draggable syn-node-active']"));
		dragAndDrop(exit, start_node_3, 100, -50);

	}

	@Then("^Connect all the end points$")
	public void connect_all_the_end_points() throws Throwable {
		WebElement start_node = driver.findElement(By.xpath(
				"//div[@id='module-2']//div[@class='syn-node syn-node-attached-w ui-draggable syn-node-active']"));
		WebElement exit_upper_node_1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//*[@id='module-4']//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']")));

		int offset_x = calculateOffsetX(start_node, exit_upper_node_1);
		int offset_y = calculateOffsetY(start_node, exit_upper_node_1);

		dragAndDrop(start_node, offset_x, offset_y);

		start_node = driver.findElement(By.xpath(
				"//div[@id='module-3']//div[@class='syn-node syn-node-attached-w ui-draggable syn-node-active']"));
		WebElement exit_upper_node_2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//*[@id='module-5']//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']")));

		offset_x = calculateOffsetX(start_node, exit_upper_node_2);
		offset_y = calculateOffsetY(start_node, exit_upper_node_2);

		dragAndDrop(start_node, offset_x, offset_y);

		start_node = driver.findElement(By.xpath(
				"//div[@id='module-3']//div[@class='syn-node syn-node-attached-e ui-draggable syn-node-active']"));
		WebElement exit_upper_node_3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//*[@id='module-6']//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']")));

		offset_x = calculateOffsetX(start_node, exit_upper_node_3);
		offset_y = calculateOffsetY(start_node, exit_upper_node_3);

		dragAndDrop(start_node, offset_x, offset_y);

	}
}
