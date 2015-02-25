package JenkinsDashboard.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends SecuredPage<PeoplePage> {

    private UserPage userPage;
    private PeoplePage peoplePage;

    @FindBys({@FindBy(id = "User-ID" )}) // this ID is given to needed elements by givePeopleElementsID method. original locators: css = "#people td:nth-of-type(2)>a"   xpath = "//td[2]/a"
    List<WebElement> people;

    @FindBy(id = "main-panel-content")
    WebElement mainPanel;

    public PeoplePage(WebDriver wd) {
        super(wd);
    }

    /**
     * This method gives id = "User-ID" to all elements in User ID column of the people table.
     */
    private void givePeopleElementsID() {
        JavascriptExecutor js = (JavascriptExecutor) wd;
        List<WebElement> people = wd.findElements(By.cssSelector("[id*=person] a"));
        String user_Id = "User-ID";
        String script = "arguments[0].setAttribute('id'," + "\'" + user_Id + "\'"+")";
        for (int i = 1; i < people.size()-1; i+=3) {
            js.executeScript(script,people.get(i));
        }
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/asynchPeople/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        mainPanel.getText().contains("Includes all known “users”");
        givePeopleElementsID();
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
        }
    }

    public UserPage openUser(String userName) {
        for (WebElement currentName : people) {
            if (currentName.getText().equals(userName)) {
                currentName.click();
                break;
            }
        }
        return new UserPage(wd);
    }
}
