package selenium;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

//<input data-v-1f99f73c="" class="oxd-input oxd-input--active" name="username" placeholder="Username" autofocus="">
public class OrangeHRMLoginTest
{
    // Step 1: Set up WebDriver
    private WebDriver driver;

    private WebDriverWait wait;

    // URL OrangeHRM Login Page
    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    // User name and Password
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "admin123";

    // Set up testing environment
    // Before Method: run before each test case
    @BeforeMethod
    public void setUp(){
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        // Configure ChroemmeDriver options
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized"); // Start browser maximized
        options.addArguments("--disable-notifications"); // Disable notifications
        options.addArguments("--disable-infobars"); // Disable infobars

        driver = new ChromeDriver(options);

        // time to Setup WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(description = "Test Login Success")
    public void testLoginSucess() throws InterruptedException {
        driver.get(LOGIN_URL);
        Thread.sleep(10000); // Pause for 10 seconds to observe the browser

        WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
        usernameField.sendKeys(USERNAME);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys(PASSWORD);
        Thread.sleep(2000);

        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(
                currentUrl.contains("/dashboard"),
                "Login successful, dashboard URL verified."
        );
    }

    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
