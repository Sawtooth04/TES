package org.sawtooth.models.SolutionLaunchResult;

import org.sawtooth.tester.models.TestLaunchResults;

import java.util.ArrayList;
import java.util.Objects;

public class SolutionLaunchResult {
    public boolean isSuccessful;
    public ArrayList<TestLaunchResults> correctLaunchResults;
    public ArrayList<TestLaunchResults> incorrectLaunchResults;

    public SolutionLaunchResult() {
        isSuccessful = true;
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
