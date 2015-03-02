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

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        Assert.assertThat(logIn.getAttribute("href"), Matchers.containsString("login"));
    }

    public SignUpPage gotoSignUpPage(){
        signUp.click();
        return new SignUpPage(wd);
    }

    public LogInPage gotoLogInPage(){
        logIn.click();
        return new LogInPage(wd);
    }

}
