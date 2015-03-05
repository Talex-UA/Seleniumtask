package JenkinsDashboard.Pages;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page<HomePage>{

    public HomePage(WebDriver wd){
        super(wd);
    }

    @FindBy(css = ".login>a[href*=login]") // css = ".login>a[href*=login]" xpath = "//*[@id='header']/div[2]/a[1]"
    private WebElement logIn;

    @FindBy(css = ".login>a[href*=signup]") // css = ".login>a[href*=signup]" xpath = "//*[@id='header']/div[2]/a[2]"
    private WebElement signUp;

    public HomePage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
//        return String.format("%s",HOST);
        return HOST;
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        Assert.assertThat(jenkinsIcon.isDisplayed(), Matchers.is(true));
    }

    public SignUpPage gotoSignUpPage(){
        signUp.click();
        return new SignUpPage(wd, true);
    }

    public LogInPage gotoLogInPage(){
        logIn.click();
        return new LogInPage(wd, true);
    }

}
