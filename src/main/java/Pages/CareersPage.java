package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {

    // Blokların başlıkları (optimize edilmiş locator'lar)
    private final By locationsSection = By.xpath("//h3[contains(text(),'Locations')]");
    private final By teamsSection = By.xpath("//h3[contains(text(),'Find your calling')]");
    private final By lifeAtInsiderSection = By.xpath("//h2[contains(text(),'Life at Insider')]");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLocationsSectionVisible() {
        return isDisplayed(driver.findElement(locationsSection));
    }

    public boolean isTeamsSectionVisible() {
        return isDisplayed(driver.findElement(teamsSection));
    }

    public boolean isLifeAtInsiderSectionVisible() {
        return isDisplayed(driver.findElement(lifeAtInsiderSection));
    }

    // QA sayfasına geçiş
    public void goToQAPage() {

        driver.get("https://useinsider.com/careers/quality-assurance/");
    }
}
