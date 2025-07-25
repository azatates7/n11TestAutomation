package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features"
        , glue = "stepDefinitions"
        , plugin =  {"pretty", "html:target/cucumber-reports/htmlReport.html", "json:target/cucumber-reports/jsonReport.json"}
        //, tags = "@n11"
)
public class N11TestRunner {
}
