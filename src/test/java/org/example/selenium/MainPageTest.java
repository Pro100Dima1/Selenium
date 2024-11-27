package org.example.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
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
    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();
        WebElement searchButton = driver.findElement(By.cssSelector("h2 > a[h='ID=SERP,5165.2']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.visibilityOf(searchButton));

        List<WebElement> href = driver.findElements(By.cssSelector("h2 > a[href] "));
        searchPage(href,0);

        //WebElement searchPageField = driver.findElement(By.cssSelector("#sb_form_q"));
       // assertEquals(input, searchPageField.getAttribute("value"), "Не нашлося");
        assertEquals("https://www.selenium.dev", driver.getCurrentUrl(), "Переход не по первой ссылке списка!!!");
    }
    public void searchPage(List<WebElement> href, int num){
        href.get(num).click();
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.selenium-button selenium-webdriver text-uppercase fw-bold")));
        System.out.println("Нажата первая ссылка из списка");
    }
}
