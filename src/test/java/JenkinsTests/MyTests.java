package JenkinsTests;

import JenkinsDashboard.Pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class MyTests extends BaseTest implements Generators {

    private static final String PASSWORD = "qwerty1234";
    public static final String EXISTING_USER_NAME = "OleksandrA";
    private static final String EXISTING_USER_FULL_NAME = "Alexander Agafonov";
    private static final String EXISTING_PROJECT_NAME = "ExistingProject";

    private String newUserName= generateNewUserName();
    private String newUserFullName=generateNewUserFullName();
    private String newUserEmail=generateNewUserEmail();
    private String newProjectName=generateNewProjectName();
    private String newProjectDescription=generateNewProjectDescription();


    private LogInPage loginPage;

    @Before
    public void before() {
        loginPage = new LogInPage(wd);
    }

    @After
    public void logOutIfLoggedIn() {
        loginPage.logOut();
    }

    @Test
    public void LogInTest() {
        loginPage.get();
        UserHomePage userHomePage = loginPage.logIn(EXISTING_USER_NAME, PASSWORD);
        assertTrue(userHomePage.getUserName().contains(EXISTING_USER_FULL_NAME));
    }

    @Test
    public void LogInWrongCredentials() {
        LogInPage logInPage = new LogInPage(wd).get();
        WrongLogInPage wrongLogInPage = logInPage.logInWithWrongCredentials("asfaf", "segwf");
        assertTrue(wrongLogInPage.getMainPanelText().contains("Invalid login information. Please try again."));
    }

    @Test
    public void SignUpNewUserTest() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        UserHomePage userHomePage = signUpPage.signUpNewUser(newUserName, PASSWORD, newUserFullName, newUserEmail);
    }

    @Test
    public void SignUpExistingNameUser() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        signUpPage.signUpExistingNameUser(EXISTING_USER_NAME, PASSWORD, newUserFullName, newUserEmail);
        assertTrue(signUpPage.getMainPanelText().contains("User name is already taken"));
    }

    @Test
    public void openExistingProject() {
        LogInPage logInPage = new LogInPage(wd).get();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME, PASSWORD);
        ProjectPage projectPage = userHomePage.openProject(EXISTING_PROJECT_NAME);
        assertTrue(projectPage.getMainPanelText().contains(EXISTING_PROJECT_NAME));
        userHomePage = projectPage.backToDashboard();
    }

    @Test
    public void createNewProject() {
        LogInPage logInPage = new LogInPage(wd).get();
        UserHomePage userHomePage = logInPage.logIn(EXISTING_USER_NAME, PASSWORD);
        NewItemPage newItemPage = userHomePage.createNewItem();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(newProjectName);
        freestylePropertiesPage.addDescription(newProjectDescription);
        ProjectPage projectPage = freestylePropertiesPage.save();
        projectPage.buildNow();
        userHomePage = projectPage.backToDashboard();

        boolean projectfound = false;
        for (String currentProject : userHomePage.getProjectsNames()) {
            if (currentProject.equals(newProjectName)) {
                projectfound = true;
                break;
            }
        }
        assertTrue(projectfound);
    }

    @Test
    public void SubPageTest() throws InterruptedException {
        SubPageSecured subPageSecured = (SubPageSecured) new SubPageSecured(wd).get();
        Thread.sleep(5000);
    }

}
