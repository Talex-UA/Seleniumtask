package JenkinsTests;

import JenkinsDashboard.Pages.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.ie.InternetExplorerDriver;
import utils.web.JenkinsAPI;

import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Browser.IE;
import static utils.Browser.getCurrentBrowser;
import static utils.CommonMethods.getIPfromPingGoogle;
import static utils.Generators.*;

@RunWith(JUnit4.class)
public class MyTests extends BaseTest {

    private String newUserFullName = generateNewUserFullName();
    private String newProjectName = generateNewProjectName();

    private String tabName = generateString(10);

    @After
    public void cleanUp() {
        if (getCurrentBrowser().equals(IE)) {
            wd.close();
            wd = new InternetExplorerDriver();
        } else {
            wd.manage().deleteAllCookies();
            wd.navigate().refresh();
            try {
                wd.switchTo().alert().dismiss();
            } catch (NoAlertPresentException e) {
            }
        }
    }

    @AfterClass
    public static void afterCurrentClass() {
        UserHomePage userHomePage = new UserHomePage(wd).get();
        userHomePage.deleteTestProjects();
        new PeoplePage(wd).get().deleteTestUsers();
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
                        .checkForError(),
                Matchers.is(true));
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
                        .openTab("All")
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
                .backToDashboard()
                .openTab("All");

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

        assertThat(projectPage.waitForNewBuild(builds)
                        .openLatestBuild()
                        .waitForBuildToEnd()
                        .getConsoleOutputText(),
                Matchers.containsString(getIPfromPingGoogle()));
    }

    @Ignore
    @Test
    public void failedTest() {
        assertThat("No such tab", new UserHomePage(wd).get()
                        .openTab(tabName)
                        .getPageTitle(),
                Matchers.containsString(tabName));
    }
}
