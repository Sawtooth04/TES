package org.sawtooth.controllers;

import org.sawtooth.compiler.abstractions.ICompiler;
import org.sawtooth.compiler.models.CompileResults;
import org.sawtooth.configuration.abstractions.ILanguageConfigurationProvider;
import org.sawtooth.configuration.models.LanguageConfiguration;
import org.sawtooth.launcher.configuration.abstractions.ILauncherConfigurationProvider;
import org.sawtooth.launcher.configuration.models.LauncherConfiguration;
import org.sawtooth.models.SolutionLaunchResult.SolutionLaunchResult;
import org.sawtooth.tester.abstractions.ITesterLauncher;
import org.sawtooth.tester.models.TestLaunchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestLaunchController {
    @Value("${tes.solutions.folder}")
    private String solutionsPath;
    @Value("${tes.tasks.folder}")
    private String tasksPath;
    @Value("${tes.roomid.placeholder}")
    private String roomIDPlaceholder;
    @Value("${tes.user.placeholder}")
    private String userPlaceholder;

    private final ILanguageConfigurationProvider languageConfigurationProvider;
    private final ICompiler compiler;
    private final ITesterLauncher testerLauncher;
    private final ILauncherConfigurationProvider launcherConfigurationProvider;

    @Autowired
    public TestLaunchController(ILanguageConfigurationProvider provider, ICompiler compiler,
        ITesterLauncher testerLauncher, ILauncherConfigurationProvider launcherConfigurationProvider) {
        this.languageConfigurationProvider = provider;
        this.compiler = compiler;
        this.testerLauncher = testerLauncher;
        this.launcherConfigurationProvider = launcherConfigurationProvider;
    }

    private boolean TryCompile(LanguageConfiguration languageConfiguration, String roomID, String user, String solution) {
        try {
            String rootPath = String.format("%s/%s/%s/%s", solutionsPath, roomID, user, solution);
            CompileResults r = compiler.TryCompile(languageConfiguration, solution, rootPath);
            return r.exitCode == 0;
        }
        catch (InterruptedException exception) {
            return false;
        }
    }

    private TestLaunchResults Launch(LauncherConfiguration launcherConfiguration, String roomID, String user, String solution) {
        try {
            if (TryCompile(launcherConfiguration.languageConfiguration, roomID, user, solution)) {
                TestLaunchResults launchResults = testerLauncher.TryTestLaunch(
                        launcherConfiguration,
                        "test_py"
                );
                return launchResults;
            }
            return null;
        }
        catch (InterruptedException exception) {
            return null;
        }
    }

    private void SetLaunchCommand(LanguageConfiguration languageConfiguration, String roomID, String user) {
        languageConfiguration.launchingCommand = languageConfiguration.launchingCommand
            .replace(roomIDPlaceholder, roomID)
            .replace(userPlaceholder, user);
    }

    @GetMapping("/launch")
    public SolutionLaunchResult LaunchAll(String roomID, String language, String solution) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<LauncherConfiguration> launcherConfigurations = launcherConfigurationProvider.TryGetLauncherConfigurations(
            String.format("%s/%s/%s", tasksPath, roomID, solution));
        LanguageConfiguration languageConfiguration = languageConfigurationProvider.TryGetValue(language);
        SolutionLaunchResult solutionLaunchResult = new SolutionLaunchResult();

        SetLaunchCommand(languageConfiguration, roomID, authentication.getName());
        for (LauncherConfiguration launcherConfiguration : launcherConfigurations) {
            launcherConfiguration.languageConfiguration = languageConfiguration;
            solutionLaunchResult.AddLaunchResult(Launch(launcherConfiguration, roomID, authentication.getName(), solution));
        }
        return solutionLaunchResult;
    }
}
