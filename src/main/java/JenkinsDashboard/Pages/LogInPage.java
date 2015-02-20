package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage extends Page<LogInPage>{
    private final WebDriver wd;
    public static boolean isLoggedIn = false;

    public LogInPage(WebDriver wd){
        super(wd);
        this.wd=wd;
        PageFactory.initElements(wd, this);
    }

    public HomePage gotoHomePage() {
        JenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/login?from=%2F";
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    @FindBy (name = "j_username")
    WebElement username;

    @FindBy (name = "j_password")
    WebElement password;

    @FindBy (id="yui-gen1-button")
    WebElement logInButton;

    @FindBy(xpath = "//*[@id='header']/div[2]/span/a[2]")
    static WebElement LogOut;


    public UserHomePage logIn(String USER_NAME, String PASSWORD){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        setIsLoggedInTrue();
        return new UserHomePage(wd);
    }

    public WrongLogInPage logInWithWrongCredentials(String USER_NAME, String PASSWORD){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        return new WrongLogInPage(wd);
    }

    public static void setIsLoggedInTrue(){
        if (isLoggedIn==false) isLoggedIn=true;
    }

    public static void setIsLoggedInFalse(){
        if (isLoggedIn==true) isLoggedIn=false;
    }

    public static void logout() {
        LogOut.click();
        setIsLoggedInFalse();
    }

}
