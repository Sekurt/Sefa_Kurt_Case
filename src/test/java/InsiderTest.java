import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import Pages.CareersPage;
import Pages.HomePage;
import Pages.QAJobsPage;
import Utils.DriverWeb;

public class InsiderTest {

    WebDriver driver;
    HomePage homePage;
    CareersPage careersPage;
    QAJobsPage qaJobsPage;

    @BeforeClass
    public void setup() {
        driver = DriverWeb.getDriver();
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        qaJobsPage = new QAJobsPage(driver);

    }
 /*   @BeforeTest
    public void goToHomePage(){
        homePage = new HomePage(driver);
        driver.get("https://useinsider.com/");

    }
*/
    public void closeCookieBannerIfPresent() {
     try {
         WebElement cookieAcceptButton = driver.findElement(By.id("wt-cli-accept-all-btn")); // id değişebilir
         if (cookieAcceptButton.isDisplayed()) {
             cookieAcceptButton.click();
         }
     } catch (NoSuchElementException ignored) {
         // Cookie banner yoksa bir şey yapma
     }
 }
    @Test(priority = 1)
    public void testHomePageLoadsCorrectly() {
        homePage.openHomePage();
        Assert.assertTrue(homePage.isAtHomePage(), "Home page did not load correctly.");
    }

    @Test(priority = 2)
    public void testCareersPageBlocksVisible() {
        homePage.openHomePage();
        homePage.goToCareersPage();
        Assert.assertTrue(careersPage.isLocationsSectionVisible(), "Locations section not visible.");
        Assert.assertTrue(careersPage.isTeamsSectionVisible(), "Teams section not visible.");
        Assert.assertTrue(careersPage.isLifeAtInsiderSectionVisible(), "Life at Insider section not visible.");
    }

    @Test(priority = 3)
    public void testQAJobsFilterAndValidation() throws InterruptedException {
        careersPage.goToQAPage();
        closeCookieBannerIfPresent();
        qaJobsPage.clickSeeAllQAJobs();
        qaJobsPage.filterByLocationAndDepartment("Istanbul, Turkey", "Quality Assurance");

        Assert.assertTrue(
                qaJobsPage.verifyAllJobsMatchFilters("Istanbul, Turkey", "Quality Assurance"),
                "One or more job listings do not match the filters."
        );

        qaJobsPage.hoverOnRole();
        Assert.assertTrue(
                qaJobsPage.verifyAllViewRoleButtonsRedirectToLever(),
                "One or more 'View Role' buttons did not redirect to Lever."
        );
    }

    @AfterClass
    public void tearDown() {
        DriverWeb.quitDriver();
    }
}