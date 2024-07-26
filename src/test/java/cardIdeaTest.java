import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class cardIdeaTest {
    static WebDriver driver;

    public static void wait(int mill){
        try{
            Thread.sleep(mill);
        } catch (Exception e){

        }
    }

    @BeforeClass
    public static void setUp(){
        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "\"C:\\Tugas Akhir\\Selenium\\ideaboxTesting\\src\\main\\resources\\chromedriver.exe\"");

        // Memaksimalkan browser
        driver.manage().window().maximize();

        driver.get("https://km4.ideaboxapp.com/");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        //login
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordFiled = driver.findElement(By.name("password"));

        emailField.sendKeys("novianto778@gmail.com");
        passwordFiled.sendKeys("12345678");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div/div/div[2]/div/div/form/div[4]/button[1]"));
        loginButton.click();

        wait(2000);
    }

    @Test
    public void TC_036(){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,4500)");
        wait(2000);
        //button join idea di card idea
        WebElement btnJoinCard = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div[3]/div/div/div[10]/div/div[1]/div/div[2]/a/div"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", btnJoinCard);

        wait(2000);
        //cek url detail idea
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/idea/detail")){
            System.out.println("[BUG] Halaman tidak mengarah ke idea detail");
        }

        //button join this idea
        WebElement btnJoin = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[3]/div/div/div[1]/button"));
        btnJoin.click();

        //verify modal join
        try {
            // Cari elemen
            WebElement modalJoin = driver.findElement(By.xpath("/html/body/div[3]/div/div"));

            // Verifikasi elemen ada
            Assert.assertTrue("Elemen tidak ditemukan", modalJoin.isDisplayed());
        } catch (NoSuchElementException e) {
            // Jika elemen tidak ditemukan, kita bisa menggunakan assertFalse
            Assert.fail("Elemen tidak ditemukan: " + e.getMessage());
        }

        //dropdown Role
        WebElement dropdownRole = driver.findElement(By.name("roleMember"));
        Select dropdown = new Select(dropdownRole);
        dropdown.selectByVisibleText("Hustler");

        wait(1000);
        //verify field role
        WebElement selectedOption = dropdown.getFirstSelectedOption();
        String selectedText = selectedOption.getText();

        // Periksa dan cetak hasil verifikasi
        if (selectedText.equals("Hustler")) {
            System.out.println("Opsi 'Hustler' berhasil terpilih.");
        } else {
            System.out.println("[BUG] Opsi 'Hustler' tidak terpilih. Teks yang sebenarnya: " + selectedText);
        }

        //field alasan
        WebElement fieldReason = driver.findElement(By.name("reason"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='ingin bergabung';", fieldReason);
//        fieldReason.sendKeys("inginbergabung");

        //button join idea di modal
        WebElement btnJoinIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[3]/div[2]/button"));
        try {
            // Cobalah untuk mengklik tombol tersebut
            btnJoinIdea.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("[BUG] Tombol Join Idea disabled karena ada field yang belum terisi");
        }

    }

    @Test
    public void TC_037() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,4500)");
        wait(2000);
        //button join idea di card idea
        WebElement btnJoinCard = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div[3]/div/div/div[10]/div/div[1]/div/div[2]/a/div"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", btnJoinCard);

        wait(2000);
        //cek url detail idea
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/idea/detail")){
            System.out.println("[BUG] Halaman tidak mengarah ke idea detail");
        }

        //button join this idea
        WebElement btnJoin = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[3]/div/div/div[1]/button"));
        btnJoin.click();

        //verify modal join
        try {
            // Cari elemen
            WebElement modalJoin = driver.findElement(By.xpath("/html/body/div[3]/div/div"));

            // Verifikasi elemen ada
            Assert.assertTrue("Elemen tidak ditemukan", modalJoin.isDisplayed());
        } catch (NoSuchElementException e) {
            // Jika elemen tidak ditemukan, kita bisa menggunakan assertFalse
            Assert.fail("Elemen tidak ditemukan: " + e.getMessage());
        }

        //dropdown Role
        WebElement dropdownRole = driver.findElement(By.name("roleMember"));
        Select dropdown = new Select(dropdownRole);
        dropdown.selectByVisibleText("Hustler");

        wait(1000);
        //verify field role
        WebElement selectedOption = dropdown.getFirstSelectedOption();
        String selectedText = selectedOption.getText();

        // Periksa dan cetak hasil verifikasi
        if (selectedText.equals("Hustler")) {
            System.out.println("Opsi 'Hustler' berhasil terpilih.");
        } else {
            System.out.println("[BUG] Opsi 'Hustler' tidak terpilih. Teks yang sebenarnya: " + selectedText);
        }

        //field alasan
        WebElement fieldReason = driver.findElement(By.name("reason"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='ingin bergabung';", fieldReason);

        //button cancel di modal
        WebElement btnCancel = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[3]/div[1]/button"));
        btnCancel.click();

        wait(2000);

        //verify url
        String currentUrl2 = driver.getCurrentUrl();
        if (!currentUrl2.contains("https://km4.ideaboxapp.com/idea/detail")) {
            System.out.println("[BUG] Halaman tidak mengarah ke idea detail");
        }
    }

    @Test
    public void TC_038() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,4500)");
        wait(2000);
        //button join idea di card idea
        WebElement btnJoinCard = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div[3]/div/div/div[10]/div/div[1]/div/div[2]/a/div"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", btnJoinCard);

        wait(2000);
        //cek url detail idea
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/idea/detail")){
            System.out.println("[BUG] Halaman tidak mengarah ke idea detail");
        }

        //button join this idea
        WebElement btnJoin = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[3]/div/div/div[1]/button"));
        btnJoin.click();

        //verify modal join
        try {
            // Cari elemen
            WebElement modalJoin = driver.findElement(By.xpath("/html/body/div[3]/div/div"));

            // Verifikasi elemen ada
            Assert.assertTrue("Elemen tidak ditemukan", modalJoin.isDisplayed());
        } catch (NoSuchElementException e) {
            // Jika elemen tidak ditemukan, kita bisa menggunakan assertFalse
            Assert.fail("Elemen tidak ditemukan: " + e.getMessage());
        }

        //button join idea di modal
        WebElement btnJoinIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[3]/div[2]/button"));
        if (btnJoinIdea.getAttribute("disabled") != null){
        }
        else{
            System.out.println("[BUG] Tombol masih bisa diklik ");
        }
    }

    @Test
    public void TC_039() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,4500)");
        wait(2000);
        //button join idea di card idea
        WebElement btnJoinCard = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div[3]/div/div/div[10]/div/div[1]/div/div[2]/a/div"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", btnJoinCard);

        wait(2000);
        //cek url detail idea
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://km4.ideaboxapp.com/idea/detail")){
            System.out.println("[BUG] Halaman tidak mengarah ke idea detail");
        }

        //button join this idea
        WebElement btnJoin = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[3]/div/div/div[1]/button"));
        btnJoin.click();

        //verify modal join
        try {
            // Cari elemen
            WebElement modalJoin = driver.findElement(By.xpath("/html/body/div[3]/div/div"));

            // Verifikasi elemen ada
            Assert.assertTrue("Elemen tidak ditemukan", modalJoin.isDisplayed());
        } catch (NoSuchElementException e) {
            // Jika elemen tidak ditemukan, kita bisa menggunakan assertFalse
            Assert.fail("Elemen tidak ditemukan: " + e.getMessage());
        }

        //dropdown Role
        WebElement dropdownRole = driver.findElement(By.name("roleMember"));
        Select dropdown = new Select(dropdownRole);
        dropdown.selectByVisibleText("Hustler");

        wait(1000);
        //verify field role
        WebElement selectedOption = dropdown.getFirstSelectedOption();
        String selectedText = selectedOption.getText();

        // Periksa dan cetak hasil verifikasi
        if (selectedText.equals("Hustler")) {
            System.out.println("Opsi 'Hustler' berhasil terpilih.");
        } else {
            System.out.println("[BUG] Opsi 'Hustler' tidak terpilih. Teks yang sebenarnya: " + selectedText);
        }

        //field alasan
        WebElement fieldReason = driver.findElement(By.name("reason"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='ingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganung terus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungterus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungterus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungterus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungterus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungterus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganungterus berganungingin bergabung terus bergabung terus berganungingin bergabung terus bergabung terus berganung';", fieldReason);

        //button join idea di modal
        WebElement btnJoinIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[3]/div[2]/button"));
        if (btnJoinIdea.getAttribute("disabled") != null){
        }
        else{
            System.out.println("[BUG] Tombol masih bisa diklik ");
        }
    }

    @Test
    public void TC_040(){
        driver.get("https://km4.ideaboxapp.com/idea/detail/259");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("[BUG] Halaman tidak mengarah ke detail idea", currentUrl.contains("https://km4.ideaboxapp.com/idea/detail"));

        //button cancel join
        WebElement btnCancelJoin = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div/div/div/div[3]/div/div/div[1]/button"));
        btnCancelJoin.click();

        WebElement popUpCancel = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!popUpCancel.getText().contains("Batal bergabung dengan Ide:")){

            System.out.println("[BUG] Pop up cancel join tidak menampilkan teks “Batal bergabung dengan Ide:\"");
        }
        //btn cancel join di pop up
        WebElement btnCancelJoin2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div/button[2]"));
        try{
            btnCancelJoin2.click();
        }
        catch (Exception e){
            System.out.println("[BUG] Button Cancel Join tidak bisa diklik");
        }
    }

    @Test
    public void TC_041(){
        driver.get("https://km4.ideaboxapp.com/idea/detail/259");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("[BUG] Halaman tidak mengarah ke detail idea", currentUrl.contains("https://km4.ideaboxapp.com/idea/detail"));

        //button cancel join
        WebElement btnCancelJoin = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div/div/div/div[3]/div/div/div[1]/button"));
        btnCancelJoin.click();

        WebElement popUpCancel = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!popUpCancel.getText().contains("Batal bergabung dengan Ide:")){

            System.out.println("[BUG] Pop up cancel join tidak menampilkan teks “Batal bergabung dengan Ide:\"");
        }
        //btn cancel di pop up
        WebElement btnCancel = driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div/button[1]"));
        try{
            btnCancel.click();
        }
        catch (Exception e){
            System.out.println("[BUG] Button Cancel Join tidak bisa diklik");
        }

        wait(1000);
        // Verifikasi apakah elemen masih ada setelah tindakan
        try {
            driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        } catch (Exception e) {
        }
        driver.quit();
    }

}
