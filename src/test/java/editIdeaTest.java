import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class editIdeaTest {


    static WebDriver driver;

    public static void wait(int mill){
        try{
            Thread.sleep(mill);
        } catch (Exception e){

        }
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
        driver.get("https://km4.ideaboxapp.com/my-ideas");
        wait(2000);

    }

    @Test
    public void TC_081(){

        System.out.println("Hasil Eksekusi TC_081:");

        //scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,170)");
        wait(2000);
        //button action idea
        WebElement btnActTJ = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/span/button"));
        btnActTJ.click();

        //verifikasi modal dropdown
        wait(1000);
        WebElement mdlActTj = driver.findElement(By.xpath("//*[@id=\"fill-tab-example-tabpane-Active-Ideas\"]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div"));
        if (!mdlActTj.isDisplayed()) {
            System.out.println("[BUG] Dropdown idea tidak muncul.");
        }

        // tombol edit idea
        WebElement btnEdit = driver.findElement(By.xpath("//*[@id=\"fill-tab-example-tabpane-Active-Ideas\"]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/a[2]"));
        btnEdit.click();
        wait(3000);

        // verifikasi data sebelumnya
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));

        try{
            Assert.assertNotNull("[BUG] Data sebelumnya tidak tersimpan", fieldTitle);
            Assert.assertEquals("Design Thinking", menuParentCat1.getText());
            Assert.assertEquals("KFC", menuParentCat2.getText());
        } catch (AssertionError e) {
            System.out.println("[BUG] Data ide sebelumnya tidak tersimpan");
        }

        // title idea
        fieldTitle.sendKeys(" Halo tes");

        // design category
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,100)");
        wait(1000);
        // junkfood category
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
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
        if (currentUrl3.contains("https://km4.ideaboxapp.com/idea/edit")) {
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
        if (currentUrl4.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.contains("https://km4.ideaboxapp.com/idea/edit")) {
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

        js.executeScript("window.scrollBy(0,300)");
        wait(2000);
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
        wait(2000);

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
        howField.sendKeys("How");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("What");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        //button tutorial lean canvas
        WebElement btnTutorLC = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        //scroll to elemen // Gulir ke elemen dengan JavaScript
        Actions actions = new Actions(driver);
        actions.moveToElement(btnTutorLC).perform();
        btnTutorLC.click();
        String urlBtnLc = driver.getCurrentUrl();
        if (urlBtnLc.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman video tutorial Lean Canvas. Actual URL: " + urlBtnLc);
        }
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
        String actualTextPs = fieldPs.getText();
        String expected = "";
        if (!actualTextPs.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks proposed solution gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualTextPs));
        }
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // verifikasi data di tabel member
        WebElement jmlMemberTable = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[1]/div/div/p"));
        String jmlMembActual = jmlMemberTable.getText();
        String jmlMembExp = "2 Member";
        if (!jmlMembActual.contains("2")){
            System.out.println(String.format("[BUG] Data member di tabel Team Member tidak muncul. Jumlah seharusnya: %s, Teks sebenarnya: %s.", jmlMembExp, jmlMembActual));
        }

        try{
            WebElement delMember = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/div/div[2]"));
            delMember.click();
        } catch (Exception e) {
            System.out.println("[BUG] Tidak ada tombol delete pada tabel Team Member.");
        }

//        WebElement modalDelete = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
//        if (!modalDelete.isDisplayed()) {
//            System.out.println("[BUG] Modal delete member tidak tampil.");
//        }
//
//        WebElement btnDelMem = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
//        btnDelMem.click();
//
//        WebElement alertSucDel = null;
//        try {
//            alertSucDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
//
//            // Memeriksa apakah alert ditampilkan dan memiliki teks tertentu
//            String actualText3 = alertSucDel.getText();
//            String expected3 = "Success Delete Team Member";
//            if (!alertSucDel.isDisplayed() || !alertSucDel.equals(expected3)) {
//                System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected3, actualText3));
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("[BUG] Alert tidak tampil.");
//        }

