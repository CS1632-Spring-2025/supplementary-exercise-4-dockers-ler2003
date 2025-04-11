package edu.pitt.cs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class D3Test {
  private WebDriver driver;
  private Map<String, Object> vars;

  @Before
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
    driver = new ChromeDriver(options);
    vars = new HashMap<>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void tEST1LINKS() {
    driver.get("http://localhost:8080/");
    assertThat(driver.findElement(By.xpath("//a[contains(@href, '/reset')]")).getText(), is("Reset"));
  }

  @Test
  public void tEST2RESET() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Reset")).click();
    assertTrue(driver.findElements(By.cssSelector("li#cat-id1:first-child")).size() > 0);
    assertTrue(driver.findElements(By.cssSelector("li#cat-id2:nth-child(2)")).size() > 0);
    assertTrue(driver.findElements(By.cssSelector("li#cat-id3:nth-child(3)")).size() > 0);
  }

  @Test
  public void tEST3CATALOG() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Catalog")).click();
    driver.findElement(By.cssSelector("li:nth-child(3) > img")).click();
    assertTrue(driver.findElements(By.cssSelector("li:nth-child(3) > img")).size() > 0);
  }

  @Test
  public void tEST4LISTING() {
    driver.get("http://localhost:8080/");
    vars.put("itemCount", driver.findElements(By.xpath("//li[@class=\"list-group-item\"]")).size());
    assertEquals(vars.get("itemCount").toString(), "3");
    assertTrue(driver.findElements(By.cssSelector("li#cat-id3:nth-child(3)")).size() > 0);
  }

  @Test
  public void tEST5RENTACAT() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    assertThat(driver.findElement(By.cssSelector(".form-group:nth-child(3) .btn")).getText(), is("Rent"));
    assertThat(driver.findElement(By.cssSelector(".form-group:nth-child(4) .btn")).getText(), is("Return"));
  }

  @Test
  public void tEST6RENT() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    driver.findElement(By.id("rentID")).sendKeys("1");
    driver.findElement(By.cssSelector(".form-group:nth-child(3) .btn")).click();
    assertThat(driver.findElement(By.id("cat-id1")).getText(), is("Rented out"));
    assertThat(driver.findElement(By.id("cat-id2")).getText(), is("ID 2. Old Deuteronomy"));
    assertThat(driver.findElement(By.id("cat-id3")).getText(), is("ID 3. Mistoffelees"));
    assertThat(driver.findElement(By.id("rentResult")).getText(), is("Success!"));
  }

  @Test
  public void tEST7RETURN() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    driver.findElement(By.id("returnID")).sendKeys("2");
    driver.findElement(By.cssSelector(".form-group:nth-child(4) .btn")).click();
    assertThat(driver.findElement(By.id("returnResult")).getText(), is("Success!"));
  }

  @Test
  public void tEST8FEEDACAT() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    assertTrue(driver.findElements(By.cssSelector(".btn")).size() > 0);
  }

  @Test
  public void tEST9FEED() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    driver.findElement(By.id("catnips")).sendKeys("6");
    driver.findElement(By.cssSelector(".btn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    wait.until(ExpectedConditions.textToBe(By.id("feedResult"), "Nom, nom, nom."));

    assertThat(driver.findElement(By.id("feedResult")).getText(), is("Nom, nom, nom."));
  }

  @Test
  public void tEST10GREETACAT() {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Greet-A-Cat")).click();
    assertThat(driver.findElement(By.cssSelector("#greeting > h4")).getText(), is("Meow!Meow!Meow!"));
  }

  @Test
  public void tEST11GREETACATWITHNAME() {
    driver.get("http://localhost:8080/greet-a-cat/Jennyanydots");
    assertThat(driver.findElement(By.cssSelector("#greeting > h4")).getText(), is("Meow! from Jennyanydots."));
  }
}