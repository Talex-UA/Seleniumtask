package JenkinsDashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage{

    private final WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='header']/div[2]/a[1]")
    WebElement logIn;

    @FindBy(xpath = "//*[@id='header']/div[2]/a[2]")
    WebElement signUp;

    @FindBy(xpath = "//*[@id='header']/div[2]/span/a[2]")
    WebElement logOut;

    public SignUpPage gotoSignUpPage(){
        signUp.click();
        return new SignUpPage(driver);
    }

    public LogInPage gotoLogInPage(){
        logIn.click();
        return new LogInPage(driver);
    }

    public void logOut(){
        logOut.click();
    };


}
