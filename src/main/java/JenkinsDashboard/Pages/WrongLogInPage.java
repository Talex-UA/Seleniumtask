package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static utils.PagesURLs.*;

public class WrongLogInPage extends Page<WrongLogInPage> {

    @FindBy (css = "#main-panel-content>div>a")
    private WebElement tryAgain;

    @FindBy (id = "main-panel-content")
    private WebElement mainPanel;

    public WrongLogInPage(WebDriver wd) {
        super(wd);
    }

    public WrongLogInPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public HomePage gotoHomePage() {
        jenkinsIcon.click();
        return new HomePage(wd);
    }

    @Override
    public String getPageURL() {
        return WRONG_LOGIN_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        tryAgain.isDisplayed();
    }

    public LogInPage tryAgain(){
        tryAgain.click();
        return new LogInPage(wd, true);
    }

    public String getMainPanelText(){
        return mainPanel.getText();
    }

    public boolean checkForError(){
        return tryAgain.getAttribute("href").contains("login?from");
    }

}
