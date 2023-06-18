package Tester;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
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
public class A01_Template {
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
		
	}

	@Test
	public void TC_02_inCorrectPassWord() {
		
	}
	
	@Test
	public void TC_03_inCorrectUserName() {
		
	}
	
	@Test
	public void TC_04_upperUserName() {
		
	}
	
	@Test
	public void TC_05_upperCaseFirstChar() {
		
	}
	
	@Test
	public void TC_05_forGotPassWord() {
		
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
