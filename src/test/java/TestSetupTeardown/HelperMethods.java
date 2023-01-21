package TestSetupTeardown;

import static java.lang.System.currentTimeMillis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Timestamp;


@Slf4j
public class HelperMethods extends SetupsAndCleanups{

    private static final int WAIT_FOR_FILE = 30;

    public static void waitForFileToExist(File fullFilePath) throws FileNotFoundException {
        int timer = 0;
        try{
            while (!fullFilePath.exists() && timer <= WAIT_FOR_FILE){
                Thread.sleep(1000);
                timer++;
            }
            if(!fullFilePath.exists()) throw new FileNotFoundException("File " + fullFilePath + " was NOT found!");
        } catch (InterruptedException e) {
            log.error("[ERROR]: Thread wasn't put on hold while waiting for file!", e);
            Thread.currentThread().interrupt();
        }
    }

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

    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
    }

    public static void main(String[] args) {
        System.out.println(getSeleniumHubLink());
    }

}

