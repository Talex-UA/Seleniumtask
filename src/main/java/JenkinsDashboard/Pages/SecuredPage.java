package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Generators.getExistingUserName;
import static utils.Generators.getPassword;


public abstract class SecuredPage<T extends SecuredPage<T>> extends Page<T> {

    @FindBy(name = "j_username")
    private WebElement username;

    @FindBy(name = "j_password")
    private WebElement password;

    @FindBy(id = "yui-gen1-button")
    private WebElement logInButton;

    @FindBy(css = ".login>a[href*=login]")
    private WebElement logIn;

    public SecuredPage(WebDriver wd) {
        super(wd);
    }

    public SecuredPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    @Override
    public T get() {
        try {
            isLoaded();
            return (T) this;
        } catch (Error e) {
            load();
            if (!isLoggedIn()) {
                login();
            }
        }
        isLoaded();
        return (T) this;
    }

    protected void login() {
        logIn.click();
        username.sendKeys(getExistingUserName());
        password.sendKeys(getPassword());
        logInButton.click();
    }
}
