package JenkinsDashboard.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.CommonMethods.sendKeysToField;

public class LogInPage extends Page<LogInPage> {

    @FindBy(id = "j_username")
    private WebElement username;

    @FindBy(id = "j_password")
    private WebElement passwordField;

    @FindBy(css = "#main-panel-content [type=button]") // css = "#main-panel-content [type=button]" id = "yui-gen1-button"
    private WebElement logInButton;

    public LogInPage(WebDriver wd) {
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

    public UserHomePage logIn(String userName, String password) {
        loginWithoutReturn(userName, password);
        return new UserHomePage(wd, true);
    }

    public WrongLogInPage logInWithWrongCredentials(String userName, String password) {
        loginWithoutReturn(userName, password);
        return new WrongLogInPage(wd, true);
    }

    public void loginWithoutReturn(String userName, String password) {
        WebElement username = (new WebDriverWait(wd, 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.name("j_username"));
            }
        });
        sendKeysToField(username, userName);
        WebElement passwordField = (new WebDriverWait(wd, 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.name("j_password"));
            }
        });
        sendKeysToField(passwordField, password);
        logInButton.click();
    }
}
