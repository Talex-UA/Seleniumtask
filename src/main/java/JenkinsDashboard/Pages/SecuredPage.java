package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Generators.*;


public abstract class SecuredPage <T extends SecuredPage<T>>extends Page<T> {

    @FindBy (name = "j_username")
    private WebElement username;

    @FindBy (name = "j_password")
    private WebElement password;

    @FindBy (id="yui-gen1-button")
    private WebElement logInButton;

    @FindBy(css = ".login>a[href*=login]")
    private WebElement logIn;

    public SecuredPage(WebDriver wd) {
        super(wd);
    }

    @Override
    protected void load() {
        super.load();
        if (!isLoggedIn()) {
            login();
        }
    }

    protected void login() {
        logIn.click();
        username.sendKeys(getExistingUserName());
        password.sendKeys(getPassword());
        logInButton.click();
    }
}
