package com.mycommerce.suites;


import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@SuiteDisplayName("Test Use Cases")
@IncludeEngines("cucumber")
@SelectClasspathResource("com/mycommerce/steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,value = "@target/failed_scenarios.txt")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value = "com/mycommerce/steps")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report/failed_cucumber.html")
public class RerunFailedTests {
}
