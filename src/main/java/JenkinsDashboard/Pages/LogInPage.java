package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage extends Page<LogInPage>{

    @FindBy (name = "j_username")
    WebElement username;

    @FindBy (name = "j_password")
    WebElement password;

    @FindBy (id="yui-gen1-button")
    WebElement logInButton;

    public LogInPage(WebDriver wd){
        super(wd);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/login?from=%2F";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        logInButton.isDisplayed();
    }

    public UserHomePage logIn(String USER_NAME, String PASSWORD){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        return new UserHomePage(wd);
    }

    public WrongLogInPage logInWithWrongCredentials(String USER_NAME, String PASSWORD){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        return new WrongLogInPage(wd);
    }
}
