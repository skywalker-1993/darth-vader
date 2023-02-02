package PassengerCars;

import static TestSetupTeardown.HelperMethods.sleep;
import static WebPageInteraction.ElementMapping.FUEL_TYPES;

import WebPageInteraction.PageElementsInteraction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

@Data
@EqualsAndHashCode(callSuper = false)
public class FuelSelection extends PageElementsInteraction {

  public void checkFuelsSelectedCount(WebElement fuelButton) throws InterruptedException {
    sleep();
    Assert.assertEquals(fuelButton.findElement(By.tagName("wb-counter")).getText(), "1");
  }

  public void selectFuelType(String fuelType) throws InterruptedException {
    SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
    WebElement filterMain = shadowRoot.findElement(
        By.cssSelector("cc-motorization-filters > cc-motorization-filters-form > form > div" +
            " > div.cc-motorization-filters-form__primary > div.cc-motorization-filters-form__primary-filters" +
            ".ng-star-inserted > cc-motorization-filters-primary-filters > div > fieldset > " +
            "wb-multi-select-control"));
    WebElement filterButton = filterMain.findElement(By.tagName("button"));
    scrollToAndClick(filterButton);
    WebElement filterType = filterMain.findElement(By.cssSelector("div > div > wb-checkbox-control:nth-child(" +
        FUEL_TYPES.get(fuelType) + ") > label > div > wb-icon"));
    scrollToAndClick(filterType);
    checkFuelsSelectedCount(filterButton);
    filterButton.click();
  }

}
