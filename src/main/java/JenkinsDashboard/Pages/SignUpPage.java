package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.CommonMethods.sendKeysToField;
import static utils.PagesURLs.SIGN_UP_PAGE;

public class SignUpPage extends Page<SignUpPage>{

    @FindBy (name = "username")
    private WebElement usernameField;

    @FindBy (name = "password1")
    private WebElement passwordField;

    @FindBy (name = "password2")
    private WebElement confirm_password;

    @FindBy (name = "fullname")
    private WebElement fullnameField;

    @FindBy (name = "email")
    private WebElement emailField;

    @FindBy (id = "yui-gen1-button")
    private WebElement signUpButton;

    @FindBy (css = "#main-panel-content>div>a") // css = "#main-panel-content>div>a"    xpath = "//*[@id='main-panel-content']/div/a"
    private WebElement gotoTopPage;

    @FindBy (id = "main-panel-content")
    private WebElement mainPanel;

    public SignUpPage(WebDriver wd){
        super(wd);
    }

    public SignUpPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd, true);
    }

    @Override
    public String getPageURL() {
        return SIGN_UP_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        confirm_password.isDisplayed();
    }

    public UserHomePage signUpNewUser(String userName, String password, String fullName, String email){
        sendKeysToField(usernameField, userName);
        sendKeysToField(passwordField, password);
        sendKeysToField(confirm_password, password);
        sendKeysToField(fullnameField, fullName);
        sendKeysToField(emailField, email);
        signUpButton.click();
        gotoTopPage.click();
        return new UserHomePage(wd, true);
    }

    public WrongSignUpPage signUpExistingNameUser(String userName, String password, String fullName, String email){
        sendKeysToField(usernameField, userName);
        sendKeysToField(passwordField, password);
        sendKeysToField(confirm_password, password);
        sendKeysToField(fullnameField, fullName);
        sendKeysToField(emailField, email);
        signUpButton.click();
        return new WrongSignUpPage(wd, true);
    }

    public String getMainPanelText(){
        return mainPanel.getText();
    }


}
