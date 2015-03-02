package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.hamcrest.Matchers;
import org.junit.Assert;

import static utils.Generators.*;

public class BuildsPage extends SecuredPage<BuildsPage>{

    @FindBy (css = ".icon-terminal.icon-md") //css = ".icon-terminal.icon-md"   xpath = "//*[@id='tasks']/div[4]/a[2]"
    private WebElement consoleOutput;

    @FindBy (className = "console-output")
    private WebElement consoleOutputText;

    @FindBy (css = ".icon-up.icon-md")
    private WebElement backToProject;

    public BuildsPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/job/"+getExistingProjectName()+"/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        Assert.assertThat(consoleOutput.getText(), Matchers.containsString("Console Output"));
    }

    public String getConsoleOutputText(){
        consoleOutput.click();
        return consoleOutputText.getText();
    }

    public UserHomePage backToDashboard(){
        jenkinsIcon.click();
        return new UserHomePage(wd);
    }

    public ProjectPage goBackToProject(){
        backToProject.click();
        return new ProjectPage(wd);
    }
}