//        wait(3000);
//        // data ari di tabel
//        WebElement tblMemb = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody"));
//        if (tblMemb.getText().contains("Ari")) {
//            System.out.println("[BUG] Member belum terhapus.");
//        }

        wait(1000);
        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 8);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected1 = "AMAThonn";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit pada modal Invite Member gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected1, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Hustler");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[2]/table/tbody/tr"));

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

        try{
            WebElement successDraft = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
            String actualText2 = successDraft.getText();
            String expectedText2 = "Ide Berhasil Disimpan ke Draft";
            if (!actualText2.equals(expectedText2)){
                System.out.println(String.format("[BUG] Verifikasi teks alert sukses draft gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText2, actualText2));
            }
        } catch (Exception e) {
            System.out.println("[BUG] Alert sukses draft tidak muncul.");
        }

        wait(2000);
        String currentUrl7 = driver.getCurrentUrl();
        String expUrl = "https://km4.ideaboxapp.com/my-ideas?tab=draft";
        if (!currentUrl7.equals(expUrl)) {
            System.out.println(String.format("[BUG] Halaman tidak mengarah ke tab Drafts dan Data gagal diperbarui. Url seharusnya: %s, Url sebenarnya: %s.", expUrl, currentUrl7));
        }

    }

    @Test
    public void TC_083(){
        System.out.println("Hasil Eksekusi TC_083:");

        //button tab draft
        WebElement btnTabDraft = driver.findElement(By.id("fill-tab-example-tab-Drafts"));
        btnTabDraft.click();

        //scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)");
        wait(2000);

        //button action idea
        WebElement btnActTJ = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/span/button"));
        JavascriptExecutor executorBtnAct = (JavascriptExecutor) driver;
        executorBtnAct.executeScript("arguments[0].click();", btnActTJ);

        // modal dropdown
        wait(2000);
        WebElement mdlActTj = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/div"));
        if (!mdlActTj.isDisplayed()) {
            System.out.println("[BUG] Dropdown idea tidak muncul.");
        }

        wait(2000);
        // tombol edit idea
        WebElement btnEdit = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/div/a[2]"));
        btnEdit.click();
        wait(3000);

        // verifikasi data sebelumnya
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));

        try{
            Assert.assertNotNull("[BUG] Data sebelumnya tidak tersimpan", fieldTitle);
            Assert.assertEquals("Design Thinking", menuParentCat1.getText());
            Assert.assertEquals("MCD", menuParentCat2.getText());
        } catch (AssertionError e) {
            System.out.println("[BUG] Data ide sebelumnya tidak tersimpan");
        }

        // title idea
        fieldTitle.sendKeys(" Halo tes");

        // design category
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,100)");
        wait(1000);
        // junkfood category
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
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
        if (currentUrl3.contains("https://km4.ideaboxapp.com/idea/edit")) {
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
        if (currentUrl4.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.contains("https://km4.ideaboxapp.com/idea/edit")) {
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

        js.executeScript("window.scrollBy(0,300)");
        wait(2000);
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
        wait(2000);

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
        howField.sendKeys("How");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("What");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        //button tutorial lean canvas
        WebElement btnTutorLC = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        //scroll to elemen // Gulir ke elemen dengan JavaScript
        Actions actions = new Actions(driver);
        actions.moveToElement(btnTutorLC).perform();
        btnTutorLC.click();
        String urlBtnLc = driver.getCurrentUrl();
        if (urlBtnLc.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman video tutorial Lean Canvas. Actual URL: " + urlBtnLc);
        }
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
        String actualTextPs = fieldPs.getText();
        String expected = "";
        if (!actualTextPs.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks proposed solution gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualTextPs));
        }
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // verifikasi data di tabel member
        WebElement jmlMemberTable = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[1]/div/div/p"));
        String jmlMembActual = jmlMemberTable.getText();
        String jmlMembExp = "2 Member";
        if (!jmlMembActual.contains("2")){
            System.out.println(String.format("[BUG] Data member di tabel Team Member tidak muncul. Jumlah seharusnya: %s, Teks sebenarnya: %s.", jmlMembExp, jmlMembActual));
        }

        try{
            WebElement delMember = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/div/div[2]"));
            delMember.click();
        } catch (Exception e) {
            System.out.println("[BUG] Tidak ada tombol delete pada tabel Team Member.");
        }

