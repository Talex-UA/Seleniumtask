package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends Page<SignUpPage>{

    @FindBy (name = "username")
    private WebElement username;

    @FindBy (name = "password1")
    private WebElement password;

    @FindBy (name = "password2")
    private WebElement confirm_password;

    @FindBy (name = "fullname")
    private WebElement fullname;

    @FindBy (name = "email")
    private WebElement email;

    @FindBy (id = "yui-gen1-button")
    private WebElement signUpButton;

    @FindBy (css = "#main-panel-content>div>a") // css = "#main-panel-content>div>a"    xpath = "//*[@id='main-panel-content']/div/a"
    private WebElement gotoTopPage;

    @FindBy (id = "main-panel-content")
    private WebElement mainPanel;

    @FindBy (className = "error")
    private WebElement errorMessage;

    public SignUpPage(WebDriver wd){
        super(wd);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/signup";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        confirm_password.isDisplayed();
    }

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
        return mainPanel.getText();
    }

    public boolean checkForError(){
        return errorMessage.isDisplayed();
    }
}
