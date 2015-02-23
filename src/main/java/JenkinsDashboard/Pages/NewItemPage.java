package JenkinsDashboard.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
    *This class is the same as NewItemPage, but extended from SecuredPage abstract class.
 */
public class NewItemPage extends SecuredPage<NewItemPage> {

    public NewItemPage(WebDriver wd) {
        super(wd);
    }

    @FindBy (id = "name")
    WebElement itemName;

    @FindBy (xpath = "//*[@id='main-panel-content']/form/table/tbody/tr[3]/td/input")
    WebElement freestyleProject;

    @FindBy (id = "ok-button")
    WebElement oKbutton;

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/view/All/newJob";
    }

    @Override
    protected void checkUniqueElements() throws NoSuchElementException {
        itemName.isDisplayed();
    }

    public FreestylePropertiesPage setFreestyleProject(String itemName){
        this.itemName.sendKeys(itemName);
        freestyleProject.click();
        oKbutton.click();
        return new FreestylePropertiesPage(wd);
    }
}
