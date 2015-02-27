package JenkinsTests;

import JenkinsDashboard.Pages.*;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.UnhandledAlertException;
import utils.web.JenkinsAPI;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class MyTests extends BaseTest implements Generators {

    private String existingUserName = getExistingUserName();
    private String existingUserFulName = getExistingUserFulName();
    private String existingProjectName = getExistingProjectName();
    private String password = getPassword();

    private String newUserName = generateNewUserName();
    private String newUserFullName = generateNewUserFullName();
    private String newUserEmail = generateNewUserEmail();
    private String newProjectName = generateNewProjectName();
    private String newProjectDescription = generateNewProjectDescription();

    private LogInPage loginPage;

    @Before
    public void before() {
        loginPage = new LogInPage(wd);
    }

    @After
    public void logOutIfLoggedIn() {
        loginPage.logOut();
    }

    @AfterClass
    public static void afterCurrentClass() {
        UserHomePage userHomePage = new UserHomePage(wd).get();
        userHomePage.deleteTestProjects();
        userHomePage.deleteTestUsers();
    }

    @Test
    public void LogInTest() {
        loginPage.get();
        UserHomePage userHomePage = loginPage.logIn(existingUserName, password);
        assertTrue(userHomePage.getUserName().contains(existingUserFulName));
    }

    @Test
    public void LogInWrongCredentials() {
        LogInPage logInPage = new LogInPage(wd).get();
        WrongLogInPage wrongLogInPage = logInPage.logInWithWrongCredentials("asfaf", "segwf");
        assertTrue(wrongLogInPage.checkForError());
    }

    @Test
    public void SignUpNewUserTest() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        UserHomePage userHomePage = signUpPage.signUpNewUser(newUserName, password, newUserFullName, newUserEmail);
        assertTrue(userHomePage.getUserName().contains(newUserFullName));
    }

    @Test
    public void SignUpExistingNameUser() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        signUpPage.signUpExistingNameUser(existingUserName, password, newUserFullName, newUserEmail);
        assertTrue(signUpPage.checkForError());
    }

    @Test
    public void openExistingProject() {
        UserHomePage userHomePage = new UserHomePage(wd).get();
        ProjectPage projectPage = userHomePage.openProject(existingProjectName);
        assertThat("Project not found", projectPage.getMainPanelText(), Matchers.containsString(existingProjectName));
        userHomePage = projectPage.backToDashboard();
    }

    @Test
    public void newProjectTest() {
        NewItemPage newItemPage = new NewItemPage(wd).get();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(newProjectName);
        freestylePropertiesPage.addDescription(newProjectDescription);
        ProjectPage projectPage = freestylePropertiesPage.save();
//
//        UserHomePage userHomePage = projectPage.backToDashboard();
//
//        boolean projectfound = false;
//        for (String currentProject : userHomePage.getProjectsNames()) {
//            if (currentProject.equals(newProjectName)) {
//                projectfound = true;
//                break;
//            }
//        }
//        assertTrue(projectfound);
    }

    @Test
    public void buildExisting() {
        ProjectPage projectPage = new ProjectPage(wd).get();
        projectPage.buildProject();
        BuildsPage buildsPage = projectPage.openLatestBuild();
        assertTrue(buildsPage.getConsoleOutputText().contains("SUCCESS"));
    }

    @Test
    public void testSearch() {
        Arrays.stream(new HomePage(wd).get().search("Exis"))
                .forEach(System.out::println);
    }

    @Test
    public void testProjectSearch() {
        HomePage homePage = new HomePage(wd).get();
        ProjectPage projectPage = homePage.searchForProject("Exis");
    }

    @Test
    public void testPeopleSearch() {
        PeoplePage peoplePage = new PeoplePage(wd).get();
        peoplePage.deleteTestUsers();
        System.out.println(" ");
    }

    @Test
    public void testNewProjectWithPing() {
        NewItemPage newItemPage = new NewItemPage(wd).get();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(newProjectName);
        freestylePropertiesPage.addDescription(generateNewProjectDescription());
        freestylePropertiesPage.checkTriggerBuildsRemotely();
        freestylePropertiesPage.addWindowsBatchCommand();

        ProjectPage projectPage = freestylePropertiesPage.save();

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int builds = projectPage.countBuildsBeforeAction();
                projectPage.waitForNewBuild(builds);
            }
        });
        newThread.start();

        new JenkinsAPI().sendPostRequest(newProjectName, "build");
        try{
            newThread.join();
        } catch (InterruptedException e){}
        projectPage.waitForBuildToEnd();
    }
}
