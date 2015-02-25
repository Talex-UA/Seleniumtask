package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public abstract class SecuredPage <T extends SecuredPage<T>>extends Page<T> implements Generators{

    @FindBy (name = "j_username")
    WebElement username;

    @FindBy (name = "j_password")
    WebElement password;

    @FindBy (id="yui-gen1-button")
    WebElement logInButton;

    @FindBy(css = ".login>a[href*=login]")
    WebElement logIn;

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
