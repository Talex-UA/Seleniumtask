package JenkinsTests;

import JenkinsDashboard.Pages.*;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import utils.web.JenkinsAPI;

import java.util.List;
import java.util.function.Predicate;

import static utils.CommonMethods.pingGoogle;
import static utils.Generators.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class MyTests extends BaseTest {

    private String newUserFullName = generateNewUserFullName();
    private String newProjectName = generateNewProjectName();

    private String tabName = generateString(10);

    @After
    public void cleanUp() {
        wd.manage().deleteAllCookies();
        wd.navigate().refresh();
    }

    @AfterClass
    public static void afterCurrentClass() {
        UserHomePage userHomePage = new UserHomePage(wd).get();
        userHomePage.deleteTestProjects();
        userHomePage.deleteTestUsers();
    }

    @Test
    public void logInTest() {
        assertThat(new LogInPage(wd).get()
                        .logIn(getExistingUserName(), getPassword())
                        .getUserName(),
                Matchers.containsString(getExistingUserFulName()));
    }

    @Test
    public void logInWrongCredentials() {
        assertThat(new LogInPage(wd).get()
                        .logInWithWrongCredentials(generateString(10), generateString(7))
                        .getMainPanelText(),
                Matchers.containsString("Try again"));
    }

    @Test
    public void signUpNewUserTest() {
        UserHomePage userHomePage = new SignUpPage(wd).get()
                .signUpNewUser(generateNewUserName(), getPassword(), newUserFullName, generateNewUserEmail());
        assertThat(userHomePage.getUserName(), Matchers.containsString(newUserFullName));
    }

    @Test
    public void signUpExistingNameUser() {
        assertThat(new SignUpPage(wd).get()
                        .signUpExistingNameUser(getExistingUserName(), getPassword(), generateNewUserFullName(), generateNewUserEmail())
                        .getErrorClass(),
                Matchers.containsString("error"));
    }

    @Test
    public void openExistingProject() {
        assertThat("Project not found", new UserHomePage(wd).get()
                        .openProject(getExistingProjectName())
                        .getMainPanelText(),
                Matchers.containsString(getExistingProjectName()));
    }

    @Test
    public void newProjectTest() {
        UserHomePage userHomePage = new NewItemPage(wd).get()
                .setUpFreestyleProject(newProjectName)
                .addDescription(generateNewProjectDescription())
                .save()
                .backToDashboard();

        assertThat("Project is not found", userHomePage.getProjectsNames().stream().anyMatch(Predicate.isEqual(newProjectName)), Matchers.is(true));
    }

    @Test
    public void buildExisting() {
        assertThat(new ProjectPage(wd).get()
                        .buildProject()
                        .openLatestBuild()
                        .getConsoleOutputText(),
                Matchers.containsString("SUCCESS"));
    }

    @Test
    public void testSearch() {
        assertThat(new HomePage(wd).get()
                        .getSearchResults("a").length,
                Matchers.notNullValue());
    }

    @Test
    public void testProjectSearch() {
        assertThat("Wrong project", new HomePage(wd).get()
                        .searchForProject("Exis")
                        .getMainPanelText(),
                Matchers.containsString(getExistingProjectName()));
    }

    @Test
    public void testNewProjectWithPing() {
        ProjectPage projectPage = new NewItemPage(wd).get()
                .setUpFreestyleProject(newProjectName)
                .addDescription(generateNewProjectDescription())
                .checkTriggerBuildsRemotely()
                .addWindowsBatchCommand()
                .save();

        int builds = projectPage.countBuildsBeforeAction();

        new Thread(
                () -> new JenkinsAPI().sendPostRequest(newProjectName, "build")
        ).start();

        List<String> pingOutput = pingGoogle(1, 1);

        assertThat(projectPage.waitForNewBuild(builds)
                        .waitForBuildToEnd()
                        .getConsoleOutputText(),
                Matchers.containsString(pingOutput.get(2).substring(11, 26)));
    }

    @Test
    public void failedTest() {
        assertThat("No such tab", new UserHomePage(wd).get()
                        .openTab(tabName)
                        .getPageTitle(),
                Matchers.containsString(tabName));
    }
}
