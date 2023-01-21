package WebPageInteraction;

import static TestSetupTeardown.HelperMethods.getTimestampString;
import static TestSetupTeardown.HelperMethods.parseStringToInt;
import static TestSetupTeardown.HelperMethods.sleep;
import static TestSetupTeardown.HelperMethods.writeToFile;

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

    private static final Map<String, Integer> PRICE_SORT_TEXT = new HashMap<>(){{
        put("ascendantPrice", 4);
        put("descendantPrice", 5);
    }};

    private final List<Integer> priceList = new ArrayList<>();

    private final Map<String, Integer> uiPriceLimits = new HashMap<>(){{
        put("MAXIMUM_PRICE", -1);
        put("MINIMUM_PRICE", -1);
    }};

    public void removeCookieBanner() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("return document.getElementsByTagName('cmm-cookie-banner')[0].remove();");
    }

    public void acceptAllCookies() {
        WebElement shadowHost = getDriver().findElement(By.tagName("cmm-cookie-banner"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(By.cssSelector("div > div > div.cmm-cookie-banner__content > " +
            "cmm-buttons-wrapper > div > div > button.wb-button.wb-button--primary.wb-button--small" +
            ".wb-button--accept-all")).click();
    }

    public void clickOnOurCars() {
        WebElement shadowHost = getDriver().findElement(By.tagName("owc-header"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(By.cssSelector("nav.owc-header__header-navigation > div > ul > li" +
            ".owc-header-navigation-topic" +
            ".owc-header-navigation-topic--desktop-nav.owc-header-navigation-topic__model-flyout > button > p"))
            .click();

    }

    public void clickOnCarModel() {
        WebElement shadowHost = getDriver().findElement(By.tagName("vmos-flyout"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        //4th Elements is Hatchback
        shadowRoot.findElement(By.cssSelector("#app-vue > div > ul > li:nth-child(3) > ul > li:nth-child(4) > div > p"))
            .click();
    }

    public void clickOnHatchbacks() {
        WebElement shadowHost = getDriver().findElement(By.tagName("vmos-flyout"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        //Class A -> index 1
        shadowRoot.findElement(By.cssSelector("#app-vue > div > owc-header-flyout > ul > li > ul > li:nth-child(1) > " +
                "a > p"))
            .click();
    }

    public void clickOnBuildYourCar() {
        WebElement shadowHost = getDriver().findElement(By.tagName("owc-stage"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(By.cssSelector("a.owc-stage-cta-buttons__button.wb-button" +
                ".wb-button--medium.wb-button--theme-dark.wb-button--large.wb-button--secondary" +
                ".owc-stage-cta-buttons__button--secondary"))
            .click();
    }

    private void scrollUntilClickable(WebElement element) {
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

    public void selectFuelType() {
        WebElement shadowHost = getDriver().findElement(By.tagName("owcc-car-configurator"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement filterMain = shadowRoot.findElement(
            By.cssSelector("cc-motorization-filters > cc-motorization-filters-form > form > div" +
                " > div.cc-motorization-filters-form__primary > div.cc-motorization-filters-form__primary-filters" +
                ".ng-star-inserted > cc-motorization-filters-primary-filters > div > fieldset > " +
                "wb-multi-select-control"));
        WebElement filterButton = filterMain.findElement(By.tagName("button"));
        scrollUntilClickable(filterButton);
        WebElement filterType = filterMain.findElement(By.cssSelector("div > div > wb-checkbox-control:nth-child(2) >" +
            " label > div > wb-icon"));
        scrollUntilClickable(filterType);
        checkFuelsSelectedCount(filterButton);
        filterButton.click();
    }

    public List<Integer> getAllPrices() {
        WebElement shadowHost = getDriver().findElement(By.tagName("owcc-car-configurator"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement allCarsContainer = shadowRoot.findElement(
            By.cssSelector("cc-motorization-comparison > div > div"));
        List<WebElement> allCars = allCarsContainer.findElements(By.xpath("*"));
        Reporter.log(String.valueOf(allCars.size()));
        for (WebElement el: allCars) {
            String price = el.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
            this.priceList.add(parseStringToInt(price));
        }
        Reporter.log(this.priceList.toString());
        //TODO: Assert message with total amount
//        "#cc-app-container-main > div.cc-app-container__main-frame.cc-grid-container > div.cc-grid-container" +
//            ".ng-star-inserted > div > div:nth-child(2) > cc-motorization > cc-motorization-filters > div"
        return this.priceList;
    }

   public void selectSort(String sort) {
       WebElement shadowHost = getDriver().findElement(By.tagName("owcc-car-configurator"));
       SearchContext shadowRoot = shadowHost.getShadowRoot();
       Select dropdown = new Select(shadowRoot.findElement(By.cssSelector("#motorization-filters-sorting-options")));
       dropdown.selectByIndex(PRICE_SORT_TEXT.get(sort));
   }

   public void getSortedPrices() {
       WebElement shadowHost = getDriver().findElement(By.tagName("owcc-car-configurator"));
       SearchContext shadowRoot = shadowHost.getShadowRoot();
       selectSort("descendantPrice");
       WebElement firstPrice = shadowRoot.findElement(
           By.cssSelector("cc-motorization-comparison > div > div > div:nth-child(1)"));
       String firstPriceText = firstPrice.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
       this.uiPriceLimits.put("MAXIMUM_PRICE", parseStringToInt(firstPriceText));
       selectSort("ascendantPrice");
       firstPrice = shadowRoot.findElement(
           By.cssSelector("cc-motorization-comparison > div > div > div:nth-child(1)"));
       firstPriceText =
           firstPrice.findElement(By.className("cc-motorization-header__price--with-environmental-hint")).getText();
       this.uiPriceLimits.put("MINIMUM_PRICE", parseStringToInt(firstPriceText));
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

}
