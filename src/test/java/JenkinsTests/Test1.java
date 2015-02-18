package JenkinsTests;

import JenkinsDashboard.Pages.HomePage;
import JenkinsDashboard.Pages.LogInPage;
import JenkinsDashboard.Pages.SignUpPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class Test1 {

    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://seltr-kbp1-1.synapse.com:8080/");
    }

    @After
    public void after() {
        driver.quit();
    }

//    @Test
//    public void SignUpTest() {
//        HomePage homePage = new HomePage(driver);
//        SignUpPage signUpPage = homePage.gotoSignUpPage();
//        homePage = signUpPage.signUp();
//        assertTrue(driver.getTitle().contains("Dashboard"));
//    }
//
    @Test
    public void LogInTest() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        LogInPage logInPage = homePage.gotoLogInPage();
        homePage = logInPage.logIn("OleksandrA", "qwerty1234");
        assertTrue(driver.getTitle().contains("Dashboard"));
    }

}
