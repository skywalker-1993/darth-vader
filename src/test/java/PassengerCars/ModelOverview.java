package PassengerCars;

import WebPageInteraction.PageElementsInteraction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

@Data
@EqualsAndHashCode(callSuper = false)
public class ModelOverview extends PageElementsInteraction {

  public void clickOnBuildYourCar() {
    SearchContext shadowRoot = getShadowRoot("owc-stage");
    shadowRoot.findElement(By.cssSelector("a.owc-stage-cta-buttons__button.wb-button" +
            ".wb-button--medium.wb-button--theme-dark.wb-button--large.wb-button--secondary" +
            ".owc-stage-cta-buttons__button--secondary"))
        .click();
  }

}
