package JenkinsDashboard.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public abstract class Page<T extends Page<T>> extends LoadableComponent<T> {

    private static final long MY_CUSTOM_SLEEP = 200;
    protected final Logger log = LogManager.getLogger(this);
    protected final WebDriver wd;

    @FindBy(id = "jenkins-home-link")
    protected WebElement jenkinsIcon;

    @FindBy(xpath = "//div/span/a[2]")
    private WebElement logOut;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    public Page(WebDriver wd) {
        this.wd = wd;
        PageFactory.initElements(wd, this);
    }

    protected Page(WebDriver wd, boolean ifToCheck) {
        this.wd = wd;
        PageFactory.initElements(wd, this);
        if (ifToCheck) {
            checkUniqueElements();
        }
    }

    public List<WebElement> search(String pattern) {
        Actions actions = new Actions(wd);
        actions.moveToElement(searchBox)
                .click()
                .sendKeys(searchBox, pattern)
                .clickAndHold(searchBox)
                .perform();

        List<WebElement> elements = new WebDriverWait(wd, 5, MY_CUSTOM_SLEEP)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".yui-ac-bd li:not([style])")));
        actions.release(searchBox).perform();
        return elements;
    }

    public String[] getSearchResults(String pattern) {
        List<WebElement> elements = search(pattern);
        String[] result = new String[elements.size()];

        elements.stream().forEach(element -> result[elements.indexOf(element)] = element.getText());
        return result;
    }

    public ProjectPage searchForProject(String pattern) {
        search(pattern).stream()
                .filter(project -> project.getText().contains(pattern))
                .forEach(item -> item.click());
        searchBox.submit();
        return new ProjectPage(wd);
    }

    public abstract String getPageURL();

    protected abstract void checkUniqueElements() throws Error;

    public boolean isLoggedIn() {
        try {
            return logOut.getAttribute("href").contains("logout");
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    /**
     * It is safe to call it even if you're not logged in - in this case it will do nothing
     *
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
        } catch (NoSuchElementException e) {
            throw new Error();
        }
    }
}
