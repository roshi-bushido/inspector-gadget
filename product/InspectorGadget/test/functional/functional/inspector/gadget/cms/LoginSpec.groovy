package functional.inspector.gadget.cms

import geb.spock.GebReportingSpec
import org.openqa.selenium.By

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class LoginSpec  extends GebReportingSpec {

    def "should login successfully" () {
        when:
            browser.go "/InspectorGadget/login"

            def form = $("#loginForm")
            form.with {
                this.driver.findElement(By.id("username")).sendKeys("admin")
                this.driver.findElement(By.id("password")).sendKeys("admin")
                form.submit()
            }
        then:
            assertThat(browser.title, equalTo("Login"))
            assertThat(browser.driver.currentUrl, equalTo("/login/auth"))

            println(browser.driver.pageSource)
    }
}
