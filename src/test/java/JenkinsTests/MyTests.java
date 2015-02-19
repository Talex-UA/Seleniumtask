package JenkinsTests;

import JenkinsDashboard.Pages.*;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.jws.soap.SOAPBinding;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class MyTests extends BaseTest {

    private static final String NEW_USER_NAME = "New2-Talex-UA";
    private static final String NEW_USER_PASSWORD = "qwerty1234";
    private static final String NEW_USER_FULL_NAME = "Alexander";
    private static final String NEW_USER_EMAIL = "talexua91@gmail.com";

    private static final String EXISTING_USER_NAME = "Talex-UA";
    private static final String EXISTING_USER_PASSWORD = "qwerty1234";
    private static final String EXISTING_USER_FULL_NAME = "Alexander";
    private static final String EXISTING_USER_EMAIL = "talexua91@gmail.com";

    private static final String EXISTING_PROJECT_NAME = "fwqegwqe";

    private static final String NEW_PROJECT_NAME = "Sasha_NewProject_Test";
    private static final String DESCRIPTION = "This is my new project";


    @Test
    public void LogInTest() {
        HomePage homePage = new HomePage(wd);
        LogInPage logInPage = homePage.gotoLogInPage();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME, EXISTING_USER_PASSWORD);
        assertTrue(wd.findElement(By.xpath("//*[@id='header']/div[2]/span/a[1]/b")).getText().contains(EXISTING_USER_FULL_NAME));
        userHomePage.logOut();
    }

    @Test
    public void LogInWrongCredentials(){
        HomePage homePage = new HomePage(wd);
        LogInPage logInPage = homePage.gotoLogInPage();
        WrongLogInPage wrongLogInPage = logInPage.logInWithWrongCredentials("asfaf","segwf");
        assertTrue(wd.findElement(By.id("main-panel-content")).getText().contains("Invalid login information. Please try again."));
        homePage = wrongLogInPage.gotoHomePage();
    }

    @Test
    public void SignUpNewUserTest() {
        HomePage homePage = new HomePage(wd);
        SignUpPage signUpPage = homePage.gotoSignUpPage();
        UserHomePage userHomePage = signUpPage.signUpNewUser(NEW_USER_NAME, NEW_USER_PASSWORD, NEW_USER_FULL_NAME, NEW_USER_EMAIL);
        assertTrue(wd.getTitle().contains("Dashboard"));
        userHomePage.logOut();
    }

    @Test
    public void SignUpExistingNameUser() {
        HomePage homePage = new HomePage(wd);
        SignUpPage signUpPage = homePage.gotoSignUpPage();
        signUpPage.signUpExistingNameUser(EXISTING_USER_NAME, EXISTING_USER_PASSWORD, EXISTING_USER_FULL_NAME, EXISTING_USER_EMAIL);
        assertTrue(wd.findElement(By.id("main-panel-content")).getText().contains("User name is already taken"));
        homePage=signUpPage.gotoHomePage();
    }

    @Test
    public void openExistingProject(){
        HomePage homePage = new HomePage(wd);
        LogInPage logInPage = homePage.gotoLogInPage();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME,EXISTING_USER_PASSWORD);
        ProjectPage projectPage = userHomePage.openProject(EXISTING_PROJECT_NAME);
        assertTrue(wd.findElement(By.id("main-panel-content")).getText().contains(EXISTING_PROJECT_NAME));
        userHomePage=projectPage.backToDashboard();
        userHomePage.logOut();
    }

    @Test
    public void createNewProject() {
        HomePage homePage = new HomePage(wd);
        LogInPage logInPage = homePage.gotoLogInPage();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME, EXISTING_USER_PASSWORD);
        NewItemPage newItemPage = userHomePage.createNewItem();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(NEW_PROJECT_NAME);
        freestylePropertiesPage.addDescription(DESCRIPTION);
        ProjectPage projectPage = freestylePropertiesPage.save();
        projectPage.buildNow();
        userHomePage = projectPage.backToDashboard();

        boolean projectfound = false;
        for (WebElement currentProject : userHomePage.getProjects()) {
            if (currentProject.getText().equals(NEW_PROJECT_NAME)) {
                projectfound = true;
                break;
            }
        }
        assertTrue(projectfound);
        userHomePage.logOut();
    }
}
