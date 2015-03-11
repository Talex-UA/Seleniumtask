package JenkinsDashboard.Pages;

import org.hamcrest.Matchers;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.CommonMethods.sendKeysToField;
import static utils.Generators.*;

public class FreestylePropertiesPage extends SecuredPage<FreestylePropertiesPage> {

    @FindBy(name = "description")
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

    @FindBy(className = "radio-block-control")
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

    @FindBy(css = ".yui-submit-button")
    private WebElement saveButton;

    @FindBy(css = "[suffix=builder]")
    private WebElement addBuildStepButton;

    @FindBy(css = ".first-of-type")
    private List<WebElement> anyDropDownList;

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
        return HOST + "view/All/job/" + getProjectName().replace(" ", "%20") + "/configure";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        assertThat(description.isDisplayed(), Matchers.is(true));
    }

    private List<WebElement> getDropDowList(){

        List<WebElement> dropDown = wd.findElements(By.cssSelector(".first-of-type li"));
        return dropDown;
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
        return new ProjectPage(wd, true) {
            @Override
            public String getProjectName() {
                return FreestylePropertiesPage.this.getProjectName();
            }
        };
    }

    public FreestylePropertiesPage addWindowsBatchCommand() {
        try {
            ((JavascriptExecutor) wd)
                    .executeScript(
                            "arguments[0].scrollIntoView(true);",
                            addBuildStepButton);

            addBuildStepButton.click();
        } catch (Exception e) {
// System.out.println("No clickable");
        }

//    addBuildStepButton.click();

        for (WebElement currentElement:getDropDowList()){
            if (currentElement.getText().contains("Windows")){
                currentElement.click();
                break;
            }
        }

        WebElement command = (new WebDriverWait(wd, 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.xpath("//textarea[@name='command']"));
            }
        });

        sendKeysToField(command, getBatchCommand());
        return this;
    }
}
//Actions actions = new Actions(wd);
//actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
