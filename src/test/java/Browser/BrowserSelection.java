package Browser;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


@Slf4j
public class BrowserSelection {
  private final String[] chromeOptionArguments = {"start-maximized", "enable-automation", "--no-sandbox",
      "--disable-infobars", "--disable-dev-shm-usage", "--disable-browser-side-navigation", "--disable-gpu"};
  private final String[] firefoxOptionArguments = {"start-maximized", "--no-sandbox", "--disable-infobars",
      "--disable-dev-shm-usage", "--disable-browser-side-navigation", "--disable-gpu"};
  private final String[] edgeOptionArguments = {"start-maximized", "enable-automation", "--no-sandbox",
      "--disable-infobars", "--disable-dev-shm-usage", "--disable-browser-side-navigation", "--disable-gpu"};
  private ChromeOptions chromeOptions;
  private FirefoxOptions firefoxOptions;
  private EdgeOptions edgeOptions;

  private void setBrowserOptions(String browser) {
    switch (browser){
      case "chrome":
        for (String argument : chromeOptionArguments) {
          this.chromeOptions.addArguments(argument);
        }
        break;
      case "firefox":
        for (String argument : firefoxOptionArguments) {
          this.firefoxOptions.addArguments(argument);
        }
        break;
      case "edge":
        for (String argument : edgeOptionArguments) {
          this.edgeOptions.addArguments(argument);
        }
        break;
      default:
        throw new IllegalArgumentException("[ERROR]: Options - Browser " + browser + " does not exist!");
    }
  }

  public ThreadLocal<RemoteWebDriver> setRemoteWebDriver(ThreadLocal<RemoteWebDriver> driver, String remote_url_hub, String browser) throws MalformedURLException {
    switch (browser) {
      case "chrome":
        setChromeOptions();
        driver.set(new RemoteWebDriver(new URL(remote_url_hub), this.chromeOptions));
        break;
      case "firefox":
        setFirefoxOptions();
        driver.set(new RemoteWebDriver(new URL(remote_url_hub), this.firefoxOptions));
        break;
      case "edge":
        setEdgeOptions();
        driver.set(new RemoteWebDriver(new URL(remote_url_hub), this.edgeOptions));
        break;
      default:
        throw new IllegalArgumentException("[ERROR]: RemoteWebDriver - Browser " + browser + " does not exist!");
    }
    return driver;
  }

  private void setFirefoxOptions() {
    this.firefoxOptions = new FirefoxOptions();
    setBrowserOptions("firefox");
    FirefoxProfile fxProfile = new FirefoxProfile();
    fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/json");
    this.firefoxOptions.setProfile(fxProfile);
  }

  private void setEdgeOptions() {
    this.edgeOptions = new EdgeOptions();
    setBrowserOptions("edge");
  }

  private void setChromeOptions() {
    this.chromeOptions = new ChromeOptions();
    setBrowserOptions("chrome");
  }

  public FirefoxOptions getFirefoxOptions() {
    return firefoxOptions;
  }

  public EdgeOptions getEdgeOptions() {
    return edgeOptions;
  }

  public ChromeOptions getChromeOptions() {
    return chromeOptions;
  }

  private String[] addArgumentsToOptions(String[] optionArguments, String argument) {
    String[] newOptions = new String[optionArguments.length + 1];
    for (int i=0; i<optionArguments.length; i++){
      newOptions[i] = optionArguments[i];
    }
    newOptions[optionArguments.length] = argument;
    return newOptions;
  }

}
