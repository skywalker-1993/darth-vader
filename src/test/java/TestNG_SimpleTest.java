import TestSetupTeardown.SetupsAndCleanups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;


public class TestNG_SimpleTest extends SetupsAndCleanups {

    @Parameters({"browser", "testURL"})
    @Test
    public void testClassAModels(String browser, String testURL) throws IOException, InterruptedException {
        goToSpecifiedWebpage(getTestURL(testURL));
        mainPage.acceptAllCookies();
        mainPage.clickOnOurCars();
        mainPage.clickOnCarType("Hatchbacks");
        mainPage.clickOnCarModel("A-Class");
        modelOverview.clickOnBuildYourCar();
        fuelSelection.selectFuelType("Diesel");
        pricesOverview.getAllPrices();
        pricesOverview.getSortedPrices();
        pricesOverview.getResultsScreenshot();
        pricesOverview.writePricesToFile();
        pricesOverview.checkThatPricesMatchWithOrder();
        pricesOverview.checkPriceRange();
    }

}
