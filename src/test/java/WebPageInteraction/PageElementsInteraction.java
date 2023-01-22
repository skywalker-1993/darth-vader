package WebPageInteraction;

import static TestSetupTeardown.HelperMethods.getTimestampString;
import static TestSetupTeardown.HelperMethods.parseStringToInt;
import static TestSetupTeardown.HelperMethods.sleep;
import static TestSetupTeardown.HelperMethods.writeToFile;
import static WebPageInteraction.ElementMapping.CAR_TYPES;
import static WebPageInteraction.ElementMapping.FUEL_TYPES;
import static WebPageInteraction.ElementMapping.HATCHBACKS_MODELS;
import static WebPageInteraction.ElementMapping.PRICE_SORT_TEXT;

import TestSetupTeardown.HelperMethods;
import TestSetupTeardown.SetupsAndCleanups;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
public class PageElementsInteraction extends SetupsAndCleanups {

    private static final int UPPER_PRICE_LIMIT = 60000;
    private static final int LOWER_PRICE_LIMIT = 15000;

    private final List<Integer> priceList = new ArrayList<>();

    private final Map<String, Integer> uiPriceLimits = new HashMap<>(){{
        put("MAXIMUM_PRICE", -1);
        put("MINIMUM_PRICE", -1);
    }};

    private SearchContext getShadowRoot(String tagName) {
        WebElement shadowHost = getDriver().findElement(By.tagName(tagName));
        return shadowHost.getShadowRoot();
    }

    public void acceptAllCookies() {
        SearchContext shadowRoot = getShadowRoot("cmm-cookie-banner");
        shadowRoot.findElement(By.cssSelector("div > div > div.cmm-cookie-banner__content > " +
            "cmm-buttons-wrapper > div > div > button.wb-button.wb-button--primary.wb-button--small" +
            ".wb-button--accept-all")).click();
    }

    public void clickOnOurCars() {
        SearchContext shadowRoot = getShadowRoot("owc-header");
        shadowRoot.findElement(By.cssSelector("nav.owc-header__header-navigation > div > ul > li" +
            ".owc-header-navigation-topic" +
            ".owc-header-navigation-topic--desktop-nav.owc-header-navigation-topic__model-flyout > button > p"))
            .click();
    }

    public void clickOnCarType(String type) {
        SearchContext shadowRoot = getShadowRoot("vmos-flyout");
        //4th Elements is Hatchback
        shadowRoot.findElement(By.cssSelector("#app-vue > div > ul > li:nth-child(3) > ul > li:nth-child(" +
                CAR_TYPES.get(type) + ") > div > p"))
            .click();
    }

    public void clickOnCarModel(String model) {
        SearchContext shadowRoot = getShadowRoot("vmos-flyout");
        shadowRoot.findElement(By.cssSelector("#app-vue > div > owc-header-flyout > ul > li > ul > li:nth-child(" +
                HATCHBACKS_MODELS.get(model) + ") > a > p"))
            .click();
    }

    public void clickOnBuildYourCar() {
        SearchContext shadowRoot = getShadowRoot("owc-stage");
        shadowRoot.findElement(By.cssSelector("a.owc-stage-cta-buttons__button.wb-button" +
                ".wb-button--medium.wb-button--theme-dark.wb-button--large.wb-button--secondary" +
                ".owc-stage-cta-buttons__button--secondary"))
            .click();
    }

    private void scrollToAndClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        executor.executeScript("arguments[0].click();", element);
    }

    public void checkFuelsSelectedCount(WebElement fuelButton) {
        sleep();
        long initTime = System.currentTimeMillis();
        boolean isTextUpdate = fuelButton.findElement(By.tagName("wb-counter")).getText().isBlank();
        while (isTextUpdate || IMPLICIT_WAIT >= System.currentTimeMillis() - initTime) {
            isTextUpdate = fuelButton.findElement(By.tagName("wb-counter")).getText().isBlank();
        }
        Assert.assertEquals(fuelButton.findElement(By.tagName("wb-counter")).getText(), "1");
    }

    public void selectFuelType(String fuelType) {
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

    public List<Integer> getAllPrices() {
        SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
        WebElement allCarsContainer = shadowRoot.findElement(
            By.cssSelector("cc-motorization-comparison > div > div"));
        List<WebElement> allCars = allCarsContainer.findElements(By.xpath("*"));
        Reporter.log(String.valueOf(allCars.size()));
        for (WebElement el: allCars) {
            String price = el.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
            this.priceList.add(parseStringToInt(price));
        }
        Reporter.log(this.priceList.toString());
        getEngineVariantsAmount(shadowRoot);
        return this.priceList;
    }

    public void selectSort(String sort) {
       SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
       Select dropdown = new Select(shadowRoot.findElement(By.cssSelector("#motorization-filters-sorting-options")));
       dropdown.selectByIndex(PRICE_SORT_TEXT.get(sort));
    }

    public void getSortedPrices() {
       SearchContext shadowRoot = getShadowRoot("owcc-car-configurator");
       selectSort("descendantPrice");
       WebElement firstPrice = shadowRoot.findElement(
           By.cssSelector("cc-motorization-comparison > div > div > div:nth-child(1)"));
       String firstPriceText = firstPrice.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
       this.uiPriceLimits.put("MAXIMUM_PRICE", parseStringToInt(firstPriceText));
       HelperMethods.takeScreenshot(getDriver(), getBrowserScreenshotsPath());
       selectSort("ascendantPrice");
       firstPrice = shadowRoot.findElement(
           By.cssSelector("cc-motorization-comparison > div > div > div:nth-child(1)"));
       firstPriceText =
           firstPrice.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
       this.uiPriceLimits.put("MINIMUM_PRICE", parseStringToInt(firstPriceText));
       HelperMethods.takeScreenshot(getDriver(), getBrowserScreenshotsPath());
       Reporter.log(this.uiPriceLimits.toString());
    }

    public void writePricesToFile() throws IOException {
       String stringToWrite = getMaximumPrice() + "\n" + getMinimumPrice();
       writeToFile(this.getBrowserScreenshotsPath() +
           "testResult_" + getTimestampString() + ".txt", stringToWrite);
    }

    public int getMaximumPrice() {
        return Collections.max(this.priceList);
    }

    public int getMinimumPrice() {
        return Collections.min(this.priceList);
    }

    public void checkThatPricesMatchWithOrder() {
        Assert.assertEquals(this.uiPriceLimits.get("MINIMUM_PRICE") + " "
            + this.uiPriceLimits.get("MAXIMUM_PRICE"), getMinimumPrice() + " " + getMaximumPrice());
    }

    public void getEngineVariantsAmount(SearchContext shadowRoot) {
        String engineVariants = shadowRoot.findElement(By.cssSelector("cc-motorization-filters > div")).getText();
        Assert.assertEquals(this.priceList.size(), Integer.parseInt(engineVariants.split(" ")[0]));
    }

    public void checkPriceRange() {
        Assert.assertTrue(LOWER_PRICE_LIMIT <= getMinimumPrice() &&
            UPPER_PRICE_LIMIT >= getMaximumPrice());
    }

}
