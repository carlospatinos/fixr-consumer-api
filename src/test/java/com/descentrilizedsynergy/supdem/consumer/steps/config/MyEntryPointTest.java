package com.descentrilizedsynergy.supdem.consumer.steps.config;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty",
        "html:build/cucumber-report.html",
        "json:build/cucumber-report.json" }, features = "src/test/resources/features", glue = "com.descentrilizedsynergy.supdem.consumer.steps")
public class MyEntryPointTest {
    // Can be left empty unless otherwise needed
}