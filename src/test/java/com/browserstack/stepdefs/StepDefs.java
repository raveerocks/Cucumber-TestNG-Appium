package com.browserstack.stepdefs;


import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RunWith(Cucumber.class)
public class StepDefs {

    private AndroidDriver<AndroidElement> driver;

    @Before
    public void setUp() throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/config.json"));

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        JSONArray envs = (JSONArray) config.get("environments");
        Map<String, String> envCapabilities = (Map<String, String>) envs.get(0);
        Set<String> envKeys = envCapabilities.keySet();
        envKeys.stream().filter(key->key!=null).forEach(key ->desiredCapabilities.setCapability(key,envCapabilities.get(key)));

        Map<String, String> capabilities = (Map<String, String>) config.get("capabilities");
        Set<String> capabilityKeys = capabilities.keySet();
        capabilityKeys.stream().filter(key->key!=null).forEach(key ->desiredCapabilities.setCapability(key, capabilities.get(key)));

        String username = System.getenv("BROWSERSTACK_USERNAME");
        username = username==null?(String) config.get("username"):username;
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        accessKey = accessKey==null?(String) config.get("access_key"):accessKey;
        String app = System.getenv("BROWSERSTACK_APP_ID");
        if(app != null && !app.isEmpty()) {
            desiredCapabilities.setCapability("app", app);
        }
        driver = new AndroidDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), desiredCapabilities);

    }

    @Given("^I am on the 'Wikipedia' app$")
    public void iAmOnTheWikipediaApp() {
        Assert.assertEquals(driver.currentActivity(),"org.wikipedia.main.MainActivity");
    }

    @When("^I submit the search term 'BrowserStack'$")
    public void iSubmitTheSearchTermBrowserStack() {
            AndroidElement searchElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                    ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
            searchElement.click();
            AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                    ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
            insertTextElement.sendKeys("BrowserStack");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^The search result is not empty$")
    public void theSearchResultIsNotEmpty() {
        List<AndroidElement> allProductsName = driver.findElementsByClassName("android.widget.TextView");
        Assert.assertTrue(allProductsName.size() > 0);
    }

}