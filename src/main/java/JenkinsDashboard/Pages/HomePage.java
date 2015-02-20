package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page<HomePage>{

    private static final String PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/";

    public HomePage(WebDriver wd){
        super(wd);
    }

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
        logIn.getAttribute("href").contains("login");
    }

    @FindBy(xpath = "//*[@id='header']/div[2]/a[1]")
    WebElement logIn;

    @FindBy(xpath = "//*[@id='header']/div[2]/a[2]")
    WebElement signUp;



    public SignUpPage gotoSignUpPage(){
        signUp.click();
        return new SignUpPage(wd);
    }

    public LogInPage gotoLogInPage(){
        logIn.click();
        return new LogInPage(wd);
    }



}
