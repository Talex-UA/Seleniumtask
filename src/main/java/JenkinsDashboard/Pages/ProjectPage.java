package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage extends Page<ProjectPage>{

    private final WebDriver wd;

    public ProjectPage(WebDriver wd) {
        super(wd);
        this.wd=wd;
        PageFactory.initElements(wd,this);
    }

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

    @Override
    public String getPageURL() {
        return null;
    }

    @Override
    protected void checkUniqueElements() throws Error {

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
