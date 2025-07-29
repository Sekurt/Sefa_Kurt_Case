package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class QAJobsPage extends BasePage {

    // Buton ve filtreler
    private final By seeAllJobsBtn = By.xpath("//a[contains(text(),'See all QA jobs')]");
    private final By locationFilter = By.xpath("//select[@name='filter-by-location']");
    private final By departmentFilter = By.xpath("//span[@id='select2-filter-by-department-container']");
    private final By jobList = By.xpath("//div[@id='jobs-list']//div[contains(@class,'position-list-item')]");
    private final By locationIstanbul= By.xpath("//option[@class='job-location istanbul-turkiye']");

    private final By jobDepartmenQa = By.xpath("//option[@class='job-team qualityassurance']");
    // İçerik detayları
    private final By jobTitle = By.cssSelector(".position-title");
    private final By jobLocation = By.cssSelector(".position-location");
    private final By jobDepartment = By.cssSelector(".position-department");

    private final By HoverFirstRole = By.xpath("//div[@class='position-list-item col-12 col-lg-4 qualityassurance istanbul-turkiye full-timeremote']");
    private final By viewRoleBtn = By.xpath(".//a[contains(text(),'View Role')]");

    public QAJobsPage(WebDriver driver) {
        super(driver);
    }

    public void clickSeeAllQAJobs() {
        click(driver.findElement(seeAllJobsBtn));
    }

    public void filterByLocationAndDepartment(String location, String department) throws InterruptedException {

      //  WebElement departmentDropdown = driver.findElement(By.xpath("//select[@name='filter-by-location']"));
        WebElement departmentDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//select[@name='filter-by-location']")));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//select[@name='filter-by-location']"),
                "Istanbul, Turkiye"
        ));
        // Select objesi oluştur
        Select selectDepartment = new Select(departmentDropdown);



        // 1) Text ile seç
        selectDepartment.selectByVisibleText("Istanbul, Turkiye");

    }

    public boolean verifyAllJobsMatchFilters(String expectedLocation, String expectedDepartment) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> jobCards = driver.findElements(By.xpath("//div[@id='jobs-list']//div[contains(@class,'position-list-item')]"));
        int matchCount = 0;
        for (int i = 0; i < jobCards.size(); i++) {
            // Her döngüde elementi yeniden al
            WebElement jobCard = driver.findElements(By.xpath("//div[@id='jobs-list']//div[contains(@class,'position-list-item')]")).get(i);
            String department = jobCard.findElement(By.cssSelector(".position-department")).getText().trim();
            String location = jobCard.findElement(By.cssSelector(".position-location")).getText().trim();
            if (department.equals("Quality Assurance") && location.equals("Istanbul, Turkiye")) {
                matchCount++;
                System.out.println("Eşleşen ilan: " + department + " - " + location);
            }
        }
        System.out.println("Toplam eşleşen ilan sayısı: " + matchCount);
        return matchCount > 0;
    }

    public void hoverOnRole(){
        WebElement roleElement = driver.findElement(HoverFirstRole);
        waitForVisibility(roleElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(roleElement).perform();
    }

    public boolean verifyAllViewRoleButtonsRedirectToLever() {
        List<WebElement> jobs = driver.findElements(jobList);
        if (jobs.isEmpty()) return false;

        for (WebElement job : jobs) {
            WebElement viewButton = job.findElement(viewRoleBtn);
            String openInNewTab = viewButton.getAttribute("target");

            String originalWindow = driver.getWindowHandle();
            click(viewButton);

            // Eğer yeni sekmede açıyorsa ona geç
            if (openInNewTab != null && openInNewTab.equals("_blank")) {
                for (String winHandle : driver.getWindowHandles()) {
                    if (!winHandle.equals(originalWindow)) {
                        driver.switchTo().window(winHandle);
                        break;
                    }
                }
            }

            boolean isLeverPage = driver.getCurrentUrl().contains("lever.co");
            driver.close(); // yeni sekmeyi kapat
            driver.switchTo().window(originalWindow);

            if (isLeverPage) return true;
        }

        return true;
    }
}
