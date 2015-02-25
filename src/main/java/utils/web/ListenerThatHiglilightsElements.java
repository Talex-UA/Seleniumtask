package utils.web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.concurrent.TimeUnit;

public class ListenerThatHiglilightsElements extends AbstractWebDriverEventListener {

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        changeColor(element, js);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        changeColor(element, js);
    }

    private void changeColor(WebElement element, JavascriptExecutor js) {
        js.executeScript("arguments[0].style.border = '3px solid red'", element);
    }

}
