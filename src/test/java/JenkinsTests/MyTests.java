package JenkinsTests;

import JenkinsDashboard.Pages.HomePage;
import JenkinsDashboard.Pages.LogInPage;
import JenkinsDashboard.Pages.SignUpPage;
import JenkinsDashboard.Pages.WrongLogInPage;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.TestCase.assertTrue;

public class MyTests extends BaseTest {

    private static final String NEW_USER_NAME = "Talex-UA";
    private static final String NEW_USER_PASSWORD = "qwerty1234";
    private static final String NEW_USER_FULL_NAME = "Alexander";
    private static final String NEW_USER_EMAIL = "talexua91@gmail.com";

    private static final String EXISTING_USER_NAME = "Talex-UA";
    private static final String EXISTING_USER_PASSWORD = "qwerty1234";
    private static final String EXISTING_USER_FULL_NAME = "Alexander";
    private static final String EXISTING_USER_EMAIL = "talexua91@gmail.com";

    @Test
    public void LogInTest() {
        HomePage homePage = new HomePage(wd);
        LogInPage logInPage = homePage.gotoLogInPage();
        homePage = logInPage.logIn(EXISTING_USER_NAME, EXISTING_USER_PASSWORD);
        assertTrue(wd.getTitle().contains("Dashboard"));
        homePage.logOut();
    }

    @Test
    public void LogInWrongCredentials(){
        HomePage homePage = new HomePage(wd);
        LogInPage logInPage = homePage.gotoLogInPage();
        WrongLogInPage wrongLogInPage = logInPage.logInWithWrongCredentials("asfaf","segwf");
        assertTrue(wd.findElement(By.id("main-panel-content")).getText().contains("Invalid login information. Please try again."));
        homePage = wrongLogInPage.gotoHomePage();
    }

    @Ignore
    @Test
    public void SignUpNewUserTest() {
        HomePage homePage = new HomePage(wd);
        SignUpPage signUpPage = homePage.gotoSignUpPage();
        homePage = signUpPage.signUpNewUser(NEW_USER_NAME, NEW_USER_PASSWORD, NEW_USER_FULL_NAME, NEW_USER_EMAIL);
        assertTrue(wd.getTitle().contains("Dashboard"));
        homePage.logOut();
    }

    @Test
    public void SignUpExistingNameUser() {
        HomePage homePage = new HomePage(wd);
        SignUpPage signUpPage = homePage.gotoSignUpPage();
        signUpPage.signUpExistingNameUser(EXISTING_USER_NAME, EXISTING_USER_PASSWORD, EXISTING_USER_FULL_NAME, EXISTING_USER_EMAIL);
        assertTrue(wd.findElement(By.id("main-panel-content")).getText().contains("User name is already taken"));
        homePage=signUpPage.gotoHomePage();
    }
}
