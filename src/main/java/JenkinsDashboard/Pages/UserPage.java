package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Generators.getExistingUserName;

public class UserPage extends SecuredPage<UserPage>{

    @FindBy (id="yui-gen1-button")
    private WebElement yesButton;

    @FindBy (css = ".icon-edit-delete.icon-md") // css = ".icon-edit-delete.icon-md"   xpath = "//*[@id='tasks']/div[6]/a[2]"
    private WebElement deleteUser;

    @FindBy (css = ".icon-user.icon-md") // css = ".icon-user.icon-md"  xpath = "//*[@id='tasks']/div[2]/a[2]"
    private WebElement goToPeoplePage;

    public UserPage(WebDriver wd) {
        super(wd);
    }

    public UserPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    public String getUserName() {
        return getExistingUserName();
    }

    @Override
    public String getPageURL() {
        return HOST + "user/" + getUserName().replace(" ","%20").toLowerCase() + "/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        deleteUser.isDisplayed();
    }

    public PeoplePage deleteUserAndReturnToPeoplePage() {
        deleteUser.click();
        yesButton.click();
        goToPeoplePage.click();
        return new PeoplePage(wd, true).get();
    }
}
