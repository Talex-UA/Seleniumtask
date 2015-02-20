package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewItemPage extends Page<NewItemPage>{

    private final WebDriver wd;

    public NewItemPage(WebDriver wd) {
        super(wd);
        this.wd=wd;
        PageFactory.initElements(wd,this);
    }

    @FindBy (id = "name")
    WebElement ItemName;

    @FindBy (xpath = "//*[@id='main-panel-content']/form/table/tbody/tr[3]/td/input")
    WebElement FreestyleProject;

    @FindBy (id = "ok-button")
    WebElement OKbutton;

    @Override
    public String getPageURL() {
        return null;
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    public FreestylePropertiesPage setFreestyleProject(String itemName){
        ItemName.sendKeys(itemName);
        FreestyleProject.click();
        OKbutton.click();
        return new FreestylePropertiesPage(wd);
    }

}
