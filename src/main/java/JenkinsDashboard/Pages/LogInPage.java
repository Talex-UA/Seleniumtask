package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage extends Page{
    private final WebDriver wd;

    public LogInPage(WebDriver wd){
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
        return wd.getCurrentUrl();
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    @FindBy (name = "j_username")
    WebElement username;

    @FindBy (name = "j_password")
    WebElement password;

    @FindBy (id="yui-gen1-button")
    WebElement logInButton;


    public HomePage logIn(String USER_NAME, String PASSWORD){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        return new HomePage(wd);
    }

    public WrongLogInPage logInWithWrongCredentials(String USER_NAME, String PASSWORD){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        return new WrongLogInPage(wd);
    }
}
