package com.malski.ocado.runners;

import com.malski.core.MyTestNGCucumberTests;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/SavingsFeature.feature",
        glue = "com.malski.ocado.glue",
        format = {"pretty"})
public class SavingsFeatureRunner extends MyTestNGCucumberTests {
}