package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WrongLogInPage extends Page<WrongLogInPage> {

    @FindBy (css = "#main-panel-content>div>a")
    WebElement tryAgain;

    @FindBy (id = "main-panel-content")
    WebElement mainPanel;

    public WrongLogInPage(WebDriver wd) {
        super(wd);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/loginError";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        tryAgain.isDisplayed();
        mainPanel.isDisplayed();
    }

    public LogInPage tryAgain(){
        tryAgain.click();
        return new LogInPage(wd);
    }

    public String getMainPanelText(){
        return mainPanel.getText();
    }

    public boolean checkForError(){
        return tryAgain.getAttribute("href").contains("login?from");
    }

}
