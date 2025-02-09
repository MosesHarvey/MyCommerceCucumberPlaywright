package com.mycommerce.suites;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;



@Suite
@SuiteDisplayName("Test Use Cases")
@IncludeEngines("cucumber")
@SelectClasspathResource("com/mycommerce/steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,value = "src/test/resources/features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value = "com/mycommerce/steps")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value = "@smoke")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty,html:target/default-html-reports.html,json:target/cucumber-report.json, rerun:target/failed_scenarios.txt")
public class RunTests {

}
