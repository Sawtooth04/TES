package org.sawtooth.models.solutionlaunchresult;

import org.sawtooth.tester.models.TestLaunchResults;

import java.util.ArrayList;
import java.util.Objects;

public class SolutionLaunchResult {
    public boolean isSuccessful;
    public boolean isCompiled;
    public ArrayList<TestLaunchResults> correctLaunchResults;
    public ArrayList<TestLaunchResults> incorrectLaunchResults;

    public SolutionLaunchResult() {
        isSuccessful = true;
        isCompiled = true;
        correctLaunchResults = new ArrayList<>();
        incorrectLaunchResults = new ArrayList<>();
    }

    public SolutionLaunchResult(boolean compiled) {
        isSuccessful = compiled;
        isCompiled = compiled;
        correctLaunchResults = new ArrayList<>();
        incorrectLaunchResults = new ArrayList<>();
    }

    private boolean CompareLaunchResult(TestLaunchResults launchResult) {
        if (launchResult.expected.size() != launchResult.launchResults.out.size())
            return false;
        for (int i = 0; i < launchResult.expected.size(); i++)
            if (!Objects.equals(launchResult.expected.get(i), launchResult.launchResults.out.get(i)))
                return false;
        return true;
    }

    public void AddLaunchResult(TestLaunchResults launchResult) {
        if (CompareLaunchResult(launchResult))
            correctLaunchResults.add(launchResult);
        else {
            incorrectLaunchResults.add(launchResult);
            isSuccessful = false;
        }
    }
}
