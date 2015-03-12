package JenkinsTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.rules.*;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.Browser;
import utils.OSUtils;
import utils.web.WebDriverController;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static utils.Browser.IE;
import static utils.Browser.getCurrentBrowser;

public class BaseTest {

    protected static WebDriver wd = null;
    protected final Logger log = LogManager.getLogger(this);

    @Rule
    public final TestName name = new TestName();

    @Rule
    public final DisableOnDebug timeout = new DisableOnDebug(new Timeout(5, TimeUnit.MINUTES) {
        @Override
        public Statement apply(Statement base, Description description) {
            Test test = description.getAnnotation(Test.class); // It could be theory, not test
            long timeout = test != null ? test.timeout() : 0L;
            if (timeout > 0L) {
                return FailOnTimeout.builder().withTimeout(timeout, TimeUnit.MILLISECONDS).build(base);
            }
            return super.apply(base, description);
        }
    });

    @Rule
    public final TestWatcher watcher = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            log.info("Test '{}' - PASSED\n", description.getMethodName());
            super.succeeded(description);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            log.info("Test '{}' - FAILED\nReason: '{}'\n", description.getMethodName(), e.getMessage());
            super.failed(e, description);
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            if (e != null) {
                log.info("Test '{}' - EXPECTED FAILURE (Reason: {})\n", description.getMethodName(), e.getMessage());
            }
            else {
                log.info("Test '{}' - SKIPPED\n");
            }
            super.skipped(e, description);
        }

        @Override
        protected void starting(Description description) {
            log.info("Test '{}' - starting...", description.getMethodName());
            super.starting(description);
        }
    };

    @Rule
    public TestRule takeScreenshotRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            }
            catch (Throwable t) {
                takeScreenshot(d.getMethodName());
                if (t.getMessage().contains("Element is not clickable at point")){
                    wd.close();
                    wd = WebDriverController.getDriver();
                }
                throw t;
            }
        }
    };

    @BeforeClass
    public static void beforeClass() {
        wd = WebDriverController.getDriver();
    }

    @AfterClass
    public static void afterClass() {
        if (wd != null) {
            wd.quit();
        }
        if (getCurrentBrowser().equals(IE)) {
            OSUtils.killProcess("IEDriverServer.exe");
            OSUtils.killProcess("iexplore.exe");
        }
    }

    protected void takeScreenshot(@Nullable String name) {
        File file = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
        if (file != null) {
            String fileName = String.format("%s_%s_%d.png",
                    Browser.getCurrentBrowser(),
                    Optional.ofNullable(name).orElse(this.name.getMethodName()),
                    Instant.now().getNano());
            try {
                Files.copy(file.toPath(), Paths.get(".", fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.trace(e.getMessage(), e);
            }
        }
    }

    /**
     * This method should be used to check if test annotation should be processed by TestRule or MethodRule.
     * In addition to checking if annotation is present for the specified method (d variable) it
     * is also checking if this test is marked with "Ignore" annotation.
     *
     * @param d          of the test
     * @param annotation you want to check
     * @return true if method has specified annotation and not marked with "Ignore", false otherwise
     */
    protected boolean shouldProcessAnnotation(@NotNull Description d, @NotNull Class<? extends Annotation> annotation) {
        return d.getAnnotation(Ignore.class) == null && d.getAnnotation(annotation) != null;
    }
}
