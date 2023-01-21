import TestSetupTeardown.HelperMethods;
import WebPageInteraction.PageElementsInteraction;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;


public class TestNG_SimpleTest extends PageElementsInteraction {

    @Parameters( {"browser", "testURL"})
    @Test
    public void doSomething(String browser, String testURL) throws IOException {
        WebDriver webdriver = getDriver();
        goToSpecifiedWebpage(getTestURL(testURL));
        acceptAllCookies();
        clickOnOurCars();
        clickOnCarModel();
        clickOnHatchbacks();
        clickOnBuildYourCar();
        selectFuelType();
        getAllPrices();
        getSortedPrices();
        writePricesToFile();
        HelperMethods.takeScreenshot(webdriver, getBrowserScreenshotsPath());
        //TODO: Check how many filters are set
    }

}
