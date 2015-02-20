package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage extends Page<ProjectPage>{

    @FindBy (xpath = "//*[@id='main-panel-content']/table/tbody/tr[1]/td[2]/a")
    WebElement Workspace;

    @FindBy (xpath = "//*[@id='main-panel-content']/table/tbody/tr[2]/td[2]/a")
    WebElement RecentChanges;

    @FindBy(id = "yui-gen1-button")
    WebElement DisableProject;

    @FindBy (xpath = "//*[@id='tasks']/div[5]/a[2]")
    WebElement BuildNow;

    @FindBy (xpath = "//*[@id='tasks']/div[1]/a[2]")
    WebElement BackToDashBoard;

    @FindBy (id = "main-panel-content")
    WebElement MainPanel;

    public ProjectPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return null;
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        DisableProject.isDisplayed();
    }

    public void buildNow(){
        BuildNow.click();
    }

    public UserHomePage backToDashboard(){
        BackToDashBoard.click();
        return new UserHomePage(wd);
    }

    public String getMainPanelText(){
        return MainPanel.getText();
    }
}
