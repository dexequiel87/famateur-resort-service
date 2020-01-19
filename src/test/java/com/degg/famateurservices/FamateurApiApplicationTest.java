package com.degg.famateurservices;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features", glue = "steps")
class FamateurApiApplicationTest extends AbstractTestNGCucumberTests {

}
