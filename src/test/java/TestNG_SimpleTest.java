import TestSetupTeardown.HelperMethods;
import TestSetupTeardown.SetupsAndCleanups;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestNG_SimpleTest extends SetupsAndCleanups {
    ///html/body/cmm-cookie-banner//div/div/div[2]/cmm-buttons-wrapper/div/div/button[2]
    @Parameters( {"browser", "testURL"})
    @Test
    public void doSomething(String browser, String testURL) {
        WebDriver webdriver = getDriver();
        goToSpecifiedWebpage(getTestURL(testURL));
        testWebPage.acceptAllCookies();
        testWebPage.clickOnOurCars();
        testWebPage.clickOnCarModel();
        testWebPage.clickOnHatchbacks();
        testWebPage.clickOnBuildYourCar();
        testWebPage.selectFuelType();
        testWebPage.getAllPrices();
        Reporter.log(String.valueOf(testWebPage.getMaximumPrice()));
        Reporter.log(String.valueOf(testWebPage.getMinimumPrice()));
        testWebPage.selectSort("ascendantPrice");
        HelperMethods.takeScreenshot(webdriver, getBrowserScreenshotsPath());
        //TODO: Check how many filters are set
    }

}
