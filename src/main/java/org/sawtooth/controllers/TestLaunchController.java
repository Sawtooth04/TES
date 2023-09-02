package org.sawtooth.controllers;

import org.sawtooth.compiler.abstractions.ICompiler;
import org.sawtooth.compiler.configuration.abstractions.ICompilerConfigurationProvider;
import org.sawtooth.launcher.configuration.abstractions.ILauncherConfigurationProvider;
import org.sawtooth.tester.abstractions.ITesterLauncher;
import org.sawtooth.tester.models.TestLaunchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestLaunchController {
    @Value("${tes.configurations.folder}")
    private String configurationsPath;
    @Value("${tes.solutions.folder}")
    private String solutionsPath;
    @Value("${tes.tasks.folder}")
    private String tasksPath;
    private final ICompilerConfigurationProvider compilerConfigurationProvider;
    private final ICompiler compiler;
    private final ITesterLauncher testerLauncher;
    private final ILauncherConfigurationProvider launcherConfigurationProvider;

    @Autowired
    public TestLaunchController(ICompilerConfigurationProvider provider, ICompiler compiler,
        ITesterLauncher testerLauncher, ILauncherConfigurationProvider launcherConfigurationProvider) {
        this.compilerConfigurationProvider = provider;
        this.compiler = compiler;
        this.testerLauncher = testerLauncher;
        this.launcherConfigurationProvider = launcherConfigurationProvider;
    }

    private boolean TryCompile() throws InterruptedException {
        return compiler.TryCompile(compilerConfigurationProvider.TryGetValue("python"), "test_py", solutionsPath + "\\1")
            .exitCode == 0;
    }

    @GetMapping("/launch")
    public void Launch() throws IOException, InterruptedException {
        if (TryCompile()) {
            TestLaunchResults launchResults = testerLauncher.TryTestLaunch(
                launcherConfigurationProvider.TryGetLauncherConfigurations(tasksPath + "/1" + "/test_py").get(0),
                "test_py"
            );
            System.out.println("abcd");
        }
    }
}
