package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserHomePage extends Page {

    private final WebDriver wd;

    public UserHomePage(WebDriver wd) {
        super(wd);
        this.wd=wd;
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//*[@id='header']/div[2]/span/a[2]")
    WebElement logOut;

    @FindBy(xpath = "//*[@id='tasks']/div[1]/a[2]")
    WebElement newItem;


    @FindBys({ @FindBy(className = "model-link") })
    private List<WebElement> Projects;

    public List<WebElement> getProjects(){
        return Projects;
    }

    @Override
    public String getPageURL() {
        return null;
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }

    public void logOut(){
        logOut.click();
    };

    public UserHomePage gotoUserHomePage() {
        JenkinsIcon.click();
        return new UserHomePage(wd);
    }

    public NewItemPage createNewItem(){
        newItem.click();
        return new NewItemPage(wd);
    }

    public ProjectPage openProject(String projectName){
        for (WebElement project:Projects){
            if (project.getText().equals(projectName)){
                project.click();
                break;
            }
        }
        return new ProjectPage(wd);
    }
}
