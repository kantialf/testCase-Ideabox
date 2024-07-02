import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class testDrag {
    static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "\"C:\\Tugas Akhir\\Selenium\\ideaboxTesting\\src\\main\\resources\\chromedriver.exe\"");

        // Memaksimalkan browser
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
    }

    @Test
    public void TC(){

        WebElement drag = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]"));
        WebElement drop = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]"));

        Actions actions = new Actions(driver);
//        actions.clickAndHold(cardIdea).moveToElement(fieldPhase1).perform();
        actions.dragAndDrop(drag, drop).build().perform();
    }
}
