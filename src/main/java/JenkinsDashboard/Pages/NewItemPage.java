package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.PagesURLs.NEW_ITEM_PAGE;

/**
    *This class is the same as NewItemPage, but extended from SecuredPage abstract class.
 */
public class NewItemPage extends SecuredPage<NewItemPage> {

    public NewItemPage(WebDriver wd) {
        super(wd);
    }

    @FindBy (id = "name")
    private WebElement itemName;

    @FindBy (css = "input[type='radio'][value*=FreeStyleProject]" ) // css = "input[type='radio'][value*=FreeStyleProject]"   xpath = "//*[@id='main-panel-content']/form/table/tbody/tr[3]/td/input"
    private WebElement freestyleProject;

    @FindBy (id = "ok-button")
    private WebElement oKbutton;

    public NewItemPage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    @Override
    public String getPageURL() {
        return NEW_ITEM_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        itemName.isDisplayed();
    }

    public FreestylePropertiesPage setUpFreestyleProject(String itemName){
        this.itemName.sendKeys(itemName);
        freestyleProject.click();
        oKbutton.click();
        return new FreestylePropertiesPage(wd, true){
            @Override
            public String getProjectName() {
                return itemName;
            }
        };
    }
}
