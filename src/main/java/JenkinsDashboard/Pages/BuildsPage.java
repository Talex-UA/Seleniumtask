package JenkinsDashboard.Pages;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Generators.getExistingProjectName;

public class BuildsPage extends SecuredPage<BuildsPage>{

    @FindBy (css = "[href*='console']") //css = ".icon-terminal.icon-md"   xpath = "//*[@id='tasks']/div[4]/a[2]"
    private WebElement consoleOutput;

    @FindBy (className = "console-output")
    private WebElement consoleOutputText;

    @FindBy (css = ".icon-up.icon-md")
    private WebElement backToProject;

    public BuildsPage(WebDriver wd) {
        super(wd);
    }

    public BuildsPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public String getProjectName() {
        return getExistingProjectName();
    }

    public String getBuildNumber() {
        return "1";
    }

    @Override
    public String getPageURL() {
        return HOST + "view/All/job/"+getProjectName().replace(" ","%20")+"/"+getBuildNumber()+"/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        assertThat(consoleOutput.isDisplayed(), Matchers.is(true));
    }

    public String getConsoleOutputText(){
        consoleOutput.click();
        WebElement consoleOutputText = (new WebDriverWait(wd, 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.className("console-output"));
            }
        });
        return consoleOutputText.getText();
    }

    public UserHomePage backToDashboard(){
        jenkinsIcon.click();
        return new UserHomePage(wd, true);
    }

    public ProjectPage goBackToProject(){
        backToProject.click();
        return new ProjectPage(wd, true);
    }

    public BuildsPage waitForBuildToEnd(){
        while (!getConsoleOutputText().contains("SUCCESS")){
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e){}
        }
        return this;
    }
}