//        WebElement modalDelete = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
//        if (!modalDelete.isDisplayed()) {
//            System.out.println("[BUG] Modal delete member tidak tampil.");
//        }
//
//        WebElement btnDelMem = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
//        btnDelMem.click();
//
//        WebElement alertSucDel = null;
//        try {
//            alertSucDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
//
//            // Memeriksa apakah alert ditampilkan dan memiliki teks tertentu
//            String actualText3 = alertSucDel.getText();
//            String expected3 = "Success Delete Team Member";
//            if (!alertSucDel.isDisplayed() || !alertSucDel.equals(expected3)) {
//                System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected3, actualText3));
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("[BUG] Alert tidak tampil.");
//        }

//        wait(3000);
//        // data ari di tabel
//        WebElement tblMemb = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody"));
//        if (tblMemb.getText().contains("Ari")) {
//            System.out.println("[BUG] Member belum terhapus.");
//        }

        wait(1000);
        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 8);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected1 = "AMAThonn";
        if (!actualText1.equals(expected1)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit pada modal Invite Member gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected1, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Hustler");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[2]/table/tbody/tr"));

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

        try{
            WebElement successDraft = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
            String actualText2 = successDraft.getText();
            String expectedText2 = "Ide Berhasil Disimpan ke Draft";
            if (!actualText2.equals(expectedText2)){
                System.out.println(String.format("[BUG] Verifikasi teks alert sukses draft gagal. Teks seharusnya: %s, Teks sebenarnya: %s.", expectedText2, actualText2));
            }
        } catch (Exception e) {
            System.out.println("[BUG] Alert sukses draft tidak muncul.");
        }

        wait(2000);
        String currentUrl7 = driver.getCurrentUrl();
        String expUrl = "https://km4.ideaboxapp.com/my-ideas?tab=draft";
        if (!currentUrl7.equals(expUrl)) {
            System.out.println(String.format("[BUG] Halaman tidak mengarah ke tab Drafts dan Data gagal diperbarui. Url seharusnya: %s, Url sebenarnya: %s.", expUrl, currentUrl7));
        }
    }

    @Test
    public void TC_080(){
        System.out.println("Hasil Eksekusi TC_080:");

        //scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,170)");
        wait(2000);
        //button action idea
        WebElement btnActTJ = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[3]/div/div[2]/div/div[2]/div/span/button"));
        btnActTJ.click();

        //verifikasi modal dropdown
        wait(1000);
        WebElement mdlActTj = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[3]/div/div[2]/div/div[2]/div/div"));
        if (!mdlActTj.isDisplayed()) {
            System.out.println("[BUG] Dropdown idea tidak muncul.");
        }

        // tombol edit idea
        WebElement btnEdit = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[3]/div/div[2]/div/div[2]/div/div/a[2]"));
        btnEdit.click();
        wait(3000);

        // verifikasi data sebelumnya
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));

        try{
            Assert.assertNotNull("[BUG] Data sebelumnya tidak tersimpan", fieldTitle);
            Assert.assertEquals("Design Thinking", menuParentCat1.getText());
            Assert.assertEquals("KFC", menuParentCat2.getText());
        } catch (AssertionError e) {
            System.out.println("[BUG] Data ide sebelumnya tidak tersimpan");
        }

        // title idea
        fieldTitle.sendKeys(" Halo tes");

        // design category
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,100)");
        wait(1000);
        // junkfood category
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
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
        if (currentUrl3.contains("https://km4.ideaboxapp.com/idea/edit")) {
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
        if (currentUrl4.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.contains("https://km4.ideaboxapp.com/idea/edit")) {
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

        js.executeScript("window.scrollBy(0,300)");
        wait(2000);
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
        wait(2000);

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
        howField.sendKeys("How");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("What");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        //button tutorial lean canvas
        WebElement btnTutorLC = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        //scroll to elemen // Gulir ke elemen dengan JavaScript
        Actions actions = new Actions(driver);
        actions.moveToElement(btnTutorLC).perform();
        btnTutorLC.click();
        String urlBtnLc = driver.getCurrentUrl();
        if (urlBtnLc.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman video tutorial Lean Canvas. Actual URL: " + urlBtnLc);
        }
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
        String actualTextPs = fieldPs.getText();
        String expected = "";
        if (!actualTextPs.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks proposed solution gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualTextPs));
        }
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // verifikasi data di tabel member
        WebElement jmlMemberTable = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[1]/div/div/p"));
        String jmlMembActual = jmlMemberTable.getText();
        String jmlMembExp = "2 Member";
        if (!jmlMembActual.contains("2")){
            System.out.println(String.format("[BUG] Data member di tabel Team Member tidak muncul. Jumlah seharusnya: %s, Teks sebenarnya: %s.", jmlMembExp, jmlMembActual));
        }

        try{
            WebElement delMember = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/div/div[2]"));
            delMember.click();
        } catch (Exception e) {
            System.out.println("[BUG] Tidak ada tombol delete pada tabel Team Member.");
        }

