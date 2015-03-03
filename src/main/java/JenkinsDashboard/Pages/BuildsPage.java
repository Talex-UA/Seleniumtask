package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.hamcrest.Matchers;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Generators.*;
import static utils.PagesURLs.*;

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

    public BuildsPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    @Override
    public String getPageURL() {
        return BUILDS_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws Error {
        assertThat(consoleOutput.getAttribute("src"), Matchers.containsString("terminal"));
    }

    public String getConsoleOutputText(){
        consoleOutput.click();
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
}
