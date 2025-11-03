package com.fdmgroup.stepdefinition;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.fdmgroup.stepdefinition",
    plugin = {"pretty", "html:src/test/resources/reports/cucumber-report.html"},
    monochrome = true,
    publish = true
)
public class TestRunner {}
