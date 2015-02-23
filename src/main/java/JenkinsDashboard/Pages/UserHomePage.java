package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;

public class UserHomePage extends SecuredPage<UserHomePage> {

    private UserHomePage userHomePage;
    private ProjectPage projectPage;
    private PeoplePage peoplePage;

    @FindBy(xpath = "//*[@id='tasks']/div[1]/a[2]")
    WebElement newItem;

    @FindBy(xpath = "//*[@id='header']/div[2]/span/a[1]/b")
    WebElement userName;
    @FindBys({@FindBy(className = "model-link")})
    List<WebElement> projects;

    public UserHomePage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
            return "http://seltr-kbp1-1.synapse.com:8080/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        newItem.isDisplayed();
        userName.isDisplayed();
        for (WebElement project: projects) project.isDisplayed();
    }

    public UserHomePage gotoUserHomePage() {
        jenkinsIcon.click();
        return new UserHomePage(wd);
    }

    public NewItemPage createNewItem() {
        newItem.click();
        return new NewItemPage(wd);
    }

    public ProjectPage openProject(String projectName) {
        for (WebElement project : projects) {
            if (project.getText().equals(projectName)) {
                project.click();
                break;
            }
        }
        return new ProjectPage(wd);
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
            projectPage = openProject(project);
            userHomePage = projectPage.deleteProject();
            System.out.println("Project Deleted: " + project);
        }
    }

    public void deleteTestUsers(){
        peoplePage = new PeoplePage(wd).get();
        peoplePage.deleteTestUsers();
    }

}
