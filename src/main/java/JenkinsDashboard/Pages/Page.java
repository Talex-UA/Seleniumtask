package JenkinsDashboard.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class Page<T extends Page<T>> extends LoadableComponent<T> {

    protected final Logger log = LogManager.getLogger(this);
    protected final WebDriver wd;

    @FindBy(id = "jenkins-home-link")
    protected WebElement jenkinsIcon;

    @FindBy(xpath = "//*[@id='header']/div[2]/span/a[2]")
    private WebElement logOut;

    public Page(WebDriver wd) {
        this.wd = wd;
        PageFactory.initElements(wd, this);
    }

    public abstract String getPageURL();

    protected abstract void checkUniqueElements() throws Error;

    public boolean isLoggedIn() {
        try {
            return logOut.getAttribute("href").contains("logout");
        } catch (NoSuchElementException e){}
        return false;
//        try {
//            return !logOut.isDisplayed();
//        }
//        catch (NoSuchElementException e) {}
//        return true;
    }

    /**
     * It is safe to call it even if you're not logged in - in this case it will do nothing
     * @return
     */
    public T logOut() {
        if (isLoggedIn()) {
            logOut.click();
        }
        return (T) this;
    }

    @Override
    protected void load() {
        log.info("Loading page: {}", getPageURL());
        wd.get(getPageURL());
    }

    @Override
    protected void isLoaded() throws NoSuchElementException {
        try {
            Assert.assertThat("Wrong page URL", wd.getCurrentUrl(), Matchers.equalToIgnoringCase(getPageURL()));
            checkUniqueElements();
        } catch (NoSuchElementException e){
                throw new Error();
        }
    }


}
