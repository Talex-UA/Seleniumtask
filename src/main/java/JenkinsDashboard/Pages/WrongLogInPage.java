package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WrongLogInPage extends Page {

    private final WebDriver wd;

    public WrongLogInPage(WebDriver wd) {
        super(wd);
        this.wd=wd;
        PageFactory.initElements(wd, this);
    }

    @Override
    public HomePage gotoHomePage() {
        JenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return null;
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    @FindBy (xpath = "//*[@id='main-panel-content']/div[1]/a")
    WebElement tryAgain;

    public LogInPage tryAgain(){
        tryAgain.click();
        return new LogInPage(wd);
    }

}
