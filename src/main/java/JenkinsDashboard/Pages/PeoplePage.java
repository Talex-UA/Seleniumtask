package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends SecuredPage<PeoplePage> {

    @FindBys({@FindBy(xpath = "//td[2]/a")})
    List<WebElement> people;

    @FindBy(id = "main-panel-content")
    WebElement mainPanel;
    private UserPage userPage;
    private PeoplePage peoplePage;

    public PeoplePage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/asynchPeople/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        mainPanel.getText().contains("Includes all known “users”");
    }

    public List<String> getPeopleNames() {
        List<String> projectNames = new ArrayList<>();
        for (WebElement project : people) {
            projectNames.add(project.getText());
        }
        return projectNames;
    }

    public List<String> getPeopleNameByTemplate(String template){
        List<String> peopleNames = new ArrayList<>();
        for (WebElement currentName : people) {
            if (currentName.getText().contains(template)){
                peopleNames.add(currentName.getText());
            }
        }
        return peopleNames;
    }

    public void deleteTestUsers() {
        List<String> names = getPeopleNameByTemplate("Test-Name ");
        for (String currentUser : names) {
            userPage = openUser(currentUser);
            peoplePage = userPage.deleteUserAndReturnToPeoplePage();
            System.out.println("User Deleted: " + currentUser);
        }
    }

    private UserPage openUser(String userName) {
        for (WebElement currentName : people) {
            if (currentName.getText().equals(userName)) {
                currentName.click();
                break;
            }
        }
        return new UserPage(wd);
    }
}
