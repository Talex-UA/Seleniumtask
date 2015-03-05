package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.CommonMethods.sendKeysToField;

public class LogInPage extends Page<LogInPage>{

    @FindBy (name = "j_username")
    private WebElement username;

    @FindBy (name = "j_password")
    private WebElement passwordField;

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
        return HOST + "login?from=%2F";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        logInButton.isDisplayed();
    }

    public UserHomePage logIn(String userName, String password){
        sendKeysToField(username, userName);
        sendKeysToField(passwordField, password);
        logInButton.click();
        return new UserHomePage(wd, true);
    }

    public WrongLogInPage logInWithWrongCredentials(String userName, String password){
        sendKeysToField(username, userName);
        sendKeysToField(passwordField,password);
        logInButton.click();
        return new WrongLogInPage(wd, true);
    }
}
