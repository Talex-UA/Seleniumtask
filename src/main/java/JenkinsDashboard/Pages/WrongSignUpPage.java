package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.PagesURLs.*;

public class WrongSignUpPage extends Page<SignUpPage> {

    @FindBy(className = "error")
    private WebElement errorMessage;

    public WrongSignUpPage(WebDriver wd) {
        super(wd);
    }

    public WrongSignUpPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    @Override
    public String getPageURL() {
        return WRONG_SIGNUP_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws Error {
        errorMessage.isDisplayed();
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }

    public String getErrorClass(){
        return errorMessage.getAttribute("class");
    }
}
