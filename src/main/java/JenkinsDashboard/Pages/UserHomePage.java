package JenkinsDashboard.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import utils.web.JenkinsAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static utils.PagesURLs.*;

public class UserHomePage extends SecuredPage<UserHomePage> {

    @FindBy(css = ".icon-new-package.icon-md") // css = ".icon-new-package.icon-md"    xpath = "//*[@id='tasks']/div[1]/a[2]"
    private WebElement newItem;

    @FindBy (css = ".icon-user.icon-md") // css = ".icon-user.icon-md"  xpath = "//*[@id='tasks']/div[2]/a[2]"
    private WebElement peoplePage;

    @FindBy(css = ".model-link.inside.inverse>b") // css = ".model-link.inside.inverse>b"     xpath = "//*[@id='header']/div[2]/span/a[1]/b"
    private WebElement userName;
    @FindBys({@FindBy(xpath = "//td[3]/a")}) // xpath = "//td[3]/a"    className = "model-link"
    private List<WebElement> projects;

    public UserHomePage(WebDriver wd) {
        super(wd);
    }

    public UserHomePage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    @Override
    public String getPageURL() {
            return USER_HOME_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws Error {
        peoplePage.isDisplayed();
    }

    public UserHomePage gotoUserHomePage() {
        jenkinsIcon.click();
        return new UserHomePage(wd, true);
    }

    public NewItemPage createNewItem() {
        newItem.click();
        return new NewItemPage(wd, true);
    }

    public ProjectPage openProject(String projectName) {
        try {
            projects.stream().filter(project -> project.getText().equals(projectName)).forEach(a -> a.click());
        } catch (StaleElementReferenceException e){

        }

//        for (WebElement project : projects) {
//            if (project.getText().equals(projectName)) {
//                project.click();
//                break;
//            }
//        }
        return new ProjectPage(wd, true);
    }

    public String getUserName() {
        return userName.getText();
    }

    public List<String> getProjectsNames() {
        List<String> projectNames = new ArrayList<>();
        for (WebElement project : projects) {
            projectNames.add(project.getText());
        }
        return projectNames;
    }

    public List<String> getProjectsNamesByTemplate(String template){
        List<String> projectNames = new ArrayList<>();
        for (WebElement project : projects) {
            if (project.getText().contains(template)){
                projectNames.add(project.getText());
            }
        }
        return projectNames;
    }

    public void deleteTestProjects() {
        List<String> projects = getProjectsNamesByTemplate("Test-Project ");
        for (String project : projects) {
            new JenkinsAPI().sendPostRequest(project, "doDelete");
        }
    }

    public void deleteTestUsers(){
        PeoplePage peoplePage = new PeoplePage(wd).get();
        peoplePage.deleteTestUsers();
    }

    public UserHomePage openTab(String tabName){
        try {
            wd.findElement(By.cssSelector(".tab>a[href*="+tabName+"]")).click();
        } catch (NoSuchElementException e){

        }
        return new UserHomePage(wd, true);
    }

    public String getPageTitle() {
        return wd.getTitle();
    }
}
