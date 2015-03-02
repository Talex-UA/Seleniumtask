package JenkinsTests;

import JenkinsDashboard.Pages.*;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import utils.web.JenkinsAPI;

import static utils.Generators.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class MyTests extends BaseTest {

    private String existingUserName = getExistingUserName();
    private String existingUserFulName = getExistingUserFulName();
    private String existingProjectName = getExistingProjectName();
    private String password = getPassword();

    private String newUserFullName = generateNewUserFullName();
    private String newUserEmail = generateNewUserEmail();
    private String newProjectName = generateNewProjectName();

    private String tabName = generateString(10);

    private LogInPage loginPage;

    @Before
    public void before() {
        loginPage = new LogInPage(wd);
    }

    @After
    public void logOutIfLoggedIn() {
        wd.manage().deleteAllCookies();
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
        assertThat(userHomePage.getUserName(), Matchers.containsString(existingUserFulName));
    }

    @Test
    public void LogInWrongCredentials() {
        LogInPage logInPage = new LogInPage(wd).get();
        WrongLogInPage wrongLogInPage = logInPage.logInWithWrongCredentials("asfaf", "segwf");
        assertThat(wrongLogInPage.checkForError(), Matchers.is(true));
    }

    @Test
    public void SignUpNewUserTest() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        UserHomePage userHomePage = signUpPage.signUpNewUser(generateNewUserName(), password, newUserFullName, newUserEmail);
        assertThat(userHomePage.getUserName(), Matchers.containsString(newUserFullName));
    }

    @Test
    public void SignUpExistingNameUser() {
        SignUpPage signUpPage = new SignUpPage(wd).get();
        signUpPage.signUpExistingNameUser(existingUserName, password, newUserFullName, newUserEmail);
        assertThat(signUpPage.checkForError(), Matchers.is(true));
    }

    @Test
    public void openExistingProject() {
        UserHomePage userHomePage = new UserHomePage(wd).get();
        ProjectPage projectPage = userHomePage.openProject(existingProjectName);
        assertThat("Project not found", projectPage.getMainPanelText(), Matchers.containsString(existingProjectName));
    }

    @Test
    public void newProjectTest() {
        NewItemPage newItemPage = new NewItemPage(wd).get();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(newProjectName);
        freestylePropertiesPage.addDescription(generateNewProjectDescription());
        ProjectPage projectPage = freestylePropertiesPage.save();

        UserHomePage userHomePage = projectPage.backToDashboard();

        boolean projectfound = false;
        for (String currentProject : userHomePage.getProjectsNames()) {
            if (currentProject.equals(newProjectName)) {
                projectfound = true;
                break;
            }
        }
        assertThat("Project is not found",projectfound,Matchers.is(true));
    }

    @Test
    public void buildExisting() {
        ProjectPage projectPage = new ProjectPage(wd).get();
        projectPage.buildProject();
        BuildsPage buildsPage = projectPage.openLatestBuild();
        assertThat(buildsPage.getConsoleOutputText(), Matchers.containsString("SUCCESS"));
    }

    @Test
    public void testSearch() {
        assertThat(new HomePage(wd).get().getSearchResults("a").length, Matchers.notNullValue());
    }

    @Test
    public void testProjectSearch() {
        HomePage homePage = new HomePage(wd).get();
        ProjectPage projectPage = homePage.searchForProject("Exis");
        assertThat("Wrong project",projectPage.getMainPanelText(),Matchers.containsString(getExistingProjectName()));
    }

    @Test
    public void testNewProjectWithPing() {
        NewItemPage newItemPage = new NewItemPage(wd).get();
        FreestylePropertiesPage freestylePropertiesPage = newItemPage.setFreestyleProject(newProjectName);
        freestylePropertiesPage.addDescription(generateNewProjectDescription());
        freestylePropertiesPage.checkTriggerBuildsRemotely();
        freestylePropertiesPage.addWindowsBatchCommand();
        ProjectPage projectPage = freestylePropertiesPage.save();

        int builds = projectPage.countBuildsBeforeAction();

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JenkinsAPI().sendPostRequest(newProjectName, "build");
            }
        });
        newThread.start();

        projectPage.waitForNewBuild(builds);

        try{
            newThread.join();
        } catch (InterruptedException e){}
        BuildsPage buildsPage = projectPage.waitForBuildToEnd();
        assertThat(buildsPage.getConsoleOutputText(),Matchers.containsString("173.194.113.201"));
    }

    @Test
    public void failedTest(){
        UserHomePage userHomePage = new UserHomePage(wd).get();
        userHomePage.openTab(tabName);
        assertThat("No such tab", userHomePage.getPageTitle(), Matchers.containsString(tabName));
    }
}
