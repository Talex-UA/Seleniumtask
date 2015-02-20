package JenkinsTests;

import JenkinsDashboard.Pages.*;
import junit.framework.Assert;
import org.junit.After;
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

    @After
    public void logOutIfLoggedIn(){
    if (LogInPage.isLoggedIn) LogInPage.logout();
    }

    @Test
    public void LogInTest() {
        LogInPage logInPage = new LogInPage(wd).get();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME, EXISTING_USER_PASSWORD);
        assertTrue(userHomePage.getUserName().contains(EXISTING_USER_FULL_NAME));
//        userHomePage.logOut();
    }

    @Test
    public void LogInWrongCredentials(){
        LogInPage logInPage = new LogInPage(wd).get();
        WrongLogInPage wrongLogInPage = logInPage.logInWithWrongCredentials("asfaf","segwf");
        assertTrue(wrongLogInPage.getMainPanelText().contains("Invalid login information. Please try again."));
        HomePage homePage = wrongLogInPage.gotoHomePage();
    }

    @Test
    public void SignUpNewUserTest() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        UserHomePage userHomePage = signUpPage.signUpNewUser(NEW_USER_NAME, NEW_USER_PASSWORD, NEW_USER_FULL_NAME, NEW_USER_EMAIL);
//        userHomePage.logOut();
    }

    @Test
    public void SignUpExistingNameUser() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        signUpPage.signUpExistingNameUser(EXISTING_USER_NAME, EXISTING_USER_PASSWORD, EXISTING_USER_FULL_NAME, EXISTING_USER_EMAIL);
        assertTrue(signUpPage.getMainPanelText().contains("User name is already taken"));
        HomePage homePage=signUpPage.gotoHomePage();
    }

    @Test
    public void openExistingProject(){
        LogInPage logInPage = new LogInPage(wd).get();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME,EXISTING_USER_PASSWORD);
        ProjectPage projectPage = userHomePage.openProject(EXISTING_PROJECT_NAME);
        assertTrue(projectPage.getMainPanelText().contains(EXISTING_PROJECT_NAME));
        userHomePage=projectPage.backToDashboard();
//        userHomePage.logOut();
    }

    @Test
    public void createNewProject() {
        LogInPage logInPage = new LogInPage(wd).get();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME, EXISTING_USER_PASSWORD);
        NewItemPage newItemPage = userHomePage.createNewItem();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(NEW_PROJECT_NAME);
        freestylePropertiesPage.addDescription(DESCRIPTION);
        ProjectPage projectPage = freestylePropertiesPage.save();
        projectPage.buildNow();
        userHomePage = projectPage.backToDashboard();

        boolean projectfound = false;
        for (String currentProject : userHomePage.getProjectsNames()) {
            if (currentProject.equals(NEW_PROJECT_NAME)) {
                projectfound = true;
                break;
            }
        }
        assertTrue(projectfound);
//        userHomePage.logOut();
    }
}
