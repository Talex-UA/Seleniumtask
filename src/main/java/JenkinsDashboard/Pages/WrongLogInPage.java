package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WrongLogInPage extends Page<WrongLogInPage> {

    private final WebDriver wd;

    public WrongLogInPage(WebDriver wd) {
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
        return "http://seltr-kbp1-1.synapse.com:8080/loginError";
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    @FindBy (xpath = "//*[@id='main-panel-content']/div[1]/a")
    WebElement tryAgain;

    @FindBy (id = "main-panel-content")
    WebElement MainPanel;

    public LogInPage tryAgain(){
        tryAgain.click();
        return new LogInPage(wd);
    }

    public String getMainPanelText(){
        return MainPanel.getText();
    }

}
