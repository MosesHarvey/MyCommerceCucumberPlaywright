package com.mycommerce.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber/report.json",
                  "html:target/cucumber/reports.html"},
        features = "src/test/resources/features",
        glue = "com/mycommerce/step_definitions",
        dryRun = false,
        tags = ""
)
public class CukesRunner {
}
