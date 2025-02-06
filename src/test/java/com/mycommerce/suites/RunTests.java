package com.mycommerce.suites;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.*;

@Suite
@SuiteDisplayName("Test Use Cases")
@IncludeEngines("cucumber")
//@SelectClasspathResource("features")
//@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com/mycommerce/step_definitions")
//@ConfigurationParameter(key = "CucumberOptions", value = "--tags @login")
@SelectClasspathResource("com/mycommerce/steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,value = "src/test/resources/features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value = "com/mycommerce/steps")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value = "@login")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports.html, json:target/cucumber-report.json")
public class RunTests {

}
