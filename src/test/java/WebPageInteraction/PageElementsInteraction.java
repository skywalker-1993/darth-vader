package WebPageInteraction;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


@Slf4j
@Data
public class PageElementsInteraction {

    private WebDriver driver;
    private String browserScreenshotsPath;

    public SearchContext getShadowRoot(String tagName) {
        WebElement shadowHost = getDriver().findElement(By.tagName(tagName));
        return shadowHost.getShadowRoot();
    }

    public void scrollToAndClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        executor.executeScript("arguments[0].click();", element);
    }

}
