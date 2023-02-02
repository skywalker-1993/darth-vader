package PassengerCars;

import static WebPageInteraction.ElementMapping.CAR_TYPES;
import static WebPageInteraction.ElementMapping.HATCHBACKS_MODELS;

import WebPageInteraction.PageElementsInteraction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

@Data
@EqualsAndHashCode(callSuper = false)
public class MainPage extends PageElementsInteraction {

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

}
