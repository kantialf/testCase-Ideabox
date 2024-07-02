import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class createIdeaTest {

    static WebDriver driver;

    public static void wait(int mill){
        try{
            Thread.sleep(mill);
        } catch (Exception e){

        }
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static String getOuter(WebElement element){
        return (String)((JavascriptExecutor)driver).executeScript("return arguments[0].outerHTML;", element);

    }
//    public static Map<String, List<String>> getDropDownDetails(WebElement parent, WebElement dropdown){
//        List<WebElement> suggestionValues = parent.findElements(By.xpath("//div[@class='suggestion']"));
//        List<WebElement> optionValues = parent.findElements(By.xpath("//div[@role='option']"));
//        List<WebElement> theRealValues = new ArrayList<>();
//        if (!suggestionValues.isEmpty()){
//            theRealValues = suggestionValues;
//        }else{
//            theRealValues = optionValues;
//        }
//        Map<String, List<String>> allOptions = new HashMap<>();
//        for (WebElement optionElement : theRealValues){
//            String mainValue = optionElement.getAttribute("innerHTML");
//            System.out.println("mainValue: "+mainValue);
//            List<WebElement> allDetails = optionElement.findElements(By.tagName("p"));
//            System.out.println("count all details: "+String.valueOf(allDetails.size()));
//            List<String> allDetailStrings = new ArrayList<>();
//            for (WebElement detailElement: allDetails){
//                String detailString = detailElement.getAttribute("innerHTML");
//                System.out.println("detail String: "+detailString);
//                allDetailStrings.add(detailString);
//            }
//            allOptions.put(mainValue, allDetailStrings);
//        }
//        return allOptions;
//    }
    public static void clickDropdown(WebElement parent, WebElement dropdown, int pilihan){

        dropdown.click();
        wait(1000);

        List<WebElement> suggestionValues = parent.findElements(By.xpath("//div[@class='suggestion']"));
        List<WebElement> optionValues = parent.findElements(By.xpath("//div[@role='option']"));
        List<WebElement> theRealValues = new ArrayList<>();
        if (!suggestionValues.isEmpty()){
            theRealValues = suggestionValues;
        }else{
            theRealValues = optionValues;
        }
        theRealValues.get(pilihan).click();
    }

    //set up
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

        emailField.sendKeys("alifakanti@gmail.com");
        passwordFiled.sendKeys("12345678");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div/div/div[2]/div/div/form/div[4]/button[1]"));
        loginButton.click();


        wait(2000);
        driver.get("https://km4.ideaboxapp.com/idea/create");
        wait(2000);

    }


    @Test
    public void TC_062(){

        //create idea publish and join event
        System.out.println("Hasil Eksekusi TC_062:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 1);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "AMATechss";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        // button publish idea
        WebElement btnPublish = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        btnPublish.click();

        wait(2000);
        WebElement modalPublish = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalPublish.isDisplayed()){
            System.out.println("[BUG] Modal konfirmasi publish tidak muncul");
        }

        WebElement btnPublishJoin = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/div/button[1]"));
        btnPublishJoin.click();

        wait(5000);
        WebElement successPublish = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        String actualText2 = successPublish.getText();
        String expectedText2 = "Idemu sukses diunggah";
        if (!actualText2.equals(expectedText2)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expected, actualText2));
        }

        //verifikasi url setelah klik button publish and join event
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://km4.ideaboxapp.com/events"; // URL yang diharapkan
        if (!actualUrl.equals(expectedUrl)) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman event. Actual URL: " + actualUrl);
        }

        System.out.println("------------------------------------------------------------");

    }

    @Test
    public void TC_063(){
        System.out.println("Hasil Eksekusi TC_063:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 1);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "AMATechss";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        // button publish idea
        WebElement btnPublish = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        btnPublish.click();

        wait(2000);
        WebElement modalPublish = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalPublish.isDisplayed()){
            System.out.println("[BUG] Modal konfirmasi publish tidak muncul");
        }

        WebElement btnPublishIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/div/button[2]"));
        btnPublishIdea.click();

        wait(5000);
        WebElement successPublishIdea = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        String actualText2 = successPublishIdea.getText();
        String expectedText2 = "Idemu sukses diunggah";
        if (!actualText2.equals(expectedText2)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expected, actualText2));
        }

        //verifikasi url setelah klik button publish
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://km4.ideaboxapp.com/my-ideas"; // URL yang diharapkan
        if (!actualUrl.equals(expectedUrl)) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman my idea. Actual URL: " + actualUrl);
        }

        System.out.println("------------------------------------------------------------");

        driver.quit();
    }

    @Test
    public void TC_067() {
        System.out.println("Hasil Eksekusi TC_067:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 0);

        //verifikasi apakah veda terpilih atau tidak
        WebElement fieldName = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div"));
        if (fieldName.getText().contains("Veda")){
            System.out.println("[BUG] Verifikasi invite member yang melengkapi profil gagal.");
        }

        WebElement modalInvite = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalInvite.getText().contains("Veda tidak bisa diundang karena belum melengkapi profil.")){
            System.out.println("[BUG] Peringatan user tidak bisa diinvite tidak ada.");
        }

        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "";
        if (!actualText1.equals(expected)) {
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        if (buttonConfirm.isEnabled()) {
            System.out.println("[BUG] Button bisa diklik dan tidak disable.");
        }
        driver.quit();
    }

    @Test
    public void TC_068(){

        System.out.println("Hasil Eksekusi TC_068:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);
        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 1);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "AMATechss";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses invite team tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        WebElement editMember = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/div/div[1]"));
        editMember.click();

        WebElement modalInvite = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalInvite.isDisplayed()){
            System.out.println("[BUG] Modal invite user tidak tampil.");
        }

        // dropdown team structure
        WebElement selectTeam2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select2 = new Select(selectTeam2);
        select2.selectByVisibleText("AmaApayaaa");

        WebElement buttonConfirm2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm2.click();

        WebElement successEditMember = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successEditMember.isDisplayed()){
            System.out.println("[BUG] Alert sukses edit tidak muncul.");
        }

        driver.quit();
    }

    @Test
    public void TC_069() {

        System.out.println("Hasil Eksekusi TC_069:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 1);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "AMATechss";
        if (!actualText1.equals(expected)) {
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses invite team tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        WebElement memberBaru = null;
        try {
            memberBaru = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        WebElement deleteMember = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/div/div[2]"));
        deleteMember.click();

        WebElement modalDelete = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDelete.isDisplayed()) {
            System.out.println("[BUG] Modal delete member tidak tampil.");
        }

        WebElement btnDelMem = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnDelMem.click();

        WebElement alertSucDel = null;
        try {
            alertSucDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));

            // Memeriksa apakah alert ditampilkan dan memiliki teks tertentu
            String actualText3 = alertSucDel.getText();
            String expected3 = "Success Delete Team Member";
            if (!alertSucDel.isDisplayed() || !alertSucDel.equals(expected3)) {
                System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected3, actualText3));
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Alert tidak tampil.");
        }

        if (memberBaru.isDisplayed()) {
            System.out.println("[BUG] Member belum terhapus.");
        }

        driver.quit();
    }

    @Test
    public void TC_073(){
        System.out.println("Hasil Eksekusi TC_073:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 1);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "AMATechss";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        // button save as draft
        WebElement btnDraft = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[1]"));
        btnDraft.click();

        wait(2000);
        WebElement modalDraft = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDraft.isDisplayed()){
            System.out.println("[BUG] Modal konfirmasi draft tidak muncul");
        }

        WebElement btnDraft2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnDraft2.click();

        WebElement successDraft = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        String actualText2 = successDraft.getText();
        String expectedText2 = "Ide Berhasil Disimpan ke Draft";
        if (!actualText2.equals(expectedText2)){
            System.out.println(String.format("[BUG] Verifikasi teks alert sukses draft gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText2, actualText2));
        }

        wait(3000);
        String currentUrl7 = driver.getCurrentUrl();
        if (!currentUrl7.equals("https://km4.ideaboxapp.com/my-ideas?tab=draft")) {
            System.out.println("[BUG] Halaman tidak mengarah ke tab Drafts.");
        }
        System.out.println("------------------------------------------------------------");
    }

    @Test
    public void TC_070(){
        // Membuat idea dengan mengisi 3 mandatory field (Title Idea, Idea Category, dan Idea Detail) dan Save as Draft

        System.out.println("Hasil Eksekusi TC_070:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // button save as draft
        WebElement btnDraft = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[1]"));
        btnDraft.click();

        wait(2000);
        WebElement modalDraft = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDraft.isDisplayed()){
            System.out.println("[BUG] Modal konfirmasi draft tidak muncul");
        }

        WebElement btnDraft2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnDraft2.click();

        WebElement successDraft = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        String actualText2 = successDraft.getText();
        String expectedText2 = "Ide Berhasil Disimpan ke Draft";
        if (!actualText2.equals(expectedText2)){
            System.out.println(String.format("[BUG] Verifikasi teks alert sukses draft gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText2, actualText2));
        }

        wait(3000);
        String currentUrl7 = driver.getCurrentUrl();
        if (!currentUrl7.equals("https://km4.ideaboxapp.com/my-ideas?tab=draft")) {
            System.out.println("[BUG] Halaman tidak mengarah ke tab Drafts.");
        }
        System.out.println("------------------------------------------------------------");
    }

    @Test
    public void TC_072(){
        //Membuat idea dengan Save as Draft di section Lean Canvas
        System.out.println("Hasil Eksekusi TC_072:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Why");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // klik button save as draft tanpa mengisi semua kolom
        WebElement btnDraft = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[1]"));
        try {
            // Cobalah untuk mengklik tombol tersebut
            btnDraft.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("[BUG] Tombol Save as Draft tidak bisa diklik");
        }

//        wait(2000);
//        try {
//            // Cari elemen modal draft
//            WebElement modalDraft = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
//
//            // Coba untuk memeriksa apakah modal draft ditampilkan
//            if (modalDraft.isDisplayed()) {
//                System.out.println("Modal draft ditampilkan.");
//            }
//        } catch (NoSuchElementException e) {
//            // Tangani jika elemen tidak ditemukan
//            System.out.println("[BUG] Modal konfirmasi draft tidak muncul");
//        }

        System.out.println("------------------------------------------------------------");
    }

    @Test
    public void TC_071(){
        //Membuat idea dengan Save as Draft di section Golden Circle
        System.out.println("Hasil Eksekusi TC_072:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Why");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Why");

        WebElement btnDraft = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[1]"));
        try {
            // Cobalah untuk mengklik tombol tersebut
            btnDraft.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("[BUG] Tombol Save as Draft tidak bisa diklik");
        }
        wait(2000);
        WebElement modalDraft = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalDraft.isDisplayed()){
            System.out.println("[BUG] Modal konfirmasi draft tidak muncul");
        }

        WebElement btnDraft2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
        btnDraft2.click();

        WebElement successDraft = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        String actualText2 = successDraft.getText();
        String expectedText2 = "Ide Berhasil Disimpan ke Draft";
        if (!actualText2.equals(expectedText2)){
            System.out.println(String.format("[BUG] Verifikasi teks alert sukses draft gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText2, actualText2));
        }

        wait(3000);
        String currentUrl7 = driver.getCurrentUrl();
        if (!currentUrl7.equals("https://km4.ideaboxapp.com/my-ideas?tab=draft")) {
            System.out.println("[BUG] Halaman tidak mengarah ke tab Drafts.");
        }
        System.out.println("------------------------------------------------------------");
    }

    @Test
    public void TC_065(){

        //Menguji nilai inputan di bawah batas untuk setiap field yang mempunyai batas maksimal untuk Create Idea
        System.out.println("Hasil Eksekusi TC_065:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaa");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        String filePath2 = "C:/Users/Kanti Alifa/OneDrive/Gambar/gambarPdf.pdf";
        uploadAddAttch.sendKeys(filePath2);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaa");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaa");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaa");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        // field Problem
        WebElement fieldProblem = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldProblem.sendKeys("p1");

        WebElement addFieldP = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor3 = (JavascriptExecutor) driver;
        executor3.executeScript("arguments[0].click();", addFieldP);

        WebElement fieldProblem2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldProblem2.sendKeys("p2");

        // field existing solution
        WebElement fieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[1]/div/div/div[1]/input"));
        fieldEs.sendKeys("es1");

        WebElement addFieldEs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/p"));
        JavascriptExecutor executor4 = (JavascriptExecutor) driver;
        executor4.executeScript("arguments[0].click();", addFieldEs);

        WebElement fieldEs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/input"));
        fieldEs2.sendKeys("es2");

        // field existing solution
        WebElement fieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[1]/div/div/div[1]/input"));
        fieldEa.sendKeys("ea1");

        WebElement addFieldEa = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/p"));
        JavascriptExecutor executor5 = (JavascriptExecutor) driver;
        executor5.executeScript("arguments[0].click();", addFieldEa);

        WebElement fieldEa2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[2]/div[5]/div[2]/div/div/div[1]/input"));
        fieldEa2.sendKeys("ea2");

        // field customer segment
        WebElement fieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[1]/div/div/div[1]/input"));
        fieldCs.sendKeys("Cs1");

        WebElement addFieldCs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/p"));
        JavascriptExecutor executor6 = (JavascriptExecutor) driver;
        executor6.executeScript("arguments[0].click();", addFieldCs);

        WebElement fieldCs2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[1]/div[3]/div[2]/div[2]/div/div/div[1]/input"));
        fieldCs2.sendKeys("Cs2");

        // field Unique Value propotion
        WebElement fieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[1]/input"));
        fieldUvp.sendKeys("Uvp1");

        WebElement addFieldUvp = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/p"));
        JavascriptExecutor executor7 = (JavascriptExecutor) driver;
        executor7.executeScript("arguments[0].click();", addFieldUvp);

        WebElement fieldUvp2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/input"));
        fieldUvp2.sendKeys("Uvp2");

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaa");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 1);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected = "AMATechss";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Region I Surabayaa");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        // button publish idea
        WebElement btnPublish = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        btnPublish.click();

        wait(2000);
        WebElement modalPublish = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        if (!modalPublish.isDisplayed()){
            System.out.println("[BUG] Modal konfirmasi publish tidak muncul");
        }

        WebElement btnPublishIdea = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/div/button[2]"));
        btnPublishIdea.click();

        wait(5000);
        WebElement successPublishIdea = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
        String actualText2 = successPublishIdea.getText();
        String expectedText2 = "Idemu sukses diunggah";
        if (!actualText2.equals(expectedText2)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expected, actualText2));
        }

        //verifikasi url setelah klik button publish
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://km4.ideaboxapp.com/my-ideas"; // URL yang diharapkan
        if (!actualUrl.equals(expectedUrl)) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman my idea. Actual URL: " + actualUrl);
        }

        System.out.println("------------------------------------------------------------");

    }

    @Test
    public void TC_064(){
        //Membuat Idea dengan mengosongkan mandatory field (Title Idea, Idea Category, dan Idea Detail)

        System.out.println("Hasil Eksekusi TC_064:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
//        clickDropdown(menuParentCat1, inputDropdown1, 1);
//        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
//        btnSiDesign.click();
//        String currentUrl1 = driver.getCurrentUrl();
//        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
//            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
//        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
//        clickDropdown(menuParentCat2, inputDropdown2, 1);

//        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
//        btnSiJunk.click();
//        String currentUrl2 = driver.getCurrentUrl();
//        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
//            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
//        }

//        // Idea Cover
//        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView();", uploadCover);
//        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
//        uploadCover.sendKeys(filePath);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
//        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));
//
//        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

//        // detail idea
//        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
//        detailField.sendKeys("");

        //periksa apakah button save and next disable
        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        // Periksa apakah tombol dinonaktifkan
        if (buttonNext1.getAttribute("disabled") != null) {
        } else {
            System.out.println("[BUG] Tombol Save and Next masih bisa diklik.");
        }

        //periksa apakah button save as draft disable
        WebElement btnDraftDisable = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[1]"));
        if (btnDraftDisable.getAttribute("disabled") != null) {
        } else {
            System.out.println("[BUG] Tombol Save as Draft masih bisa diklik.");
        }

//        WebElement fieldCreate = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]"));
//        if (!fieldCreate.getText().contains("Kolom tidak boleh kosong")){
//            System.out.println("[BUG] Tidak ada message error kolom kosong");
//        }

        System.out.println("------------------------------------------------------------");
    }

    @Test
    public void TC_074() {
        //Mengklik tombol Cancel ketika sedang membuat idea
        System.out.println("Hasil Eksekusi TC_074:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        uploadAddAttch.sendKeys(filePath);
        wait(10000);

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement btnCancel = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[1]/div/button"));
        btnCancel.click();

        wait(3000);
        String urlCancel = driver.getCurrentUrl();
        if (!urlCancel.equals("https://km4.ideaboxapp.com/home?search=")) {
            System.out.println("[BUG] Halaman tidak mengarah ke homepage.");
        }

    }

    @Test
    public void TC_066(){

        //Menguji nilai inputan di atas batas untuk setiap field yang mempunyai batas maksimal
        System.out.println("Hasil Eksekusi TC_066:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji2MB.jpg";
        uploadCover.sendKeys(filePath);

        //verify error message file melebihi batas
        wait(2000);
        WebElement errorMessage = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/span"));
        Assert.assertTrue("[BUG] Error message image cover tidak tampil", errorMessage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaass");

//        wait(8000);
//        //verifikasi error message
//        WebElement errorMessage2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/p"));
//        Assert.assertTrue("[BUG] Error message deskripsi idea tidak tampil", errorMessage2.isDisplayed());

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        // add field url
        WebElement addField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/p"));
        JavascriptExecutor executor2 = (JavascriptExecutor) driver;
        executor2.executeScript("arguments[0].click();", addField);

        // field url additional attachment
        WebElement urlField2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[2]/div/div/div[1]/input"));
        urlField2.sendKeys("https://dummy.com//");

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        String filePath2 = "C:/Users/Kanti Alifa/OneDrive/Gambar/17MB.pdf";
        uploadAddAttch.sendKeys(filePath2);
        wait(1000);

        //verify error message file melebihi batas
        WebElement errorMessage3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/span"));
        Assert.assertTrue("[BUG] Error message additional attachment tidak tampil", errorMessage3.isDisplayed());

        // verifikasi deskripsi file
        WebElement successUpload = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[5]/div/div/div"));
        if (!successUpload.getText().contains("Deskripsi File")) {
            System.out.println("[BUG] Field deskripsi file additional attachment tidak ada.");
        }

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        System.out.println("------------------------------------------------------------");

    }

    @Test
    public void TC_066_2() {
        System.out.println("Hasil Eksekusi TC_066:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaaaa");

        //verifikasi error message
        WebElement errorMessage1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[6]/td[2]"));
        try{
            Assert.assertTrue("[BUG] Error message di field Why tidak muncul", errorMessage1.getText().contains("Input lebih dari 600 karakter"));
        }
        catch (java.lang.AssertionError e){
            System.out.println("[BUG] Error message di field Why tidak muncul");
        }

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaaaa");

        //verifikasi error message
        WebElement errorMessage2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[6]/td[2]"));
        try{
            Assert.assertTrue("[BUG] Error message di field Why tidak muncul", errorMessage2.getText().contains("Input lebih dari 600 karakter"));
        }
        catch (java.lang.AssertionError e){
            System.out.println("[BUG] Error message di field How tidak muncul");
        }

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaaaa");

        //verifikasi error message
        WebElement errorMessage3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[10]/td[2]"));
        try{
            Assert.assertTrue("[BUG] Error message di field What tidak muncul", errorMessage3.getText().contains("Input lebih dari 600 karakter"));
        }
        catch (java.lang.AssertionError e){
            System.out.println("[BUG] Error message di field How tidak muncul");
        }

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

    }

    @Test
    public void TC_066_3() {
        System.out.println("Hasil Eksekusi TC_066:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("Halo tes");

        // design category
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        // junkfood category
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        // other category
        WebElement btnOtherCat = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[7]/td/div/div[1]/p"));
        btnOtherCat.click();

        // management category
        WebElement menuParentCat3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat3, inputDropdown3, 1);

        wait(2000);

        //verifikasi button similar category management
        WebElement btnSiMng = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[8]/td[2]/div/div[2]/button"));
        btnSiMng.click();
        String currentUrl3 = driver.getCurrentUrl();
        if (currentUrl3.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori management tidak berfungsi.");
        }

        // blockchain category
        WebElement menuParentCat4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat4, inputDropdown4, 0);

        wait(2000);

        //verifikasi button similar category blockchain
        WebElement btnSiBc = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/button"));
        btnSiBc.click();
        String currentUrl4 = driver.getCurrentUrl();
        if (currentUrl4.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.equals("https://km4.ideaboxapp.com/idea/create")) {
            System.out.println("[BUG] Button similar idea kategori binatang tidak berfungsi.");
        }

        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/seiji.jpg";
        uploadCover.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        WebElement uploadedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[12]/td[2]/div/div[1]/div/span/img")));

        Assert.assertTrue("Uploaded image is displayed", uploadedImage.isDisplayed());

        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[14]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("detail");

        // checkbox join
        WebElement cbJoin = driver.findElement(By.className("checkbox-radius"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cbJoin);
        // Verify if the checkbox is checked
        boolean isChecked = cbJoin.isSelected();
        // Assertion to check if the checkbox is checked
        Assert.assertTrue("Checkbox is not checked", isChecked);

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("https://dummy.com//");

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext1.click();

        //create idea - section story behind
        WebElement whyField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[1]/td[2]/div/div[2]/div[1]"));
        whyField.sendKeys("depan");

        WebElement howField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[5]/td[2]/div/div[2]/div[1]"));
        howField.sendKeys("aaaa");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("aaa");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //field proposed solution
        WebElement fieldPs = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[1]/textarea"));
        fieldPs.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaaaa");

        //verify error message
        WebElement errorMessage1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div[2]/div[2]/div[2]/div/div[2]"));
        try{
            Assert.assertTrue("[BUG] Error message di field Proposed Solution tidak muncul", errorMessage1.getText().contains("Input lebih dari 600 karakter"));
        }
        catch (java.lang.AssertionError e){
            System.out.println("[BUG] Error message di field Proposed Solution tidak muncul");
        }
    }

    @Test
    public void TC_075() {
        System.out.println("Hasil Eksekusi TC_075:");
        // title idea
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        fieldTitle.sendKeys("");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,550)"); // You can adjust the scroll value based on your need

        wait(2000);
        // Idea Cover
        WebElement uploadCover = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/div/input"));
        String filePath = "C:/Users/Kanti Alifa/OneDrive/Gambar/gambarPdf.pdf";
        uploadCover.sendKeys(filePath);

        //verify error message file melebihi batas
        wait(2000);
        WebElement errorMessage = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[9]/td[2]/div/div[2]/span"));
        Assert.assertTrue("[BUG] Error message image cover tidak tampil", errorMessage.getText().contains("Jenis file tidak sesuai"));

        js.executeScript("window.scrollBy(0,400)");
        wait(5000);
        // detail idea
        WebElement detailField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[11]/td[2]/div/div[2]/div[1]"));
        detailField.sendKeys("Kota-kota besar di seluruh dunia tidak hanya dikenal karena infrastruktur modern dan gedung pencakar langitnya, tetapi juga karena budaya dan sejarah yang kaya. Dari Tokyo dengan neon gemerlapnya, Paris yang romantis dengan Menara Eiffel yang ikonik, hingga New York yang dinamis dengan Times Square yang selalu ramai, masing-masing kota menawarkan pengalaman unik bagi wisatawan. Di sisi lain, kota-kota ini juga menghadapi tantangan besar seperti polusi, kemacetan, dan kebutuhan akan solusi hijau untuk keberlanjutan di masa depan depan.depan.depan.depan.depan.depan.depan.depan.depan.depan.deaaass");

        // field url additional attachment
        WebElement urlField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/div/div/div[1]/input"));
        urlField.sendKeys("dummy");

        //verify error message
        WebElement errorMessage2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[3]/div[1]/p"));
        Assert.assertTrue("[BUG] Error message additional attachment tidak muncul", errorMessage2.getText().contains("Url is not valid"));

        //upload file additional attachment
        WebElement uploadAddAttch = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/div[4]/input"));
        String filePath2 = "C:/Users/Kanti Alifa/OneDrive/Gambar/17MB.pdf";
        uploadAddAttch.sendKeys(filePath2);
        wait(1000);

        //verify error message file melebihi batas
        WebElement errorMessage3 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/span"));
        Assert.assertTrue("[BUG] Error message additional attachment tidak tampil", errorMessage3.isDisplayed());

        WebElement buttonNext1 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonNext1);

        wait(2000);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        wait(1000);
        // error message title idea
        WebElement errorMessage4 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[2]/td[2]"));
        Assert.assertTrue("[BUG] Error message title idea tidak muncul", errorMessage4.getText().contains("Kolom tidak boleh kosong"));

        // error message kategori ide 1
        WebElement errorMessage5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/span"));
        Assert.assertTrue("[BUG] Error message idea category 1 tidak muncul", errorMessage5.getText().contains("Kolom tidak boleh kosong"));

        // error message kategori ide 2
        WebElement errorMessage6 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/span"));
        Assert.assertTrue("[BUG] Error message idea category 2 tidak muncul", errorMessage6.getText().contains("Kolom tidak boleh kosong"));

        // error message idea detail
        WebElement errorMessage7 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/span"));
        Assert.assertTrue("[BUG] Error message idea detail tidak muncul", errorMessage7.getText().contains("Kolom tidak boleh kosong"));

    }

}
