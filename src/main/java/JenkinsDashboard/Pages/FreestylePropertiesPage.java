package JenkinsDashboard.Pages;

import org.hamcrest.Matchers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Generators.*;

public class FreestylePropertiesPage extends SecuredPage<FreestylePropertiesPage> {

    @FindBy(css = "#main-panel-content>form>table>tbody>tr>td>textarea")
    // css = "#main-panel-content>form>table>tbody>tr>td>textarea" xpath = "//*[@id='main-panel-content']/form/table/tbody/tr[3]/td[3]/textarea"
    private WebElement description;

    @FindBy(id = "cb6")
    private WebElement discardOldBuilds;

    @FindBy(id = "cb7")
    private WebElement thisBuildIsParameterized;

    @FindBy(id = "cb8")
    private WebElement disableBuild;

    @FindBy(id = "cb9")
    private WebElement executeConcurrentBuildsIfNecessary;

    @FindBys({@FindBy(className = "radio-block-control")})
    private List<WebElement> sourceCodeManagement;

    @FindBy(name = "pseudoRemoteTrigger")//id = "cb18"
    private WebElement triggerBuildsRemotely;

    @FindBy(name = "authToken")
    private WebElement authomationToken;

    @FindBy(id = "cb19")
    private WebElement buildAfterOtherProjectsAreBuilt;

    @FindBy(id = "cb20")
    private WebElement buildPeriodically;

    @FindBy(id = "cb21")
    private WebElement pollSCM;

    @FindBy(id = "yui-gen30-button")
    private WebElement saveButton;

    @FindBy(id = "yui-gen3-button")
    private WebElement addBuildStepButton;

    @FindBy(id = "yui-gen32")
    private WebElement executeWindowsBatchCommand;

    @FindBy(name = "command")
    private WebElement command;

    public FreestylePropertiesPage(WebDriver wd) {
        super(wd);
    }

    public FreestylePropertiesPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public String getProjectName() {
        return getExistingProjectName();
    }

    @Override
    public String getPageURL() {
        return HOST + "job/" + getProjectName().replace(" ", "%20") + "/configure";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        assertThat(description.isDisplayed(), Matchers.is(true));
    }

    public FreestylePropertiesPage addDescription(String stringDescription) {
        description.sendKeys(stringDescription);
        return this;
    }

    public void checkDiscardOldBuilds() {
        if (!discardOldBuilds.isSelected()) {
            discardOldBuilds.click();
        }
    }

    public void checkThisBuildIsParameterized() {
        if (!thisBuildIsParameterized.isSelected()) {
            thisBuildIsParameterized.click();
        }
    }

    public void checkDisableBuild() {
        if (!disableBuild.isSelected()) {
            disableBuild.click();
        }
    }

    public void checkExecuteConcurrentBuildsIfNecessary() {
        if (!executeConcurrentBuildsIfNecessary.isSelected()) {
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
    public void setSourceCodeManagement(int index) {
        if (!sourceCodeManagement.get(index).isSelected()) {
            sourceCodeManagement.get(index).click();
        }
    }

    public FreestylePropertiesPage checkTriggerBuildsRemotely() {
        if (!triggerBuildsRemotely.isSelected()) {
            triggerBuildsRemotely.click();
            authomationToken.sendKeys(getBuildToken());
        }
        return this;
    }

    public void checkBuildAfterOtherProjectsAreBuilt() {
        if (!buildAfterOtherProjectsAreBuilt.isSelected()) {
            buildAfterOtherProjectsAreBuilt.click();
        }
    }

    public void checkBuildPeriodically() {
        if (!buildPeriodically.isSelected()) {
            buildPeriodically.click();
        }
    }

    public void checkPollSCM() {
        if (!pollSCM.isSelected()) {
            pollSCM.click();
        }
    }

    public ProjectPage save() {
        saveButton.click();
        System.out.println(FreestylePropertiesPage.this.getProjectName());
        return new ProjectPage(wd, true) {
            @Override
            public String getProjectName() {
                return FreestylePropertiesPage.this.getProjectName();
            }
        };
    }

    public FreestylePropertiesPage addWindowsBatchCommand() {
        try {
            addBuildStepButton.click();
            while (!executeWindowsBatchCommand.isDisplayed()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            executeWindowsBatchCommand.click();

            boolean isDisplayed = false;
            while (!isDisplayed) {
                try {
                    command.sendKeys(getBatchCommand());
                    isDisplayed = true;
                } catch (NoSuchElementException e) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        } catch (UnhandledAlertException uae) {
            uae.printStackTrace();
        }
        return this;
    }
}
