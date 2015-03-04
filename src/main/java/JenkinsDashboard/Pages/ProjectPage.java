package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

import static utils.Generators.getExistingProjectName;

public class ProjectPage extends SecuredPage<ProjectPage> {

    @FindBy(css = "#main-panel-content [href*=ws]" ) // css = "#main-panel-content  [href*=ws]"   xpath = "//*[@id='main-panel-content']/table/tbody/tr[1]/td[2]/a"
    private WebElement Workspace;

    @FindBy(css = "#main-panel-content [href*=changes]")// css = "#main-panel-content  [href*=changes]"   xpath = "//*[@id='main-panel-content']/table/tbody/tr[2]/td[2]/a"
    private WebElement RecentChanges;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableProject;

    @FindBy(css = ".icon-clock.icon-md") // css = ".icon-clock.icon-md" xpath = "//*[@id='tasks']/div[5]/a[2]"
    private WebElement buildNow;

    @FindBy(css = ".icon-up.icon-md") // css = ".icon-up.icon-md"  xpath = "//*[@id='tasks']/div[1]/a[2]"
    private WebElement backToDashBoard;

    @FindBy(id = "main-panel-content")
    private WebElement mainPanel;

    @FindBy(css = ".icon-edit-delete.icon-md")
    private WebElement removeButton;

    @FindBys({@FindBy(className = "zws-inserted")})
    private List<WebElement> builds;

    public ProjectPage(WebDriver wd) {
        super(wd);
    }

    public ProjectPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public String getProjectName(){
        return getExistingProjectName();
    }

    @Override
    public String getPageURL() {
        return HOST + "job/"+ getProjectName().replace(" ","%20").toLowerCase() + "/";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        disableProject.isDisplayed();
    }

    public ProjectPage buildProject(){
        buildNow.click();
        return new ProjectPage(wd, true);
    }

    public UserHomePage backToDashboard() {
        backToDashBoard.click();
        return new UserHomePage(wd, true);
    }

    public String getMainPanelText() {
        return mainPanel.getText();
    }

    public BuildsPage openLatestBuild(){
        String buildNumber = builds.get(0).getText().substring(1).trim();
        builds.get(0).click();
        return new BuildsPage(wd, true){
            @Override
            public String getBuildNumber(){
                return buildNumber;
            }
            @Override
            public String getProjectName(){
                return ProjectPage.this.getProjectName();
            }
        };
    }

    public int countBuildsBeforeAction(){
        return builds.size();
    }

    public ProjectPage waitForNewBuild(int countBuildsBeforeAction){
        int countBuildsAfterAction = builds.size();
        while (countBuildsAfterAction-countBuildsBeforeAction!=1) {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e){}
            countBuildsAfterAction=builds.size();
        }
        return this;
    }

    public BuildsPage waitForBuildToEnd(){
        BuildsPage buildsPage=openLatestBuild();
        while (!buildsPage.getConsoleOutputText().contains("SUCCESS")){
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e){}
        }
        return new BuildsPage(wd, true);
    }
}
