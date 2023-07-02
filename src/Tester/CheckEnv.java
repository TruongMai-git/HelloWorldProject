package Tester;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class CheckEnv {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;
	JavascriptExecutor jsExcutor;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("Webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("Webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		jsExcutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");

	}

	@Test
	public void TC_01_Url() {
		AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
	}

	//	explicitWait.until(ExpectedConditions.attributeToBe());
	@Test
	public void TC_05_Url() {
		System.out.println("SSH Port Forwarding");
	}

	@Test
	public void TC_02_Logo() {
		AssertJUnit.assertTrue(driver.findElement(By.cssSelector("img.fb_logo")).isDisplayed());
	}

	@Test
	public void TC_03_Form() {
		AssertJUnit.assertTrue(driver.findElement(By.xpath("//form[@data-testid='royal_login_form']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public boolean isPageLoadedSuccess() {
		explicitWait = new WebDriverWait(driver, 30);
		ExpectedCondition<Boolean> jqueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExcutor.executeScript("return (window.jQuery != null) && (jQuery.active ===0);");
			}
		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExcutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jqueryLoad) && explicitWait.until(jsLoad);
	}
}

























