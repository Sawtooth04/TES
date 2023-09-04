package org.sawtooth.controllers;

import org.sawtooth.compiler.abstractions.ICompiler;
import org.sawtooth.compiler.models.CompileResults;
import org.sawtooth.configuration.abstractions.ILanguageConfigurationProvider;
import org.sawtooth.configuration.models.LanguageConfiguration;
import org.sawtooth.launcher.configuration.abstractions.ILauncherConfigurationProvider;
import org.sawtooth.launcher.configuration.models.LauncherConfiguration;
import org.sawtooth.tester.abstractions.ITesterLauncher;
import org.sawtooth.tester.models.TestLaunchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    private boolean TryCompile(LanguageConfiguration languageConfiguration) throws InterruptedException {
        CompileResults r = compiler.TryCompile(languageConfiguration, "test_py", solutionsPath + "\\1\\test_py");
        return
            r.exitCode == 0;
    }

    private TestLaunchResults Launch(LauncherConfiguration launcherConfiguration) throws InterruptedException {
        if (TryCompile(launcherConfiguration.languageConfiguration)) {
            TestLaunchResults launchResults = testerLauncher.TryTestLaunch(
                    launcherConfiguration,
                    "test_py"
            );
            return launchResults;
        }
        return null;
    }

    @GetMapping("/launch")
    public void LaunchAll(String roomID, String language, String solution) throws IOException, InterruptedException {
        ArrayList<LauncherConfiguration> launcherConfigurations = launcherConfigurationProvider.TryGetLauncherConfigurations(
            String.format("%s/%s/%s", tasksPath, roomID, solution));
        LanguageConfiguration languageConfiguration = languageConfigurationProvider.TryGetValue(language);
        ArrayList<TestLaunchResults> launchResults = new ArrayList<>();

        languageConfiguration.launchingCommand = languageConfiguration.launchingCommand.replace(roomIDPlaceholder, roomID);
        for (LauncherConfiguration launcherConfiguration : launcherConfigurations) {
            launcherConfiguration.languageConfiguration = languageConfiguration;
            launchResults.add(Launch(launcherConfiguration));
        }
    }
}
