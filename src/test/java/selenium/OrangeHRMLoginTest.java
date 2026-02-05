package selenium;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

import ultils.ConfigUtils;

//<input data-v-1f99f73c="" class="oxd-input oxd-input--active" name="username" placeholder="Username" autofocus="">
public class OrangeHRMLoginTest
{
    // Step 1: Set up WebDriver
    private WebDriver driver;

    private WebDriverWait wait;

    // URL OrangeHRM Login Page
//    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    // User name and Password
//    private static final String USERNAME = "Admin";
//    private static final String PASSWORD = "admin123";

    // define elements, locators
//    private static final By USERNAME_INPUT  = By.xpath("//input[@name='username']");
    private static final By USERNAME_INPUT = By.name("username");
//    By.cssSelector("input[name='username']")
//    By.cssSelector("input[placeholder='Username']")

//    private static final By PASSWORD_INPUT  = By.xpath("//input[@name='password']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[name='password']");
    private static final By LOGIN_BTN    = By.xpath("//button[@type='submit']");
//    By.cssSelector("button[type='submit']")
//    By.cssSelector("button.oxd-button--main")
    private static final By USER_DROPDOWN = By.cssSelector("li.oxd-userdropdown");
    private static final By LOGOUT_LINK = By.cssSelector("a[href='/web/index.php/auth/logout']");
//  private static final By LOGOUT_LINK = By.linkText("Logout");

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
        driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(10000); // Pause for 10 seconds to observe the browser
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));

        WebElement usernameField = driver.findElement(USERNAME_INPUT);
        usernameField.sendKeys(ConfigUtils.getUsername());
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);

        WebElement loginButton = driver.findElement(LOGIN_BTN);
        loginButton.click();
//        Thread.sleep(2000);
        wait.until(ExpectedConditions.urlContains("dashboard"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(
                currentUrl.contains("/dashboard"),
                "Login successful, dashboard URL verified."
        );
    }

    @Test(description = "Test Logout Success")
    public void testLogoutSuccess() throws InterruptedException {
        driver.get(ConfigUtils.getLoginUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));

        WebElement usernameField = driver.findElement(USERNAME_INPUT);
        usernameField.sendKeys(ConfigUtils.getUsername());
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);

        WebElement loginButton = driver.findElement(LOGIN_BTN);
        loginButton.click();
        wait.until(ExpectedConditions.urlContains("dashboard"));

        wait.until(ExpectedConditions.elementToBeClickable(USER_DROPDOWN)).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();

        wait.until(ExpectedConditions.urlContains("auth/login"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(
                currentUrl.contains("auth/login"),
                "Logout successful, redirected to login page."
        );

    }
    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
