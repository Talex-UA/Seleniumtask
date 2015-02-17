package JenkinsDashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage {
    private final WebDriver driver;

    public LogInPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    private static final String USER_NAME = "OleksandrA";
    private static final String PASSWORD = "qwerty1234";

    @FindBy (name = "j_username")WebElement username;
    @FindBy (name = "j_password") WebElement password;
    @FindBy (id="yui-gen1-button") WebElement logInButton;

    public HomePage logIn(){
        username.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        logInButton.click();
        return new HomePage(driver);
    }
}
