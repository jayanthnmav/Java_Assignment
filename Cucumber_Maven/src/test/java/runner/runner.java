package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/Feature/Prob3.feature","src/test/Feature/Prob2.feature","src/test/Feature/Prob1.feature"} ,
        glue = "stepDef" ,
        tags = "@postCall or @userPage or @getCall",
        plugin = {
                "pretty",
                "json:target/cucumber-report/cucumber.json",
                "html:target/cucumber-report/cucumber.html"}
)


public class runner {
}