//        WebElement modalDelete = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
//        if (!modalDelete.isDisplayed()) {
//            System.out.println("[BUG] Modal delete member tidak tampil.");
//        }
//
//        WebElement btnDelMem = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
//        btnDelMem.click();
//
//        WebElement alertSucDel = null;
//        try {
//            alertSucDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
//
//            // Memeriksa apakah alert ditampilkan dan memiliki teks tertentu
//            String actualText3 = alertSucDel.getText();
//            String expected3 = "Success Delete Team Member";
//            if (!alertSucDel.isDisplayed() || !alertSucDel.equals(expected3)) {
//                System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected3, actualText3));
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("[BUG] Alert tidak tampil.");
//        }

//        wait(3000);
//        // data ari di tabel
//        WebElement tblMemb = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody"));
//        if (tblMemb.getText().contains("Ari")) {
//            System.out.println("[BUG] Member belum terhapus.");
//        }

        wait(1000);
        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 8);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected1 = "AMAThonn";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit pada modal Invite Member gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected1, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Hustler");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[2]/table/tbody/tr"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        // add member kedua
        buttonInvite.click();
        // dropdown nama
        WebElement menuParentCat7 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown7 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat7, inputDropdown7, 2);
        WebElement selectTeam2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        Select select2 = new Select(selectTeam2);
        select2.selectByVisibleText("Hustler");

        WebElement buttonConfirm2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm2.click();

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

        try{
            WebElement successPublish = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        } catch (Exception e) {
            System.out.println("[BUG] Alert success publish tidak tampil.");
        }

        // memastikan data terupdate
        WebElement judulBaruIde = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]/div/div[2]/div/div[2]/h4"));
        String judulBaru = judulBaruIde.getText();
        if (!judulBaru.equals("Halo tes")){
            System.out.println("[BUG] Data baru ide tidak tersimpan dan terbarui.");
        }

        wait(3000);
        String currentUrl7 = driver.getCurrentUrl();
        if (!currentUrl7.contains("https://km4.ideaboxapp.com/home")) {
            System.out.println("[BUG] Halaman tidak mengarah ke HomePage.");
        }

    }

    @Test
    public void TC_082(){

        System.out.println("Hasil Eksekusi TC_082:");

        //button tab draft
        WebElement btnTabDraft = driver.findElement(By.id("fill-tab-example-tab-Drafts"));
        btnTabDraft.click();
        wait(1000);

        //scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,170)");
        wait(2000);
        //button action idea
        WebElement btnActTJ = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/span/button"));
        JavascriptExecutor executorBtnAct = (JavascriptExecutor) driver;
        executorBtnAct.executeScript("arguments[0].click();", btnActTJ);

        // modal dropdown
        wait(2000);
        WebElement mdlActTj = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/div"));
        if (!mdlActTj.isDisplayed()) {
            System.out.println("[BUG] Dropdown idea tidak muncul.");
        }

        wait(2000);
        // tombol edit idea
        WebElement btnEdit = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/div/a[2]"));
        btnEdit.click();
        wait(3000);

        // verifikasi data sebelumnya
        WebElement fieldTitle = driver.findElement(By.name("desc[0].value"));
        WebElement menuParentCat1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div"));
        WebElement menuParentCat2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div"));

        try{
            Assert.assertNotNull("[BUG] Data sebelumnya tidak tersimpan", fieldTitle);
            Assert.assertEquals("Design Thinking", menuParentCat1.getText());
            Assert.assertEquals("KFC", menuParentCat2.getText());
        } catch (AssertionError e) {
            System.out.println("[BUG] Data ide sebelumnya tidak tersimpan");
        }

        // title idea
        fieldTitle.sendKeys("Tes draft jadi active idea");

        // design category
        WebElement inputDropdown1 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat1, inputDropdown1, 1);
        wait(2000);

        //verifikasi button similar category design
        WebElement btnSiDesign = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[4]/td[2]/div/div[2]/button"));
        btnSiDesign.click();
        String currentUrl1 = driver.getCurrentUrl();
        if (currentUrl1.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori design tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,100)");
        wait(1000);
        // junkfood category
        WebElement inputDropdown2 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat2, inputDropdown2, 1);

        wait(2000);

        //verifikasi button similar category junk food
        WebElement btnSiJunk = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[5]/td[2]/div/div[2]/button"));
        btnSiJunk.click();
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori junk food tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
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
        if (currentUrl3.contains("https://km4.ideaboxapp.com/idea/edit")) {
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
        if (currentUrl4.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Button similar idea kategori blockchain tidak berfungsi.");
        }

        js.executeScript("window.scrollBy(0,200)");
        wait(2000);
        // binatang category
        WebElement menuParentCat5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]"));
        WebElement inputDropdown5 = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[1]/div/div/div/div[1]/div[1]/div[2]/input"));
        clickDropdown(menuParentCat5, inputDropdown5, 1);

        wait(2000);

        //verifikasi button similar category binatang
        WebElement btnSiBntg = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/div/table/tbody/tr[10]/td[2]/div/div[2]/button"));
        btnSiBntg.click();
        String currentUrl5 = driver.getCurrentUrl();
        if (currentUrl5.contains("https://km4.ideaboxapp.com/idea/edit")) {
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

        js.executeScript("window.scrollBy(0,300)");
        wait(2000);
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
        wait(2000);

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
        howField.sendKeys("How");

        WebElement whatField = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[3]/form/table/tbody/tr[9]/td[2]/div/div[2]/div[1]"));
        whatField.sendKeys("What");

        WebElement buttonNext2 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext2.click();

        //create idea - section leancanvas

        //button tutorial lean canvas
        WebElement btnTutorLC = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        //scroll to elemen // Gulir ke elemen dengan JavaScript
        Actions actions = new Actions(driver);
        actions.moveToElement(btnTutorLC).perform();
        btnTutorLC.click();
        String urlBtnLc = driver.getCurrentUrl();
        if (urlBtnLc.contains("https://km4.ideaboxapp.com/idea/edit")) {
            System.out.println("[BUG] Halaman tidak mengarah ke halaman video tutorial Lean Canvas. Actual URL: " + urlBtnLc);
        }
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
        String actualTextPs = fieldPs.getText();
        String expected = "";
        if (!actualTextPs.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks proposed solution gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected, actualTextPs));
        }
        fieldPs.sendKeys("Proposed solution");

        WebElement buttonNext3 = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/button[2]"));
        buttonNext3.click();

        // verifikasi data di tabel member
        WebElement jmlMemberTable = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[1]/div/div/p"));
        String jmlMembActual = jmlMemberTable.getText();
        String jmlMembExp = "2 Member";
        if (!jmlMembActual.contains("2")){
            System.out.println(String.format("[BUG] Data member di tabel Team Member tidak muncul. Jumlah seharusnya: %s, Teks sebenarnya: %s.", jmlMembExp, jmlMembActual));
        }

        try{
            WebElement delMember = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/div/div[2]"));
            delMember.click();
        } catch (Exception e) {
            System.out.println("[BUG] Tidak ada tombol delete pada tabel Team Member.");
        }

