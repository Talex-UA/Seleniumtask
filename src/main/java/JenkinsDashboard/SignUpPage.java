package JenkinsDashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    private final WebDriver driver;

    public SignUpPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    private static final String USER_NAME = "Talex-UA";
    private static final String PASSWORD = "qwerty1234";
    private static final String FULL_NAME = "Alexander";
    private static final String EMAIL = "talexua91@gmail.com";

    @FindBy (name = "username")
    WebElement username;

    @FindBy (name = "password1")
    WebElement password;

    @FindBy (name = "password2")
    WebElement confirm_password;

    @FindBy (name = "fullname")
    WebElement fullname;

    @FindBy (name = "email")
    WebElement email;

    @FindBy (id = "yui-gen1-button")
    WebElement signUpButton;

    @FindBy (xpath = "//*[@id='main-panel-content']/div/a")
    WebElement gotoTopPage;

    public HomePage signUp(){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        confirm_password.sendKeys(PASSWORD);
        fullname.sendKeys(FULL_NAME);
        email.sendKeys(EMAIL);
        signUpButton.click();
        gotoTopPage.click();
        return new HomePage(driver);
    }



}
