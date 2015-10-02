package com.malski.core;

import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyTestNGCucumberTests implements IHookable {
    public MyTestNGCucumberTests() {
    }

    @Parameters("browser")
    @Test(
            groups = {"cucumber"},
            description = "Runs Cucumber Features"
    )
    public void run_cukes(String browser) throws IOException {
        System.setProperty("TEST_BROWSER", browser);
        (new TestNGCucumberRunner(this.getClass())).runCukes();
    }

    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        iHookCallBack.runTestMethod(iTestResult);
    }

}