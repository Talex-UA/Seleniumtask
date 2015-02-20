package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;


public abstract class SecuredPage extends Page<SecuredPage> implements Generators{

    public SecuredPage(WebDriver wd) {
        super(wd);
    }

    @Override
    protected void load() {
        if (isLoggedIn()){
            super.load();
        } else {
            login();
            super.load();
        }
    }

    @Override
    protected void isLoaded() throws NoSuchElementException {
        try {
            super.isLoaded();
        } catch (AssertionError e){

        }
    }


    private void login() {
        LogInPage logInPage = new LogInPage(wd);
        UserHomePage userHomePage = logInPage.logIn(getExistingUserName(), getPassword());
    }
}
