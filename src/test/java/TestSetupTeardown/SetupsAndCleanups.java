package TestSetupTeardown;

import static TestSetupTeardown.HelperMethods.getSeleniumHubLink;

import Browser.BrowserSelection;
import PassengerCars.FuelSelection;
import PassengerCars.MainPage;
import PassengerCars.ModelOverview;
import PassengerCars.PricesOverview;
import WebPageInteraction.PageElementsInteraction;
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

public class SetupsAndCleanups {

    protected String browserScreenshotsPath;
    protected WebDriver webDriver;

    public static final int IMPLICIT_WAIT = 30;
    private static final String REMOTE_URL_HUB = getSeleniumHubLink();

    private void setBrowserScreenshotsPath(String browser) {
        if (null == System.getenv("SELENIUM_HUB_LINK")) {
            this.browserScreenshotsPath = System.getProperty("user.home") + "/browser_storage/" + browser + "/";
        } else {
            this.browserScreenshotsPath = "/home/test_repo/browser_storage/" + browser + "/";
        }
    }

    protected String getBrowserScreenshotsPath() {
        return browserScreenshotsPath;
    }

    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    private void setWebDriver(String browser) throws MalformedURLException {
        this.webDriver = browserSelection.setRemoteWebDriver(driver, REMOTE_URL_HUB, browser).get();
    }

    protected WebDriver getWebDriver() {
        return webDriver;
    }

    private final Map<String, String> testURLsTable = new HashMap<>() {{
        put("WEBPAGE_QA_URL", "https://www.mercedes-benz.co.uk");
    }};

    public String getTestURL(String testConfigURL) {
        return testURLsTable.get(testConfigURL);
    }

    protected BrowserSelection browserSelection = new BrowserSelection();
    public PageElementsInteraction pageElements = new PageElementsInteraction();
    public MainPage mainPage = new MainPage();
    public ModelOverview modelOverview = new ModelOverview();
    public FuelSelection fuelSelection = new FuelSelection();
    public PricesOverview pricesOverview = new PricesOverview();

    public void goToSpecifiedWebpage(String webpageURL) {
        this.webDriver.navigate().to(webpageURL);
        Actions actionObject = new Actions(this.webDriver);
        actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
        this.webDriver.navigate().refresh();
        this.webDriver.manage().window().maximize();
    }

    @Parameters({"browser"})
    @BeforeTest
    public void initializeBrowserInstance(String browser) throws MalformedURLException {
        setWebDriver(browser);
        pageElements.setDriver(getWebDriver());
        mainPage.setDriver(getWebDriver());
        modelOverview.setDriver(getWebDriver());
        fuelSelection.setDriver(getWebDriver());
        pricesOverview.setDriver(getWebDriver());
        setBrowserScreenshotsPath(browser);
        pricesOverview.setBrowserScreenshotsPath(getBrowserScreenshotsPath());
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
    }

    @AfterTest
    public void closeBrowserInstance() {
        if (null != this.webDriver) this.webDriver.quit();
    }

}
