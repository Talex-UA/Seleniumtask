package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuildsPage extends SecuredPage<BuildsPage>{

    @FindBy (xpath = "//*[@id='tasks']/div[4]/a[2]")
    WebElement consoleOutput;

    @FindBy (className = "console-output")
    WebElement consoleOutputText;

    public BuildsPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/job/"+getExistingProjectName()+"/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        consoleOutput.getText().contains("Console Output");
    }

    public String getConsoleOutputText(){
        consoleOutput.click();
        return consoleOutputText.getText();
    }

    public UserHomePage backToDashboard(){
        jenkinsIcon.click();
        return new UserHomePage(wd);
    }
}
