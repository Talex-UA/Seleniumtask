package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static utils.PagesURLs.*;

public class LogInPage extends Page<LogInPage>{

    @FindBy (name = "j_username")
    private WebElement username;

    @FindBy (name = "j_password")
    private WebElement password;

    @FindBy (id="yui-gen1-button")
    private WebElement logInButton;

    public LogInPage(WebDriver wd){
        super(wd);
    }

    public LogInPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd, true);
    }

    @Override
    public String getPageURL() {
        return LOG_IN_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws Error {
        logInButton.isDisplayed();
    }

    public UserHomePage logIn(String userName, String password){
        username.sendKeys(userName);
        this.password.sendKeys(password);
        logInButton.click();
        return new UserHomePage(wd, true);
    }

    public WrongLogInPage logInWithWrongCredentials(String userName, String password){
        username.sendKeys(userName);
        this.password.sendKeys(password);
        logInButton.click();
        return new WrongLogInPage(wd, true);
    }
}
