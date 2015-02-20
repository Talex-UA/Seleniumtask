package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage extends Page<SignUpPage>{

    private final WebDriver wd;

    public SignUpPage(WebDriver wd){
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
        return "http://seltr-kbp1-1.synapse.com:8080/signup";
    }

    @Override
    protected void checkUniqueElements() throws Error {
    }

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

    @FindBy (id = "main-panel-content")
    WebElement MainPanel;

    public UserHomePage signUpNewUser(String USER_NAME, String PASSWORD, String FULL_NAME, String EMAIL){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        confirm_password.sendKeys(PASSWORD);
        fullname.sendKeys(FULL_NAME);
        email.sendKeys(EMAIL);
        signUpButton.click();
        gotoTopPage.click();
        return new UserHomePage(wd);
    }

    public void signUpExistingNameUser(String USER_NAME, String PASSWORD, String FULL_NAME, String EMAIL){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        confirm_password.sendKeys(PASSWORD);
        fullname.sendKeys(FULL_NAME);
        email.sendKeys(EMAIL);
        signUpButton.click();
    }

    public String getMainPanelText(){
        return MainPanel.getText();
    }

}
