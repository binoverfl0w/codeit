package org.example.codeit.domaintest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"org.example.codeit.domaintest.stepdefs"},
        plugin = {"pretty", "json:target/cucumber.json"},
        features = "classpath:features"
)
public class CucumberRunnerTest {

}
