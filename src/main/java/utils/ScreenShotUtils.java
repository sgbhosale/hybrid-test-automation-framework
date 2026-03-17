package utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenShotUtils {

	public  String captureScreenshot(WebDriver driver, String testName) {			
        String path = System.getProperty("user.dir")+"/Screenshot/" +testName +setDateTime()+ ".jpeg";
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path; // return screenshot path for logging
    }
	
	public String setDateTime() {
		// Current date and time
		LocalDateTime now = LocalDateTime.now();
		// Format the date and time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
		String formatted = now.format(formatter).toString();
		return formatted;
	}

}
