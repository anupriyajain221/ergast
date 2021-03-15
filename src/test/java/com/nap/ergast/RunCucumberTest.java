package com.nap.ergast;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", 
					plugin = {"pretty", "html:target/cucumber.html"},
					publish = true)
public class RunCucumberTest {}
