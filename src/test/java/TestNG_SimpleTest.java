import WebPageInteraction.PageElementsInteraction;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;


public class TestNG_SimpleTest extends PageElementsInteraction {

    @Parameters( {"browser", "testURL"})
    @Test
    public void testClassAModels(String browser, String testURL) throws IOException {
        goToSpecifiedWebpage(getTestURL(testURL));
        acceptAllCookies();
        clickOnOurCars();
        clickOnCarType("Hatchbacks");
        clickOnCarModel("A-Class");
        clickOnBuildYourCar();
        selectFuelType("Diesel");
        getAllPrices();
        getSortedPrices();
        getResultsScreenshot();
        writePricesToFile();
        checkThatPricesMatchWithOrder();
        checkPriceRange();
    }

}
