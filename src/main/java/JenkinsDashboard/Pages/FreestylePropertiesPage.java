package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public class FreestylePropertiesPage extends SecuredPage<FreestylePropertiesPage>{

    @FindBy (css = "#main-panel-content>form>table>tbody>tr>td>textarea") // css = "#main-panel-content>form>table>tbody>tr>td>textarea" xpath = "//*[@id='main-panel-content']/form/table/tbody/tr[3]/td[3]/textarea"
    WebElement description;

    @FindBy (id = "cb6")
    WebElement discardOldBuilds;

    @FindBy (id = "cb7")
    WebElement thisBuildIsParameterized;

    @FindBy (id = "cb8")
    WebElement disableBuild;

    @FindBy (id = "cb9")
    WebElement executeConcurrentBuildsIfNecessary;

    @FindBys({ @FindBy(className = "radio-block-control") })
    private List<WebElement> sourceCodeManagement;

    @FindBy (id = "cb18")
    WebElement triggerBuildsRemotely;

    @FindBy (id = "cb19")
    WebElement buildAfterOtherProjectsAreBuilt;

    @FindBy (id = "cb20")
    WebElement buildPeriodically;

    @FindBy (id = "cb21")
    WebElement pollSCM;

    @FindBy (id = "yui-gen30-button")
    WebElement saveButton;

    public FreestylePropertiesPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/job/"+getExistingProjectName()+"/configure";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        description.isDisplayed();
    }

    public void addDescription(String description){
        this.description.sendKeys(description);
    }

    public void checkDiscardOldBuilds(){
        if (!discardOldBuilds.isSelected()){
            discardOldBuilds.click();
        }
    }
    public void checkThisBuildIsParameterized(){
        if (!thisBuildIsParameterized.isSelected()){
            thisBuildIsParameterized.click();
        }
    }
    public void checkDisableBuild(){
        if (!disableBuild.isSelected()){
            disableBuild.click();
        }
    }
    public void checkExecuteConcurrentBuildsIfNecessary(){
        if (!executeConcurrentBuildsIfNecessary.isSelected()){
            executeConcurrentBuildsIfNecessary.click();
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
        if(!sourceCodeManagement.get(index).isSelected()){
            sourceCodeManagement.get(index).click();
        }
    }

    public void checkTriggerBuildsRemotely(){
        if (!triggerBuildsRemotely.isSelected()){
            triggerBuildsRemotely.click();
        }
    }
    public void checkBuildAfterOtherProjectsAreBuilt(){
        if (!buildAfterOtherProjectsAreBuilt.isSelected()){
            buildAfterOtherProjectsAreBuilt.click();
        }
    }
    public void checkBuildPeriodically(){
        if (!buildPeriodically.isSelected()){
            buildPeriodically.click();
        }
    }
    public void checkPollSCM(){
        if (!pollSCM.isSelected()){
            pollSCM.click();
        }
    }

    public ProjectPage save(){
        saveButton.click();
        return new ProjectPage(wd);
    }
}
