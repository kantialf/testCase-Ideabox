import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class eventManageTest {

    static WebDriver driver;
    static WebDriverWait wait1;

    public static void wait(int mill) {
        try {
            Thread.sleep(mill);
        } catch (Exception e) {

        }
    }

    //ekstraksi text ke number
    private static int extractNumberFromText(String text) {
        // Use regular expression to find the number in the text
        String numberString = text.replaceAll("[^0-9]", "");
        // Convert the string to an integer
        return Integer.parseInt(numberString);
    }

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "\"C:\\Tugas Akhir\\Selenium\\ideaboxTesting\\src\\main\\resources\\chromedriver.exe\"");

        // Memaksimalkan browser
        driver.manage().window().maximize();

        driver.get("https://km4.ideaboxapp.com/");
        wait(5000);

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        //login
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordFiled = driver.findElement(By.name("password"));

        emailField.sendKeys("admin@example.com");
        passwordFiled.sendKeys("12345678");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div/div/div[2]/div/div/form/div[4]/button[1]"));
        loginButton.click();

        wait(2000);

        WebElement btnAdmin = driver.findElement(By.xpath("/html/body/div/div/nav/div/div/div[1]/div/a[5]"));
        btnAdmin.click();

        //klik menu event management
        WebElement btnEventMng = driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div/a[2]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnEventMng);


    }

    @Test
    public void TC_138() {
        //Mengecek fungsi drag and drop idea ke fase event

        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        wait(2000);
        //card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]"));

        // field fase 1
        WebElement fieldPhase1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]"));

        wait(1000);
        // Ngambil data jumlah ide awal fase 2
        WebElement totalIdeField2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[1]/div[1]/span"));
        String textTotalIde2 = totalIdeField2.getText();
        int oldNumber2 = extractNumberFromText(textTotalIde2);

        // Ngambil data jumlah ide awal fase 3
        WebElement totalIdeField3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[1]/span"));
        String textTotalIde3 = totalIdeField3.getText();
        int oldNumber3 = extractNumberFromText(textTotalIde3);

        wait(3000);
        //drag n drop
        Actions actions = new Actions(driver);
        actions.clickAndHold(cardIdea).perform();
        wait(1000);
        actions.moveToElement(fieldPhase1).perform();
        actions.release(cardIdea).perform();

        //        actions.release(fieldPhase1).perform();
        wait(2000);
        // Ngambil data jumlah ide fase 2 setelah
        String newText2 = totalIdeField2.getText();
        int newNumber2 = extractNumberFromText(newText2);

        // Ngambil data jumlah ide fase 3 setelah
        String newText3 = totalIdeField3.getText();
        int newNumber3 = extractNumberFromText(newText3);

        // Verify
        Assert.assertTrue("[BUG] Total idea di Phase asal tidak berkurang.", oldNumber2 > newNumber2);
        Assert.assertTrue("[BUG] Total idea di Phase tujuan tidak bertambah.", oldNumber3 < newNumber3);

        //verify alert sukses drag and drop
        WebElement alertSuccessDrop = driver.findElement(By.xpath("/html/body/div[1]/div[2]"));
        if (!alertSuccessDrop.isDisplayed()){
            System.out.println("[BUG] Alert sukses drag and drop tidak muncul");
        }
        String actualText3 = alertSuccessDrop.getText();
        String expectedText3 = "Berhasil Mengupdate Fase Ide";
        if (!expectedText3.equals(actualText3)) {
            System.out.println(String.format("[BUG] Teks pada alert sukses drag and drop ide salah. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText3, actualText3));
        }
        driver.quit();
    }

    @Test
    public void TC_139() {
        //Mengecek fungsi drag and drop idea ke luar field fase event

        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        wait(3000);
        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        //card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]"));

        // field luar
        WebElement fieldLuar = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/a/div"));

        // Ngambil data jumlah ide awal fase 2
        WebElement totalIdeField2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[1]/div[1]/span"));
        String textTotalIde2 = totalIdeField2.getText();
        int oldNumber2 = extractNumberFromText(textTotalIde2);

        //drag n drop
        Actions actions = new Actions(driver);
        actions.clickAndHold(cardIdea).perform();
        wait(1000);
        actions.moveToElement(fieldLuar).perform();
        actions.release(cardIdea).perform();

        wait(2000);
        // Ngambil data jumlah ide fase 2 setelah
        String newText2 = totalIdeField2.getText();
        int newNumber2 = extractNumberFromText(newText2);

        Assert.assertTrue("[BUG] Total idea di Phase 2 bertambah/berkurang.", oldNumber2 == newNumber2);

        //verify alert error message drag and drop
        WebElement alertSuccessDrop = driver.findElement(By.xpath("/html/body/div[1]/div[2]"));
        if (!alertSuccessDrop.isDisplayed()){
            System.out.println("[BUG] Alert sukses drag and drop tidak muncul");
        }
        String actualText = alertSuccessDrop.getText();
        String expectedText = "Failed to move the idea";
        if (!expectedText.equals(actualText)) {
            System.out.println(String.format("[BUG] Teks pada alert error drag and drop ide salah. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText, actualText));
        }
        driver.quit();
    }

    @Test
    public void TC_140() {
        //Mengecek fungsi drag and drop beberapa idea sekaligus dengan checkbox pada idea yang berada di fase yang sama

        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        wait(3000);
        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        wait(2000);
        //card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]"));

        // field fase 1
        WebElement fieldPhase1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]"));

        //checkbox element
        WebElement cb1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]/div/div[1]/input"));
        cb1.click();

        //checkbox element
        WebElement cb2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[3]/div/div[1]/input"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cb2);
//        cb2.click();

        wait(1000);
        //drag n drop
        Actions actions = new Actions(driver);

        actions.clickAndHold(cardIdea).perform();
        wait(1000);
        actions.moveToElement(fieldPhase1).perform();
//        actions.release(fieldPhase1).perform();
        actions.release(cardIdea).perform();

        wait(1000);
        //verify alert sukses drag and drop
        WebElement alertSuccessDrop = driver.findElement(By.xpath("/html/body/div[1]/div[2]"));
        if (!alertSuccessDrop.isDisplayed()){
            System.out.println("[BUG] Alert sukses drag and drop tidak muncul");
        }
        String actualText3 = alertSuccessDrop.getText();
        String expectedText3 = "Berhasil Mengupdate Fase Ide";
        if (!expectedText3.equals(actualText3)) {
            System.out.println(String.format("[BUG] Teks pada alert sukses drag and drop ide salah. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText3, actualText3));
        }
        driver.quit();
    }

    @Test
    public void TC_141() {
        //Mengecek fungsi drag and drop beberapa idea sekaligus dengan checkbox pada idea yang berada di fase berbeda

        wait(2000);
        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        wait(3000);
        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        wait(2000);
        //card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]"));

        // field luar
        WebElement fieldLuar = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/a/div"));

        //checkbox element
        WebElement cb1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]/div/div[1]/input"));
        cb1.click();

        //checkbox card idea berbeda fase
        WebElement cb2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]/div[2]/div/div[1]/input"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cb2);
//        cb2.click();

        wait(1000);
        //drag n drop
        Actions actions = new Actions(driver);

        actions.clickAndHold(cardIdea).perform();
        wait(1000);
        actions.moveToElement(fieldLuar).perform();
//        actions.release(fieldPhase1).perform();
        actions.release(cardIdea).perform();

        wait(2000);
        //verify alert error message drag and drop
        WebElement alertSuccessDrop = driver.findElement(By.xpath("/html/body/div[1]/div[2]"));
        if (!alertSuccessDrop.isDisplayed()) {
            System.out.println("[BUG] Alert sukses drag and drop tidak muncul");
        }
        String actualText = alertSuccessDrop.getText();
        String expectedText = "Cannot move items with different phase";
        if (!expectedText.equals(actualText)) {
            System.out.println(String.format("[BUG] Teks pada alert error drag and drop ide salah. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText, actualText));
        }
    }

    @Test
    public void TC_142() {
        //Mengubah event phase name

        wait(2000);
        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        wait(3000);
        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        wait(2000);
        //card idea
        WebElement btnActCard = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]/div[1]/div[2]/span/button"));
        btnActCard.click();

        // button edit phase
        WebElement btnEditPhase = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]/div[1]/div[2]/div/button[1]"));
        WebElement fieldPhaseName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]/div[1]/div[1]/div/div/div/div[1]/input"));

        String textBefore = fieldPhaseName.getText();
        btnEditPhase.click();

        //inputan phase name
        fieldPhaseName.sendKeys(" Phase 1");
        fieldPhaseName.sendKeys(Keys.ENTER);

        String textAfter = fieldPhaseName.getText();

        if (textAfter == textBefore){
            System.out.println("[BUG] Event Phase Name tidak terubah");
        }

        wait(1000);
        WebElement alertSuccess = driver.findElement(By.xpath("/html/body/div[1]/div[2]"));
        if (!alertSuccess.isDisplayed()){
            System.out.println("[BUG] Alert sukses ubah nama event phase tidak muncul.");
        }
        String actualText = alertSuccess.getText();
        String expectedText = "Success Updated";
        if (!expectedText.equals(actualText)) {
            System.out.println(String.format("[BUG] Teks pada alert sukses update salah. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText, actualText));
        }
    }

    @Test
    public void TC_143() {
        //Mengunci event phase

        wait(2000);
        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        wait(2000);
        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        wait(2000);
        //card idea
        WebElement btnActCard = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[2]/span/button"));
        btnActCard.click();

        //ngambil data jumlah ide awal fase 2
        WebElement totalIdeField3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[1]/span"));
        String textTotalIde = totalIdeField3.getText();
        Integer intTotalIde = extractNumberFromText(textTotalIde);

        // button lock phase
        WebElement btnLockPhase = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[2]/div/button[2]"));
        btnLockPhase.click();

        //card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]"));

        // field fase 1
        WebElement fieldPhase3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]"));

        //drag n drop
        Actions actions = new Actions(driver);

        try {
            actions.clickAndHold(cardIdea).perform();
            wait(1000);
            actions.moveToElement(fieldPhase3).perform();
//        actions.release(fieldPhase1).perform();
            actions.release(cardIdea).perform();
        } finally {

        }
        //ngambil data jumlah ide fase 2 setelah
        String newText = totalIdeField3.getText();
        int newNumber = extractNumberFromText(newText);

        //verify
        Assert.assertTrue("[BUG] Total idea di Phase asal bertambah/berkurang.", newNumber==intTotalIde);

        driver.quit();
    }

    @Test
    public void TC_144() {
        //Membuka kunci event phase

        wait(2000);
        //verify element event management
        WebElement btnAddEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a"));
        WebElement cardEvent = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/a/div"));

        wait(2000);
        if (!btnAddEvent.isDisplayed()) {
            System.out.println("[BUG] Button add event tidak ada");
        }
        if (!cardEvent.isDisplayed()) {
            System.out.println("[BUG] Card event tidak ada");
        }

        //button action event
        WebElement btnActEvnt = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/span/button"));
        btnActEvnt.click();

        //button manage event phase
        WebElement btnManage = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[2]/div/div/div/div/a[2]"));
        btnManage.click();

        wait(2000);
        //card idea
        WebElement btnActCard = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[2]/span/button"));
        btnActCard.click();

        //ngambil data jumlah ide awal fase 2
        WebElement totalIdeField3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[1]/span"));
        String textTotalIde = totalIdeField3.getText();
        Integer intTotalIde = extractNumberFromText(textTotalIde);

        // button lock phase
        WebElement btnLockPhase = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[3]/div[1]/div[2]/div/button[2]"));
        btnLockPhase.click();

        wait(2000);
        // Klik unlock
        btnActCard.click();
        btnLockPhase.click();

        //card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[2]/div[2]"));

        // field fase 1
        WebElement fieldPhase1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div/div/div[3]/div/div/div[1]"));

        //drag n drop
        Actions actions = new Actions(driver);

        actions.clickAndHold(cardIdea).perform();
        wait(1000);
        actions.moveToElement(fieldPhase1).perform();
//        actions.release(fieldPhase1).perform();
        actions.release(cardIdea).perform();

        //ngambil data jumlah ide fase 2 setelah
        String newText = totalIdeField3.getText();
        // Extract the new number
        int newNumber = extractNumberFromText(newText);

        //verify
        // Compare the initial and new numbers
        if (newNumber == intTotalIde) {
            System.out.println("[BUG] Total idea di Phase asal tidak berkurang.");
        }

    }
}
