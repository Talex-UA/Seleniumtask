package JenkinsDashboard.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static utils.PagesURLs.*;

public class PeoplePage extends SecuredPage<PeoplePage> {

    @FindBys({@FindBy(id = "User-ID" )}) // this ID is given to needed elements by givePeopleElementsID method. original locators: css = "#people td:nth-of-type(2)>a"   xpath = "//td[2]/a"
    private List<WebElement> people;

    @FindBy(id = "main-panel-content")
    private WebElement mainPanel;

    public PeoplePage(WebDriver wd) {
        super(wd);
    }

    public PeoplePage(WebDriver wd, boolean b) {
        super(wd, b);
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
        return PEOPLE_PAGE;
    }

    @Override
    protected void checkUniqueElements() throws Error {
        mainPanel.getText().contains("Includes all known “users”");
        givePeopleElementsID();
    }

    public List<String> getPeopleNameByTemplate(String template){
        List<String> peopleNames = new ArrayList<>();
        people.stream()
                .filter(currentName -> currentName.getText().contains(template))
                .forEach(name -> peopleNames.add(name.getText()));
        return peopleNames;
    }

    public void deleteTestUsers() {
        getPeopleNameByTemplate("Test-Name ").stream().forEach(currentUser -> openUser(currentUser)
                .deleteUserAndReturnToPeoplePage());
    }

    public UserPage openUser(String userName) {
        try {
            people.stream()
                    .filter(currentName -> currentName.getText().equals(userName))
                    .forEach(name -> name.click());
        } catch (StaleElementReferenceException e){

        }
        return new UserPage(wd, true);
    }
}
