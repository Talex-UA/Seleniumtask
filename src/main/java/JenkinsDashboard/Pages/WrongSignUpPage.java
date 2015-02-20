package JenkinsDashboard.Pages;

import org.openqa.selenium.WebDriver;

public class WrongSignUpPage extends Page<WrongSignUpPage> {

    public WrongSignUpPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getPageURL() {
        return "http://seltr-kbp1-1.synapse.com:8080/securityRealm/createAccount";
    }

    @Override
    protected void checkUniqueElements() throws Error {

    }
}
