package TestSetupTeardown;

import static TestSetupTeardown.HelperMethods.getSeleniumHubLink;

import Browser.BrowserSelection;
import WebPageInteraction.PageElementsInteraction;
import lombok.Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Data
public class SetupsAndCleanups {

    public static final int IMPLICIT_WAIT = 30;
    private static final String REMOTE_URL_HUB = getSeleniumHubLink();
    public static final String TEST_DATA_PATH = "/home/test_repo/testData/";
    private String browserDownloadsPath;
    private String browserScreenshotsPath;
    private WebDriver webdriver;
    private final Map<String, String> testURLsTable = new HashMap<String, String>();
    //private final Map<String, String> seleniumHubLink = new HashMap<String, String>();
    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    protected BrowserSelection browserSelection = new BrowserSelection();
    protected PageElementsInteraction testWebPage;

    public SetupsAndCleanups(){
        setTestURLsTable();
        //setSeleniumHubLink();
    }

    private void setTestURLsTable() {
        testURLsTable.put("WEBPAGE_QA_URL", "https://www.mercedes-benz.co.uk");
    }

//    private void setSeleniumHubLink(){
//        seleniumHubLink.put("SELENIUM_HUB_LOCAL", "http://localhost:4444/wd/hub");
//        seleniumHubLink.put("SELENIUM_HUB_DOCKER", "http://selenium-hub:4444/wd/hub");
//    }

    public String getTestURL(String testConfigURL){
        return testURLsTable.get(testConfigURL);
    }

    private void setPageElementsInteraction(WebDriver webdriver) {
        this.testWebPage = new PageElementsInteraction(webdriver);
    }

    private void setBrowserDownloadsPath(String browser){
        this.browserDownloadsPath = "/home/test_repo/browser_storage/" + browser + "/Downloads/";
    }

    public String getBrowserDownloadsPath() {
        return this.browserDownloadsPath;
    }

    private void setBrowserScreenshotsPath(String browser) {
        if (null == System.getenv("SELENIUM_HUB_LINK")) {
            //System.getProperty("user.dir")
            this.browserScreenshotsPath = "C:\\BrowserScreenshots\\" + browser + "\\";
        } else {
            this.browserScreenshotsPath = "/home/test_repo/browser_storage/" + browser + "/screenshots/";
        }
    }

    public String getBrowserScreenshotsPath() {
        return this.browserScreenshotsPath;
    }

    private void setDriver(String browser) throws MalformedURLException {
        this.webdriver = browserSelection.setRemoteWebDriver(driver, REMOTE_URL_HUB, browser).get();
    }

    public WebDriver getDriver(){
        return webdriver;
    }

    public void goToSpecifiedWebpage(String webpageURL) {
        this.webdriver.navigate().to(webpageURL);
        Actions actionObject = new Actions(this.webdriver);
        actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
        this.webdriver.navigate().refresh();
        this.webdriver.manage().window().maximize();
    }

    @Parameters({"browser"})
    @BeforeTest
    public void initializeBrowserInstance(String browser) throws MalformedURLException {
        setDriver(browser);
        setPageElementsInteraction(getDriver());
        setBrowserDownloadsPath(browser);
        setBrowserScreenshotsPath(browser);
        this.webdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
    }

    @AfterTest
    public void closeBrowserInstance() {
        if (null != this.webdriver) this.webdriver.quit();
    }
}