//        WebElement modalDelete = driver.findElement(By.xpath("/html/body/div[3]/div/div"));
//        if (!modalDelete.isDisplayed()) {
//            System.out.println("[BUG] Modal delete member tidak tampil.");
//        }
//
//        WebElement btnDelMem = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/button[2]"));
//        btnDelMem.click();
//
//        WebElement alertSucDel = null;
//        try {
//            alertSucDel = driver.findElement(By.xpath("/html/body/div/div[2]/div"));
//
//            // Memeriksa apakah alert ditampilkan dan memiliki teks tertentu
//            String actualText3 = alertSucDel.getText();
//            String expected3 = "Success Delete Team Member";
//            if (!alertSucDel.isDisplayed() || !alertSucDel.equals(expected3)) {
//                System.out.println(String.format("[BUG] Verifikasi teks gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected3, actualText3));
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("[BUG] Alert tidak tampil.");
//        }

//        wait(3000);
//        // data ari di tabel
//        WebElement tblMemb = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/table/tbody"));
//        if (tblMemb.getText().contains("Ari")) {
//            System.out.println("[BUG] Member belum terhapus.");
//        }

        wait(1000);
        // button invite teams
        WebElement buttonInvite = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div[2]/div[1]/button"));
        buttonInvite.click();

        // dropdown nama
        WebElement menuParentCat6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown6 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat6, inputDropdown6, 8);


        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[5]/td[2]/div/div/div[1]/textarea"));
        String actualText1 = element.getText();
        String expected1 = "AMAThonn";
        if (!actualText1.equals(expected)){
            System.out.println(String.format("[BUG] Verifikasi teks Unit pada modal Invite Member gagal. Teks seharusnya: %s, Teks sebenarnya: %s", expected1, actualText1));
        }

        // dropdown team structure
        WebElement selectTeam = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        // Create a Select object based on the <select> element
        Select select = new Select(selectTeam);
        select.selectByVisibleText("Hustler");

        WebElement buttonConfirm = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm.click();

        //verifikasi alert sukses invite team
        WebElement successInvite = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        if (!successInvite.isDisplayed()) {
            System.out.println("[BUG] Alert sukses tidak tampil.");
        }

        //verifikasi member yang diinvite tampil di tabel
        try {
            WebElement memberBaru = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[3]/div[2]/div[2]/table/tbody/tr"));

            // Memeriksa apakah elemen ditampilkan dan memiliki teks tertentu
            if (!memberBaru.isDisplayed() || !memberBaru.getText().contains("Inactive")) {
                System.out.println("[BUG] Status member bukan inactive.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("[BUG] Member yang diinvite tidak tampil di tabel Team Member.");
        }

        // add member kedua
        buttonInvite.click();
        // dropdown nama
        WebElement menuParentCat7 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div"));
        WebElement inputDropdown7 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[1]/td[2]/div/div/div/div[1]/div[2]/input"));
        wait(2000);
        clickDropdown(menuParentCat7, inputDropdown7, 2);
        WebElement selectTeam2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/table/tbody/tr[4]/td[2]/div/div/div[1]/select"));
        Select select2 = new Select(selectTeam2);
        select2.selectByVisibleText("Hustler");

        WebElement buttonConfirm2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[4]/div/button[2]"));
        buttonConfirm2.click();

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

        try{
            WebElement successPublish = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]"));
        } catch (Exception e) {
            System.out.println("[BUG] Alert success publish tidak tampil.");
        }

        // memastikan data terupdate
        WebElement fieldActiveIdea = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/nav/div/div/div[1]/div[2]"));
        String judulBaru = fieldActiveIdea.getText();
        if (!judulBaru.contains("Tes draft jadi active idea")){
            System.out.println("[BUG] Data baru ide tidak tersimpan dan terbarui.");
        }

        wait(3000);
        String currentUrl7 = driver.getCurrentUrl();
        if (!currentUrl7.contains("https://km4.ideaboxapp.com/home")) {
            System.out.println("[BUG] Halaman tidak mengarah ke HomePage.");
        }
    }

}

