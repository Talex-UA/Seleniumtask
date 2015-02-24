package JenkinsDashboard.Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public class ProjectPage extends SecuredPage<ProjectPage> {

    @FindBy(xpath = "//*[@id='main-panel-content']/table/tbody/tr[1]/td[2]/a")
    WebElement Workspace;

    @FindBy(xpath = "//*[@id='main-panel-content']/table/tbody/tr[2]/td[2]/a")
    WebElement RecentChanges;

    @FindBy(id = "yui-gen1-button")
    WebElement disableProject;

    @FindBy(xpath = "//*[@id='tasks']/div[5]/a[2]")
    WebElement buildNow;

    @FindBy(xpath = "//*[@id='tasks']/div[1]/a[2]")
    WebElement backToDashBoard;

    @FindBy(id = "main-panel-content")
    WebElement mainPanel;

    @FindBy(xpath = "//*[@id='tasks']/div[5]/a[2]")
    WebElement removeButtonifDisabled;

    @FindBy(xpath = "//*[@id='tasks']/div[6]/a[2]")
    WebElement removeButtonifEnabled;

    @FindBys({@FindBy(className = "zws-inserted")})
    List<WebElement> builds;

    public ProjectPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/job/" + getExistingProjectName() + "/";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        disableProject.isDisplayed();
    }

    public ProjectPage buildProject(){
        int before = builds.size();
        buildNow.click();
        int after = builds.size();
        while (after-before!=1) {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e){}
            after=builds.size();
        }
        return new ProjectPage(wd);
    }

    public UserHomePage backToDashboard() {
        backToDashBoard.click();
        return new UserHomePage(wd);
    }

    public String getMainPanelText() {
        return mainPanel.getText();
    }

    public UserHomePage deleteProject() {
        if (isDisabled()){
            removeButtonifDisabled.click();
        } else removeButtonifEnabled.click();
        Alert alert = wd.switchTo().alert();
        alert.accept();
        return new UserHomePage(wd);
    }

    private boolean isDisabled() {
        return getMainPanelText().contains("This project is currently disabled");
    }

    public BuildsPage openLatestBuild(){
        System.out.println(builds.get(0).getText());
        builds.get(0).click();
        return new BuildsPage(wd);
    }
}
