package JenkinsDashboard.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends SecuredPage<PeoplePage> {

    @FindBy(name = "User" ) // this name is given to needed elements by givePeopleElementsID method. original locators: css = "#people td:nth-of-type(2)>a"   xpath = "//td[2]/a"
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
     * This method gives name = "User" to all elements in User column of the people table.
     */
    private void givePeopleElementsName() {
        JavascriptExecutor js = (JavascriptExecutor) wd;
        List<WebElement> people = wd.findElements(By.cssSelector("[id*=person] a"));
        String user_name = "User";
        String script = "arguments[0].setAttribute('name'," + "\'" + user_name + "\'"+")";
        for (int i = 1; i < people.size()-1; i+=3) {
            js.executeScript(script,people.get(i));
        }
    }

    public List<WebElement> getPeople(){
        givePeopleElementsName();
        return new ArrayList<>(people);
    }

    @Override
    public String getPageURL() {
        return  HOST +"asynchPeople/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        mainPanel.getText().contains("Includes all known “users”");
    }

    public List<String> getPeopleNameByTemplate(String template){
        List<String> peopleNames = new ArrayList<>();
        getPeople().stream()
                .filter(currentName -> currentName.getText().contains(template))
                .forEach(name -> peopleNames.add(name.getText()));
        return peopleNames;
    }

    public void deleteTestUsers() {
        getPeopleNameByTemplate("Test-Name ").stream().forEach(currentUser -> openUser(currentUser)
                .deleteUserAndReturnToPeoplePage());
    }

    public UserPage openUser(String userName) {

        for (WebElement el:getPeople()){
            if(el.getText().equals(userName)){
                el.click();
                break;
            }
        }
        return new UserPage(wd, true){
            @Override
            public String getUserName(){
                return userName;
            }
        };
    }
}
