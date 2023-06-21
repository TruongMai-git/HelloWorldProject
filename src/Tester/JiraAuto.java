package Tester;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class JiraAuto {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String URL = "https://id.atlassian.com/login";
	String EMAIL = "@gmail.com";
	String PASS = "Test!234";
	String originFilter = "project = PY AND issuetype = Story AND resolution = Unresolved ORDER BY key ASC, updated DESC";
	String TE = "[TE]";
	String TD = "[TD]";
	String jiraBrowser = "https://selfstudy.atlassian.net/browse/";

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
		driver.get(URL);
	}

	@Test
	public void TC_01_logInSuccess() {
		driver.findElement(By.name("username")).sendKeys(EMAIL);
		driver.findElement(By.id("login-submit")).click();
		sleepInSecond(3);
		driver.findElement(By.name("password")).sendKeys(PASS);
		driver.findElement(By.id("login-submit")).click();
		sleepInSecond(5);
	}

	@Test
	public void TC_02_navigateToFilter() {
		/// Navigate to filter
		driver.findElement(By.xpath("//span[@aria-label='Appswitcher Icon']")).click();
		driver.findElement(By.xpath("//span[text()='Jira Software']")).click();
		sleepInSecond(3);
		// open all filter
		driver.findElement(By.xpath("//span[text()='Filters']")).click();
		// navigateFilter
		driver.findElement(By.xpath("//span[text()='My open issues']")).click();
		sleepInSecond(5);

		// switch to Detail view
		WebElement activeLayout = driver
				.findElement(By.xpath("//ul[@class = 'aui-list-section aui-first aui-last']/li[1]//span"));
		if (activeLayout.getCssValue("class") != "aui-icon aui-icon-small aui-iconfont-success") {
			activeLayout.click();
		}
		sleepInSecond(5);
		// switch to advance search
		WebElement nowInactive = driver.findElement(By.cssSelector(".switcher-item"));
		if (nowInactive.getCssValue("data-id") != "basic") {
			nowInactive.click();
		}
	}

	@Test
	public void TC_03_allFilter() {
		/// Input filter
		WebElement jqltextarea = driver.findElement(By.xpath("//textarea[@id='advanced-search']"));
		jqltextarea.clear();
		jqltextarea.sendKeys(originFilter);
		driver.findElement(By.xpath("//button[text()='Search']")).click();
		sleepInSecond(5);
	}

	@Test
	public void TC_04_createAllIssue() {
		/// Create Issue Base On Parent List
		List<WebElement> allIssue = driver.findElements(By.cssSelector("ol.issue-list>li"));
		int pageSize = allIssue.size();
		System.out.println("Total issue:" + pageSize);
		for (WebElement issue : allIssue) {
			String title = issue.getAttribute("title");
			String dataKey = issue.getAttribute("data-key");
			String newTitle = TE + " [" + dataKey + "] " + title;

			/// Create a Issue
			driver.findElement(By.xpath("//*[@id='createGlobalItem']/span")).click();
			sleepInSecond(3);
			// Summary
			driver.findElement(By.name("summary")).sendKeys(newTitle);
			sleepInSecond(2);
			// Link Parent Issue
			driver.findElement(By.xpath(
					"//label[text()='Linked Issues']//following-sibling::div//div[@class=' css-hkzqy0-singleValue']"))
					.click();
			driver.findElement(By.xpath("//div[text()='blocks']")).click();
			// Click on select issue drop down
			driver.findElement(By.xpath("//div[text()='Select Issue']")).click();
			driver.findElement(By.xpath(
					"//div[text()='Select Issue']//following-sibling::div//input[contains(text(),react-select-)]"))
					.sendKeys(jiraBrowser, dataKey);
			sleepInSecond(2);
			driver.findElement(By.xpath("//div[contains(text(),'Exact key')]")).click();
			sleepInSecond(2);
			// selectSprint //clickLabels//SelectPriority//SelectPriority//clickFixversion
			// Select Project
			// Select Component
			// Select Fix version
			// Select Priority
			// Select Labels
			// Select Sprint
			
			// Click create Ticket
			driver.findElement(By.xpath("//button[@type='submit']")).click();
			sleepInSecond(3);
			System.out.println("Created issue" + newTitle);

		}
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
