package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserPage extends SecuredPage<UserPage>{

    @FindBy (id="yui-gen1-button")
    WebElement yesButton;

    @FindBy (xpath = "//*[@id='tasks']/div[6]/a[2]")
    WebElement deleteUser;

    @FindBy (xpath = "//*[@id='tasks']/div[2]/a[2]")
    WebElement goToPeoplePage;

    public UserPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/user/"+getExistingUserName()+"/";
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    public PeoplePage deleteUserAndReturnToPeoplePage() {
        deleteUser.click();
        yesButton.click();
        goToPeoplePage.click();
        return new PeoplePage(wd);
    }
}
