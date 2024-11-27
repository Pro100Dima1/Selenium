package org.example.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @DisplayName("Проверка поисковой строки в поисковике bing")
    @RepeatedTest(1)
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();
        By locator = By.xpath("//a[contains (@class, 'tilk')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.attributeContains(locator, "aria-label", "Selenium"));
        List<WebElement> href = driver.findElements(locator);
        searchPage(href, 0);
        ArrayList tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1).toString());
        assertEquals("https://www.selenium.dev/", driver.getCurrentUrl(), "Переход не по первой ссылке списка!!!");
    }

    public void searchPage(List<WebElement> href, int num) {
        href.get(num).click();
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.selenium-button selenium-webdriver text-uppercase fw-bold")));
        System.out.println("Нажата первая ссылка из списка");
    }
}
