package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class N11TestDriver {
    public static WebDriver driver;
    public static WebDriver setupDriver(){
        if(driver == null){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        return driver;
    }

    public static void disposeDriver(){
        if(driver != null){
            driver.quit();
        }
    }

    public static void ClosePopUp(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // 5 saniye bekle
            WebElement closePopupButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btnBlack.btnSave")));
            closePopupButton.click();
            System.out.println("Pop-up kapatıldı.");
            Thread.sleep(1000); // Pop-up kapanış animasyonu için bekle
        } catch (Exception e) {
            System.out.println("Pop-up bulunamadı veya kapatılamadı. Devam ediliyor.");// Pop-up yoksa veya kapatılamazsa hata vermeden devam et.
        }
    }
}
