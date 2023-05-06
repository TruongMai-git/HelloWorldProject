package Tester;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
public class Testing {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("Webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("Webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");	
	}
	
	@Test
	public void TC_01_Loginsuccess() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		username.sendKeys("truongmairole9@yopmail.com");
		password.sendKeys("Test!234");
		loginButton.click();
		sleepInSecond(5);
		WebElement congratulations = driver.findElement(By.xpath("//h2"));
		
		Assert.assertEquals(congratulations.getText(),"Congratulations! You get inside of AgentHub.");
	}

	@Test
	public void TC_02_inCorrectPassWord() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		username.sendKeys("truongmairole9@yopmail.com");
		password.sendKeys("Test!1234");
		loginButton.click();
		sleepInSecond(5);
		WebElement errorMsg = driver.findElement(By.xpath("//form/div[3]/span"));
		Assert.assertEquals(errorMsg.getText(), "The email address or password you entered is incorrect, please review the information and try again.");
	}	
	
	@Test
	public void TC_03_inCorrectUserName() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		username.sendKeys("truongmairole9@yoail.com");
		password.sendKeys("Test!234");
		loginButton.click();
		sleepInSecond(5);
		WebElement errorMsg = driver.findElement(By.xpath("//form/div[3]/span"));
		Assert.assertEquals(errorMsg.getText(), "The email address or password you entered is incorrect, please review the information and try again.");
	}
	
	@Test
	public void TC_04_upperUserName() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		username.sendKeys("TRUONGMAIROLE9@YOPMAIL.COM");
		password.sendKeys("Test!234");
		loginButton.click();
		sleepInSecond(5);
		WebElement congratulations = driver.findElement(By.xpath("//h2"));
		
		Assert.assertEquals(congratulations.getText(),"Congratulations! You get inside of AgentHub.");
	}
	@Test
	public void TC_05_upperCaseFirstChar() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		username.sendKeys("Truongmairole9@yopmail.com");
		password.sendKeys("Test!234");
		loginButton.click();
		sleepInSecond(5);
		WebElement congratulations = driver.findElement(By.xpath("//h2"));
		
		Assert.assertEquals(congratulations.getText(),"Congratulations! You get inside of AgentHub.");
	}
	
	@Test
	public void TC_05_forGotPassWord() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/login");
		WebElement forGotPassWord = driver.findElement(By.xpath("//form/div[5]/a"));
		forGotPassWord.click();
		sleepInSecond(5);
		Assert.assertEquals(driver.getCurrentUrl(), "https://acb-int-agenthub-ui.creditstrong.com/user/password/reset");
		WebElement subHeader = driver.findElement(By.xpath("//h2"));
		WebElement messageforCS = driver.findElement(By.xpath("//form/preceding-sibling :: div/p"));
		WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
		WebElement cancelButton = driver.findElement(By.xpath("//button[@type='reset']"));
		Assert.assertEquals(subHeader.getText(), "Did you forget your password?");
		Assert.assertEquals(messageforCS.getText(), "Enter your email below and we will send you an email for you to reset your password.");
		Assert.assertFalse(submitButton.isEnabled());
		Assert.assertTrue(cancelButton.isEnabled());
		
	}
	
	@Test
	public void TC_06_inputEmailAddress() {
		driver.get("https://acb-int-agenthub-ui.creditstrong.com/user/password/reset");
		sleepInSecond(5);
		WebElement inputEmail = driver.findElement(By.xpath("//input"));
		inputEmail.sendKeys("acbcabc");
		WebElement errorMsg = driver.findElement(By.xpath("//div/div/div/div/span"));
		Assert.assertEquals(errorMsg.getText(), "Invalid email");
		
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	private void sleepInSecond(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
