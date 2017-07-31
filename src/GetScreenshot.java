import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yash on 7/29/17.
 */
public class GetScreenshot {
    public static String capture(WebDriver driver, String screenShotName) throws IOException
    {
        DateFormat df;
        //Date format for screenshot file name
        df = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //String dest = System.getProperty("user.dir") +"\\screenshots\\"+screenShotName+".png";

        //String dest = System.getProperty("user.dir") + "/extentReport/screenshots/" + System.currentTimeMillis() + ".png";
        String dest_path = "screenshots/" + "Screenshot_" + df.format(new Date()) + ".png";
        String dest = System.getProperty("user.dir") + "/extentReport/" + dest_path;
        String dest1 = "../" + dest_path;

        File destination = new File(dest);
        FileUtils.copyFile(source, destination);

        return dest1;

    }
}
