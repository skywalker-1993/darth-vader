package TestSetupTeardown;

import static java.lang.System.currentTimeMillis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Locale;


@Slf4j
public class HelperMethods {

    private static final int SLEEP_TIME = 500;

    public static String getTimestampString(){
        Timestamp timestamp = new Timestamp(currentTimeMillis());
        return Long.toString(timestamp.getTime());
    }

    public static void takeScreenshot(WebDriver webdriver, String screenshotsPath) {
        File directory = new File(screenshotsPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String copiedFile = screenshotsPath + "screenshot_" + getTimestampString() + ".png";
        try {
            File scrFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(copiedFile));
        }catch(IOException e){
            throw new UncheckedIOException("Output file " + copiedFile + " is in a directory that does not exist!", e);
        }
    }

    protected static String getSeleniumHubLink() {
        String link = System.getenv("SELENIUM_HUB_LINK");
        if (null == System.getenv("SELENIUM_HUB_LINK")) {
            return "http://localhost:4444/wd/hub";
        }
        return link;
    }

    public static int parseStringToInt(String s) {
        String mod = s.replaceAll(",", "");
        mod = mod.replaceAll("£", "");
        return Integer.parseInt(mod);
    }

    public static void sleep() throws InterruptedException {
        Thread.sleep(SLEEP_TIME);
    }

    public static void writeToFile(String fileName, String write) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(write);
        writer.close();
    }

    public static String getPriceUK(int priceInt) {
        String ukPrice = DecimalFormat.getCurrencyInstance(Locale.UK).format(priceInt);
        return ukPrice.substring(0, ukPrice.length() - 3);
    }

}
