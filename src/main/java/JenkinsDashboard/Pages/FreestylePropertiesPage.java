package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class FreestylePropertiesPage extends Page<FreestylePropertiesPage>{

    private final WebDriver wd;

    public FreestylePropertiesPage(WebDriver wd) {
        super(wd);
        this.wd=wd;
        PageFactory.initElements(wd,this);
    }

    @FindBy (xpath = "//*[@id='main-panel-content']/form/table/tbody/tr[3]/td[3]/textarea")
    WebElement Description;

    @FindBy (id = "cb6")
    WebElement DiscardOldBuilds;

    @FindBy (id = "cb7")
    WebElement ThisBuildIsParameterized;

    @FindBy (id = "cb8")
    WebElement DisableBuild;

    @FindBy (id = "cb9")
    WebElement ExecuteConcurrentBuildsIfNecessary;

    @FindBys({ @FindBy(className = "radio-block-control") })
    private List<WebElement> SourceCodeManagement;

    @FindBy (id = "cb18")
    WebElement TriggerBuildsRemotely;

    @FindBy (id = "cb19")
    WebElement BuildAfterOtherProjectsAreBuilt;

    @FindBy (id = "cb20")
    WebElement BuildPeriodically;

    @FindBy (id = "cb21")
    WebElement PollSCM;

    @FindBy (id = "yui-gen30-button")
    WebElement SaveButton;

    @Override
    public String getPageURL() {
        return null;
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    public void addDescription(String description){
        Description.sendKeys(description);
    }

    public void checkDiscardOldBuilds(){
        if (!DiscardOldBuilds.isSelected()){
            DiscardOldBuilds.click();
        }
    }
    public void checkThisBuildIsParameterized(){
        if (!ThisBuildIsParameterized.isSelected()){
            ThisBuildIsParameterized.click();
        }
    }
    public void checkDisableBuild(){
        if (!DisableBuild.isSelected()){
            DisableBuild.click();
        }
    }
    public void checkExecuteConcurrentBuildsIfNecessary(){
        if (!ExecuteConcurrentBuildsIfNecessary.isSelected()){
            ExecuteConcurrentBuildsIfNecessary.click();
        }
    }

    /**
     * This method should receive number of preferable option:
     * <ul>
     * <li>0 - None (default)</li>
     * <li>1 - CVS</li>
     * <li>2 - CVS Projectset</li>
     * <li>3 - Subversion</li>
     */
    public void setSourceCodeManagement(int index){
        if(!SourceCodeManagement.get(index).isSelected()){
            SourceCodeManagement.get(index).click();
        }
    }

    public void checkTriggerBuildsRemotely(){
        if (!TriggerBuildsRemotely.isSelected()){
            TriggerBuildsRemotely.click();
        }
    }
    public void checkBuildAfterOtherProjectsAreBuilt(){
        if (!BuildAfterOtherProjectsAreBuilt.isSelected()){
            BuildAfterOtherProjectsAreBuilt.click();
        }
    }
    public void checkBuildPeriodically(){
        if (!BuildPeriodically.isSelected()){
            BuildPeriodically.click();
        }
    }
    public void checkPollSCM(){
        if (!PollSCM.isSelected()){
            PollSCM.click();
        }
    }

    public ProjectPage save(){
        SaveButton.click();
        return new ProjectPage(wd);
    }
}
