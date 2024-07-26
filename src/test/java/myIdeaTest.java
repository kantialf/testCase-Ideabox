import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class myIdeaTest {
    static WebDriver driver;

    public static void wait(int mill) {
        try {
            Thread.sleep(mill);
        } catch (Exception e) {

        }
    }

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "\"C:\\Tugas Akhir\\Selenium\\ideaboxTesting\\src\\main\\resources\\chromedriver.exe\"");

        // Memaksimalkan browser
        driver.manage().window().maximize();

        driver.get("https://km4.ideaboxapp.com/");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        //login
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordFiled = driver.findElement(By.name("password"));

        emailField.sendKeys("innovator@example.com");
        passwordFiled.sendKeys("12345678");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div/div/div[2]/div/div/form/div[4]/button[1]"));
        loginButton.click();

        wait(2000);
        driver.get("https://km4.ideaboxapp.com/my-ideas");
    }

    @Test
    public void TC_084() {

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[4]/div/div[2]/div/div[2]/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        //scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,70)");
        wait(2000);
        WebElement btnDelete = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[4]/div/div[2]/div/div[2]/div/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnDelete);

        //verify modal delete
        WebElement modalDel = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDel.isDisplayed()) {
            System.out.println("[BUG] Modal delete tidak muncul");
        }

        //button delete di modal
        WebElement btnDelIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnDelIdea.click();

        wait(2000);
        try{
            WebElement alertSuccessDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        } catch (Exception e) {
            System.out.println("[BUG] Alert sukses delete tidak muncul");
        }

        //memastikan idea yang dihapus hilang dari field active idea
        WebElement fieldIdea = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]"));
        if (fieldIdea.getText().contains("Coba")) {
            System.out.println("[BUG] Ide tidak terhapus dari active idea");
        }

        
    }

    @Test
    public void TC_085() {

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[1]/div/div[2]/div/div[2]/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        WebElement btnDelete = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[1]/div/div[2]/div/div[2]/div/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnDelete);

        //verify modal delete
        WebElement modalDel = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDel.isDisplayed()) {
            System.out.println("[BUG] Modal delete tidak muncul");
        }

        //button delete di modal
        WebElement btnCancelDel = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[1]"));
        btnCancelDel.click();
        

    }

    @Test
    public void TC_086() {

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        //button manage talent approval
        WebElement btnMTA = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/a[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnMTA);

        //verify halaman manage talent approval
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/talent-approval/detail")) {
            System.out.println("[BUG] Halaman tidak mengarah ke manage talent approval");
        }

        
    }

    @Test
    public void TC_087() {

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[1]/div/div[2]/div/div[2]/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        //klik card idea
        WebElement cardIdea = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[1]/div/div[2]/h4"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cardIdea);

        wait(3000);
        //verify halaman detail ide
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/idea/detail")) {
            System.out.println("[BUG] Halaman tidak mengarah ke detail idea");
        }

        
    }

    @Test
    public void TC_088() {

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[7]/div/div[2]/div/div[2]/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        //button request to leave
        WebElement btnReqLeave = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[7]/div/div[2]/div/div[2]/div/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnReqLeave);

        //verify modal
        WebElement modalReqLeave = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalReqLeave.isDisplayed()) {
            System.out.println("[BUG] Modal request leave tidak muncul");
        }

        //field alasan
        WebElement fieldReason = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[1]/div/div/div[2]/div/div[1]/textarea"));
        fieldReason.sendKeys("mau leave");

        //button send
        WebElement btnSend = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnSend.click();

        wait(1000);

        //alert sukses send
        WebElement alertSuccessSend = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        String actualText = alertSuccessSend.getText();
        String expected2 = "Permintaanmu untuk keluar sedang diproses. Mohon tunggu persetujuan pemilik ide";
        if (!actualText.equals(expected2)) {
            System.out.println(String.format("[BUG] Verifikasi teks alert sukses send gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected2, actualText));
        }

        
    }

    @Test
    public void TC_089() {

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[7]/div/div[2]/div/div[2]/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        //button request to leave
        WebElement btnReqLeave = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[7]/div/div[2]/div/div[2]/div/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnReqLeave);

        //verify modal
        WebElement modalReqLeave = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalReqLeave.isDisplayed()) {
            System.out.println("[BUG] Modal request leave tidak muncul");
        }

        //field alasan
        WebElement fieldReason = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[1]/div/div/div[2]/div/div[1]/textarea"));
        fieldReason.sendKeys("mau leave");

        //button cancel
        WebElement btnCancel = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[1]"));
        btnCancel.click();

        wait(1000);

        
    }

    @Test
    public void TC_090() {

        //button draft tab
        WebElement btnDrafTab = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/ul/li[2]/button"));
        btnDrafTab.click();

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        WebElement btnDelete = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnDelete);

        //verify modal delete
        WebElement modalDel = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDel.isDisplayed()) {
            System.out.println("[BUG] Modal delete tidak muncul");
        }

        //button delete di modal
        WebElement btnDelIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnDelIdea.click();

        wait(2000);
        try{
            WebElement alertSuccessDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        } catch (Exception e) {
            System.out.println("[BUG] Alert sukses delete tidak muncul");
        }

        //memastikan idea yang dihapus hilang dari field draft idea
        WebElement fieldIdea = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]"));
        if (fieldIdea.getText().contains("Draft 3")) {
            System.out.println("[BUG] Ide tidak terhapus dari tab draft");
        }

        
    }

    @Test
    public void TC_091() {

        //button draft tab
        WebElement btnDrafTab = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/ul/li[2]/button"));
        btnDrafTab.click();

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        WebElement btnDelete = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnDelete);

        //verify modal delete
        WebElement modalDel = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDel.isDisplayed()) {
            System.out.println("[BUG] Modal delete tidak muncul");
        }

        //button delete di modal
        WebElement btnCancel = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[1]"));
        btnCancel.click();
        

//        wait(2000);
//        //verify modal delete masih muncul
//        if (modalDel.isDisplayed()){
//            System.out.println("[BUG] Modal delete masih muncul");
//        }
    }

    @Test
    public void TC_092() {

        //button draft tab
        WebElement btnDrafTab = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/ul/li[2]/button"));
        btnDrafTab.click();

        //action button idea
        WebElement btnAct = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/span/button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAct);

        //button manage talent approval
        WebElement btnMTA = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div/a[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnMTA);

        //verify halaman manage talent approval
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/talent-approval/detail")) {
            System.out.println("[BUG] Halaman tidak mengarah ke manage talent approval");
        }
        
        
        
    }
}
