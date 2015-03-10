package JenkinsDashboard.Pages;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.web.JenkinsAPI;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class UserHomePage extends SecuredPage<UserHomePage> {

    @FindBy(css = ".icon-new-package.icon-md")
    // css = ".icon-new-package.icon-md"    xpath = "//*[@id='tasks']/div[1]/a[2]"
    private WebElement newItem;

    @FindBy(css = ".task [href*='asynch']") // css = ".icon-user.icon-md"  xpath = "//*[@id='tasks']/div[2]/a[2]"
    private WebElement peoplePage;

    @FindBy(css = ".model-link.inside.inverse>b")
    // css = ".model-link.inside.inverse>b"     xpath = "//*[@id='header']/div[2]/span/a[1]/b"
    private WebElement userName;
    @FindBy(xpath = "//td[3]/a") // xpath = "//td[3]/a"    className = "model-link"
    private List<WebElement> projects;

    public UserHomePage(WebDriver wd) {
        super(wd);
    }

    public UserHomePage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    @Override
    public String getPageURL() {
        return HOST;
    }

    @Override
    protected void checkUniqueElements() throws Error {
        assertThat(peoplePage.isDisplayed(), Matchers.is(true));
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
        for (WebElement project : projects) {
            if (project.getText().equals(projectName)) {
                project.click();
                break;
            }
        }
        return new ProjectPage(wd, true){
            @Override
            public String getProjectName(){
                return projectName;
            }
        };
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

    public List<String> getProjectsNamesByTemplate(String template) {
        List<String> projectNames = new ArrayList<>();
        for (WebElement project : projects) {
            if (project.getText().contains(template)) {
                projectNames.add(project.getText());
            }
        }
        return projectNames;
    }

    public void deleteTestProjects() {
        openTab("All");
        List<String> projects = getProjectsNamesByTemplate("Test-Project ");
        for (String project : projects) {
            new JenkinsAPI().sendPostRequest(project, "doDelete");
        }
    }

    public void deleteTestUsers() {
        PeoplePage peoplePage = new PeoplePage(wd){
            @Override
            public String getPageURL() {
                return HOST + "asynchPeople/";
            }
        }.get();
        peoplePage.deleteTestUsers();
    }

    public UserHomePage openTab(String tabName) {
        try {
            wd.findElement(By.cssSelector(".tab>a[href*=" + tabName + "]")).click();
            WebElement passwordField = (new WebDriverWait(wd, 20)).until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver d) {
                    return d.findElement(By.cssSelector(".tab.active>a[href*=" + tabName + "]"));
                }
            });
        } catch (NoSuchElementException e) {
            log.info("No such tab");
        }
        return this;
    }

    public String getPageTitle() {
        return wd.getTitle();
    }
}
