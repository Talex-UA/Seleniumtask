package JenkinsDashboard.Pages;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Generators.getExistingUserName;

public class UserPage extends SecuredPage<UserPage>{

    @FindBy (css = "[type=button]") // css = "[type=button]"  id="yui-gen1-button"
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
        assertThat(deleteUser.isDisplayed(), Matchers.is(true));
    }

    public PeoplePage deleteUserAndReturnToPeoplePage() {
        deleteUser.click();
        yesButton.click();
        goToPeoplePage.click();
        return new PeoplePage(wd, true).get();
    }

    public void deleteUser(){
        deleteUser.click();
        WebElement yesButton = (new WebDriverWait(wd, 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector("[type=button]"));
            }
        });
        yesButton.click();
    }
}
