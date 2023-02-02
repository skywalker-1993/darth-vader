package PassengerCars;

import static TestSetupTeardown.HelperMethods.getPriceUK;
import static TestSetupTeardown.HelperMethods.getTimestampString;
import static TestSetupTeardown.HelperMethods.parseStringToInt;
import static TestSetupTeardown.HelperMethods.sleep;
import static TestSetupTeardown.HelperMethods.writeToFile;
import static WebPageInteraction.ElementMapping.PRICE_SORT_TEXT;

import TestSetupTeardown.HelperMethods;
import WebPageInteraction.PageElementsInteraction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class PricesOverview extends PageElementsInteraction {

  private static final int UPPER_PRICE_LIMIT = 60000;
  private static final int LOWER_PRICE_LIMIT = 15000;

  private final List<Integer> priceList = new ArrayList<>();

  public int getMaximumPrice() {
    return Collections.max(this.priceList);
  }

  public int getMinimumPrice() {
    return Collections.min(this.priceList);
  }

  private final Map<String, Integer> uiPriceLimits = new HashMap<>() {{
    put("MAXIMUM_PRICE", -1);
    put("MINIMUM_PRICE", -1);
  }};

  private final Map<String, String> uiPricesText = new HashMap<>() {{
    put("MAXIMUM_PRICE", "");
    put("MINIMUM_PRICE", "");
  }};

  public List<Integer> getAllPrices() {
    SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
    WebElement allCarsContainer = shadowRoot.findElement(
        By.cssSelector("cc-motorization-comparison > div > div"));
    List<WebElement> allCars = allCarsContainer.findElements(By.xpath("*"));
    for (WebElement el : allCars) {
      String price = el.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
      this.priceList.add(parseStringToInt(price));
    }
    Reporter.log("[INFO]: All the gathered prices shown in the UI -> " + this.priceList);
    getEngineVariantsAmount(shadowRoot);
    return this.priceList;
  }

  public void selectSort(String sort) {
    SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
    Select dropdown = new Select(shadowRoot.findElement(By.cssSelector("#motorization-filters-sorting-options")));
    dropdown.selectByIndex(PRICE_SORT_TEXT.get(sort));
  }

  public String getFirstPrice() {
    SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
    WebElement firstPrice = shadowRoot.findElement(
        By.cssSelector("cc-motorization-comparison > div > div > div:nth-child(1)"));
    return firstPrice.findElement(By.className("cc-motorization-header__price--with" +
        "-environmental-hint")).getText();
  }

  public void getSortedPrices() {
    selectSort("descendantPrice");
    String firstPriceText = getFirstPrice();
    this.uiPriceLimits.put("MAXIMUM_PRICE", parseStringToInt(firstPriceText));
    this.uiPricesText.put("MAXIMUM_PRICE", firstPriceText);
    selectSort("ascendantPrice");
    firstPriceText = getFirstPrice();
    this.uiPriceLimits.put("MINIMUM_PRICE", parseStringToInt(firstPriceText));
    this.uiPricesText.put("MINIMUM_PRICE", firstPriceText);
    Reporter.log("[INFO]: UI Highest and Lowest Prices -> " + this.uiPriceLimits);
  }

  public void getResultsScreenshot() throws InterruptedException {
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript("scrollTo(0, document.body.scrollHeight/3);");
    sleep();
    HelperMethods.takeScreenshot(getDriver(), getBrowserScreenshotsPath());
  }

  public void writeActualPricesToFile() throws IOException {
    String stringToWrite = getPriceUK(getMaximumPrice()) + "\n" + getPriceUK(getMinimumPrice());
    writeToFile(this.getBrowserScreenshotsPath() +
        "testResult_" + getTimestampString() + ".txt", stringToWrite);
  }

  public void writePricesToFile() throws IOException {
    String stringToWrite = this.uiPricesText.get("MAXIMUM_PRICE") + "\n" + this.uiPricesText.get("MINIMUM_PRICE");
    writeToFile(this.getBrowserScreenshotsPath() +
        "testResult_" + getTimestampString() + ".txt", stringToWrite);
  }

  public void checkThatPricesMatchWithOrder() {
    Assert.assertEquals(this.uiPriceLimits.get("MINIMUM_PRICE") + " "
        + this.uiPriceLimits.get("MAXIMUM_PRICE"), getMinimumPrice() + " " + getMaximumPrice());
  }

  public void getEngineVariantsAmount(SearchContext shadowRoot) {
    String engineVariants = shadowRoot.findElement(By.cssSelector("cc-motorization-filters > div")).getText();
    Assert.assertEquals(Integer.parseInt(engineVariants.split(" ")[0]), this.priceList.size());
  }

  public void checkPriceRange() {
    Assert.assertTrue(LOWER_PRICE_LIMIT <= getMinimumPrice() &&
        UPPER_PRICE_LIMIT >= getMaximumPrice());
  }

}
