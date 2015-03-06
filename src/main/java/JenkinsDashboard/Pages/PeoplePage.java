package JenkinsDashboard.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends SecuredPage<PeoplePage> {

    @FindBy(name = "TestUser")
    // this name is given to needed elements by givePeopleElementsID method. original locators: css = "#testUsers td:nth-of-type(2)>a"   xpath = "//td[2]/a"
    private List<WebElement> testUsers;

    @FindBy(id = "main-panel-content")
    private WebElement mainPanel;

    @FindBy(css = "div[style='float:right'] [href*=asynch]")
    WebElement showAllPeople;

    public PeoplePage(WebDriver wd) {
        super(wd);
    }

    public PeoplePage(WebDriver wd, boolean b) {
        super(wd, b);
    }

    /**
     * This method gives name = "User" to all elements in User column of the Users table.
     */
    private void giveTestElementsName() {
        JavascriptExecutor js = (JavascriptExecutor) wd;
        List<WebElement> people = wd.findElements(By.cssSelector("[id*=person] a"));
        String user_name = "TestUser";
        String script = "arguments[0].setAttribute('name'," + "\'" + user_name + "\'" + ")";
        for (int i = 0; i < people.size() - 1; i++) {
            if (people.get(i).getText().contains("Test-Name ")) {
                js.executeScript(script, people.get(i));
            }
        }
    }

    public List<WebElement> getPeople() {
        giveTestElementsName();
        return new ArrayList<>(testUsers);
    }

    @Override
    public String getPageURL() {
        return HOST + "view/Training/asynchPeople/";
    }

    @Override
    protected void checkUniqueElements() throws Error {
        mainPanel.getText().contains("Includes all known “users”");
    }

    public List<String> getPeopleNameByTemplate(String template) {
        List<String> peopleNames = new ArrayList<>();
        getPeople().stream()
                .filter(currentName -> currentName.getText().contains(template))
                .forEach(name -> peopleNames.add(name.getText()));
        return peopleNames;
    }

    public void deleteTestUsers() {
        //        for (WebElement currentName:getPeople()){
//            if (currentName.getText().contains("Test-Name")){
//                currentName.click();
//                UserPage userPage = new UserPage(wd, true) {
//                    @Override
//                    public String getUserName() {
//                        return currentName.getText();
//                    }
//                };
//                userPage.deleteUserAndReturnToPeoplePage();
//                showAllPeople();
//            }
        for (String currentName:getPeopleNameByTemplate("Test-Name ")){
            openUser(currentName).deleteUserAndReturnToPeoplePage().showAllPeople();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public UserPage openUser(String userName) {
        for (WebElement el : getPeople()) {
            if (el.getText().equals(userName)) {
                el.click();
                break;
            }
        }
        return new UserPage(wd, true) {
            @Override
            public String getUserName() {
                return userName;
            }
        };
    }

    public PeoplePage showAllPeople() {
        showAllPeople.click();
        return this;
    }
}
