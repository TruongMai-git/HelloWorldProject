package Tester;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
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
	String EMAIL = ""; 
	String PASS = "";
	String originFilter = "project = PY AND issuetype = Story AND resolution = Unresolved order by updated DESC";
	String originXpath = "//ol[@class='issue-list']/li[";
	String TE = "[TE]";
	String TD = "[TD]";
	
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
		WebElement enterEmail = driver.findElement(By.name("username"));
		enterEmail.sendKeys(EMAIL);
		WebElement loginButton = driver.findElement(By.id("login-submit"));
		loginButton.click();
		sleepInSecond(3);
		WebElement passWord = driver.findElement(By.name("password"));
		passWord.sendKeys(PASS);
		WebElement loginButton1 = driver.findElement(By.id("login-submit"));
		loginButton1.click();
		sleepInSecond(5);
	}

	@Test
	public void TC_02_navigateToFilter() {
		/// Navigate to filter
		WebElement hamburger = driver.findElement(By.xpath("//span[@aria-label='Appswitcher Icon']"));
		hamburger.click();
		WebElement jiraSoft = driver.findElement(By.xpath("//span[text()='Jira Software']"));
		jiraSoft.click();
		sleepInSecond(3);
		WebElement filters = driver.findElement(By.xpath("//span[text()='Filters']"));
		filters.click();//open all filter
		WebElement viewAllFilters = driver.findElement(By.xpath("//span[text()='My open issues']"));
		viewAllFilters.click();//navigateFilter
		sleepInSecond(5);
		WebElement viewType = driver.findElement(By.id("layout-switcher-button"));
		WebElement columnButton = driver.findElement(By.xpath("//button[text()='Columns']"));
		if (columnButton.isDisplayed()) {
			viewType.click();
			driver.findElement(By.xpath("//a[text()='Detail View']")).click();
			sleepInSecond(5);

		}
		WebElement switchToJQL = driver.findElement(By.xpath("//a[text()='Switch to JQL']")); 
		if (switchToJQL.isDisplayed()) {
			switchToJQL.click();
		}
	}
	
	@Test 
	public void TC_03_allFilter() {
		/// Input filter
		WebElement jqltextarea = driver.findElement(By.xpath("//textarea[@id='advanced-search']"));
		jqltextarea.clear();
		jqltextarea.sendKeys(originFilter);
		WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
		searchButton.click();
		sleepInSecond(5);
	}
	
	@Test
	public void TC_04_() {
		//Get page size
		WebElement textOfPageSize = driver.findElement(By.xpath("//li[@class='showing']"));
		String subTring = textOfPageSize.getText().substring(5);
		int pageSize = Integer.parseInt(subTring);
		for(int i=1; i<= pageSize; i++){
			String s = Integer.toString(i);
        	String correctXpath = originXpath + s + ']';
        	System.out.print(correctXpath);
        	WebElement issue = driver.findElement(By.xpath(correctXpath));
			String ticketId= issue.getAttribute("data-key");
			String DATAKEY = " [" + ticketId + "] ";
			String TITLE = issue.getAttribute("title");
			System.out.println(ticketId);
			System.out.println(TITLE);
			/// Create a issue
			WebElement createNewIssue = driver.findElement(By.xpath("//*[@id='createGlobalItem']/span"));
			createNewIssue.click();			
			sleepInSecond(5);
			//Summary 
			WebElement inputSummary = driver.findElement(By.xpath("//input[@name='summary']")); 
			inputSummary.sendKeys(TE, DATAKEY, TITLE);
			sleepInSecond(4);
			//LinkedIssueType
			String JIRABROWSER = "https://selfstudy.atlassian.net/browse/";
			String TICKETLINK = JIRABROWSER + ticketId;
			//clickLinkedIssueType
			WebElement createIssueTitle = driver.findElement(By.xpath("//label[text()='Linked Issues']//following-sibling::div//div[@class=' css-hkzqy0-singleValue']")); 
			createIssueTitle.click();
			driver.findElement(By.xpath("//div[text()='blocks']")).click();
			sleepInSecond(3);
			//clickLinkedIssueId     >>
			WebElement linkinkedIssueId = driver.findElement(By.xpath("//div[text()='Select Issue']/parent::div//input")); 
			linkinkedIssueId.sendKeys(TICKETLINK);
			sleepInSecond(3);
			//choose exactId
			WebElement chooseExactId = driver.findElement(By.xpath("//div[contains(text(),'Exact key')]"));
			chooseExactId.click();
			sleepInSecond(2);
			//Click create  ticket
			driver.findElement(By.xpath("//button[@type='submit']")).click();
			sleepInSecond(5);
			
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
