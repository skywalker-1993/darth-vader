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
        clickOnCarModel();
        clickOnCarClass();
        clickOnBuildYourCar();
        selectFuelType();
        getAllPrices();
        getSortedPrices();
        writePricesToFile();
    }

}
