package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private final String homePageUrl = "https://useinsider.com/";

    // Menüler
    private final By companyMenu = By.xpath("(//a[contains(text(), 'Company')])");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final By careersMenuItem = By.xpath("//a[@href='https://useinsider.com/careers/']");

    // Sayfayı aç
    public void openHomePage() {
        driver.get(homePageUrl);
    }

    // URL doğrulama
    public boolean isAtHomePage() {
        return driver.getCurrentUrl().equalsIgnoreCase(homePageUrl);
    }

    // Company menüsüne hover ve Careers'a tıkla
    public void goToCareersPage() {
        driver.get(homePageUrl);
        click(driver.findElement(companyMenu));
        click(driver.findElement(careersMenuItem));
    }
}
