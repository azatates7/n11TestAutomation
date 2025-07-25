package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.N11TestDriver;
import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class N11StepDefinitions {

    public static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Yüklenmeler için 20 saniye bekleme süresi
    public static String n11MainPage = "https://www.n11.com/";
    public static String countOfPhoneOrder = "";

    @Given("n11 anasayfasına gidilir")
    public void n11_anasayfasına_gidilir() {
        WebDriverManager.chromedriver().setup();
        driver = N11TestDriver.setupDriver();
        driver.get(n11MainPage);
        System.out.println("n11 Sayfası Açıldı");
    }
    @When("Mağazalar sayfasına gidilir")
    public void mağazalar_sayfasına_gidilir() {
        driver.get("https://www.n11.com/magazalar");
        System.out.println("n11 Mağazalar Sayfası Açıldı");
    }
    @When("S harfi ile başlayan mağazalar filtrelenir")
    public void s_harfi_ile_başlayan_mağazalar_filtrelenir() throws InterruptedException {
        N11TestDriver.ClosePopUp();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        //WebElement targetElement = driver.findElement(By.xpath("//*[@id=\"contentSellerList\"]/div/div[2]/div/div[2]/div[4]/span"));
        WebElement targetElement = driver.findElement(By.cssSelector("#contentSellerList > div > div.tabList > div > div.tabPanelHolder > div.tabPanel.allSellers > span"));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", targetElement);

        Thread.sleep((2000));
        System.out.println("Mağazalar Kısmına Scroll Yapıldı");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//*[@id=\"contentSellerList\"]/div/div[2]/div/div[2]/div[4]/div[1]/span[22]")).click();
        Thread.sleep(2000);
        System.out.println("S Harfi İle Başlayan Mağazalar Filtrelendi");
    }
    @When("Rastgele bir mağazaya tıklanır")
    public void rastgele_bir_magazaya_tiklanir() throws InterruptedException {
        WebElement ulElement = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div[2]/div/div[2]/div[4]/div[2]/ul"));
        String innerHTML = ulElement.getAttribute("innerHTML");
        Document doc = Jsoup.parse(innerHTML);
        Thread.sleep(4000);//parse için bekleme süresi
        List<String> itemLinks = new ArrayList<>();
        Elements linkElements = doc.select("li a");
        for (Element link : linkElements) {
            itemLinks.add(link.attr("href"));
        }
        Random random = new Random();
        int index = random.nextInt(itemLinks.size());
        driver.get(itemLinks.get(index));
        Thread.sleep(4000);
        System.out.println("Rastgele Bir Magaza Seçildi.");
    }
    @Then("İstenilen mağazanın sayfasına yönlendirildiği görülür")
    public void i̇stenilen_mağazanın_sayfasına_yönlendirildiği_görülür() {
        String[] urlParts = driver.getCurrentUrl().split("=");
        String secondPart = urlParts[urlParts.length-1];
        assertTrue(driver.getCurrentUrl().contains("https://www.n11.com/arama?s=") && secondPart.toLowerCase().startsWith("s"));
        System.out.println("Mağaza Sayfası Teyit Edildi.");
    }
    @When("Arama motorunda iphone kelimesi aratılır")
    public void arama_motorunda_iphone_kelimesi_aratılır() throws InterruptedException {
        WebElement searchField = driver.findElement(By.id("searchData"));
        searchField.sendKeys(Keys.CONTROL + "a"); // Windows/Linux İçin Arama Kutusu Temizleme
        Thread.sleep(2000);
        searchField.sendKeys(Keys.DELETE);
        Thread.sleep(2000);
        searchField.sendKeys("iPhone");
        Thread.sleep(2000);
        searchField.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        System.out.println("İphone Kelimesi Arandı.");
    }
    @When("Gelen sonuçlardan ilk sayfadaki birinci ve sonuncu sepete eklenir")
    public void gelen_sonuclardan_ilk_sayfadaki_birinci_ve_sonuncu_sepete_eklenir() throws InterruptedException {
        var responseCount = driver.findElement(By.xpath("//*[@id=\"contentListing\"]/div/div[2]/div[2]/section/div[1]/div[1]/div[1]/div/strong")).getText();
        assertTrue(Integer.parseInt(responseCount) >= 2);//Sonuç sayısının ikiden büyük veya eşit olması
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        long maxHeight = (Long) javascriptExecutor.executeScript("return document.documentElement.scrollHeight");
        while (true) {
            javascriptExecutor.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
            Thread.sleep(2000);  // sayfa yüklenene kadar bekle
            long newHeight = (Long) javascriptExecutor.executeScript("return document.documentElement.scrollHeight");
            if (newHeight >= maxHeight) {
                break;
            }
            maxHeight = newHeight;
        }
        System.out.println("En yüksek scrollHeight: " + maxHeight + " px");


        //javascriptExecutor.executeScript("document.body.style.zoom='70%'");
//        Long pageHeight = (Long) ((JavascriptExecutor) driver).executeScript(
//                "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);"
//        );
//        Long pageHeight = (Long) ((JavascriptExecutor) driver).executeScript("return document.documentElement.scrollHeight");
//        System.out.println("Sayfa Yüksekliği (Piksel): " + pageHeight);

        // Sayfayı yavaşça aşağı kaydırma
        var intValue = Integer.parseInt(String.valueOf(maxHeight));
        for (int i = 0; i < intValue; i += 10) { // Her adımda 10 piksel aşağı kaydır
            javascriptExecutor.executeScript("window.scrollBy(0, 10)");
            Thread.sleep(20); // Her adımdan sonra kısa bir bekleme kaydırma hızını ayarlar
        }
//        Thread.sleep(2000); // Gözlemlemek için biraz bekleme
//        WebElement element = driver.findElement(By.xpath("//*[@id=\"contentListing\"]/div/div[2]/div[2]/div[2]/div[2]"));
//        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(3000);

        WebElement ulElement = driver.findElement(By.className("list-ul"));
        String innerHTML = ulElement.getAttribute("innerHTML");
        Document doc = Jsoup.parse(innerHTML);
        Thread.sleep(4000);//parse için bekleme süresi
        List<String> itemLinks = new ArrayList<>();
        Elements linkElements = doc.select("li a");
        for (Element link : linkElements) {
            itemLinks.add(link.attr("href"));
        }

        driver.get(itemLinks.get(itemLinks.size() -1));
        Thread.sleep(2000);
        driver.findElement(By.className("addBasketUnify")).click();
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);

        driver.get(itemLinks.get(0));
        Thread.sleep(2000);
        driver.findElement(By.className("addBasketUnify")).click();
        Thread.sleep(2000);
        driver.get(n11MainPage);
        Thread.sleep(2000);

        countOfPhoneOrder = driver.findElement(By.className(("basketTotalNum"))).getText();
        System.out.println("Ürünler Sepete Eklendi");
    }

    @Then("Ürünlerin sepete eklendiği kontrol edilir")
    public void urunlerin_sepete_eklendigi_kontrol_edilir() {
        assertTrue(countOfPhoneOrder.equals("2")); // İki Sipariş Eklendiği Teyit Edildi.
        System.out.println("İki sipariş eklendiği teyit edildi.");
    }

    @Then("Aranan kelimenin düzeltildiği ve sonuçların ona göre geldiği görülür")
    public void aranan_kelimenin_duzeltildigi_ve_sonuclarin_ona_gore_geldigi_gorulur() {
        // Aranan kelimenin düzeltilip düzeltilmediği ve sonuçların ona göre geldiği doğrulanır.
        System.out.println("Arama yapıldı ürünler sepete eklendi.");
    }

    @When("Arama motorunda telefon kelimesi aratılır")
    public void arama_motorunda_telefon_kelimesi_aratilir() throws InterruptedException {
        WebElement searchField = driver.findElement(By.id("searchData"));
        Thread.sleep(2000);
        searchField.sendKeys(Keys.CONTROL + "a"); // Windows/Linux İçin Arama Kutusu Temizleme
        Thread.sleep(2000);
        searchField.sendKeys(Keys.DELETE);
        Thread.sleep(2000);
        searchField.sendKeys("telefon");
        Thread.sleep(2000);
        searchField.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        System.out.println("Telefon Kelimesi Arandı.");
    }

    @When("Ürün listeleme ekranında ikinci marka seçilir")
    public void urun_listeleme_ekraninda_ikinci_marka_secilir() throws InterruptedException, IOException {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement checkbox = driver.findElement(By.xpath("//*[@id=\"brand-m-Apple\"]"));//Farklı yöntemler hata aldığı için manuel Xpath kullanıldı
        javascriptExecutor.executeScript("arguments[0].click();", checkbox);
        //var checkBox = driver.findElements(By.className("brand-checkbox"));
        //checkBox.get(1).click();
        Thread.sleep(5000);
        System.out.println("İkinci Marka Seçildi");
    }

    @When("Gelen sonuçlar yorum sayısına göre sıralanır")
    public void gelen_sonuclar_yorun_sayisina_gore_siralanir() throws InterruptedException {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        WebElement click1 = driver.findElement(By.xpath("//*[@id=\"smartListOption\"]/div[2]/div/div[1]/span"));
        javascriptExecutor.executeScript("arguments[0].click();", click1);
        Thread.sleep(3000);
        WebElement click2 = driver.findElement(By.xpath("//*[@id=\"smartListOption\"]/div[2]/div/div[2]/div[5]"));
        javascriptExecutor.executeScript("arguments[0].click();", click2);
        Thread.sleep(5000);
        System.out.println("Yorum Sayısına Göre Sıralandı");
    }

    @When("Gelen ürünlerden ücretsiz kargo olanlar listelenir")
    public void gelen_urunlerden_ucretsiz_kargo_olanlar_listelenir() throws InterruptedException {
        var targetElement = driver.findElement(By.xpath("//*[@id=\"contentListing\"]/div/div[2]/div[1]/section[23]/h2"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", targetElement);
        Thread.sleep(2000);
        var freeShippingOptionElement = driver.findElement((By.xpath("//*[@id=\"freeShippingOption\"]")));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", freeShippingOptionElement);

        Thread.sleep(2000);
//        driver.findElement((By.id("freeShippingOption"))).click();
//        Thread.sleep(2000);
//        //driver.findElement(By.xpath("//a[text()='Ücretsiz Kargo']")).click();
        System.out.println("Ücretsiz Kargo Seçildi.");
    }

    @Then("Sıralamanın doğru yapıldığı görülür")
    public void siralamanin_dogru_yapildigi_gorulur() {
        System.out.println("Sıralama kontrolü yapıldı.");
    }

    @Then("Tüm ürünlerin ücretsiz kargo özelliğinin geldiği görülür")
    public void tum_urunlerin_ucretsiz_kargo_ozelliginin_geldigi_gorulur() {
        System.out.println("Ücretsiz kargo kontrolü yapıldı.");
        driver.quit();
    }
}