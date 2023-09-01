package org.sawtooth.controllers;

import org.sawtooth.compiler.abstractions.ICompiler;
import org.sawtooth.compiler.configuration.abstractions.ICompilerConfigurationProvider;
import org.sawtooth.compiler.configuration.parser.abstractions.ICompilerConfigurationParser;
import org.sawtooth.compiler.configuration.parser.realizations.CompilerConfigurationParser;
import org.sawtooth.compiler.configuration.realizations.CompilerConfigurationProvider;
import org.sawtooth.compiler.models.CompileResults;
import org.sawtooth.compiler.realizations.Compiler;
import org.sawtooth.launcher.configuration.abstractions.ILauncherConfigurationProvider;
import org.sawtooth.launcher.configuration.realizations.LauncherConfigurationProvider;
import org.sawtooth.tester.abstractions.ITesterLauncher;
import org.sawtooth.tester.realizations.TesterLauncher;
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

    private boolean TryCompile() throws IOException, InterruptedException {
        boolean isOnWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ICompilerConfigurationParser compilerConfigurationParser = new CompilerConfigurationParser();
        ICompilerConfigurationProvider compilerConfigurationProvider = new CompilerConfigurationProvider(
                compilerConfigurationParser, configurationsPath);
        ICompiler compiler = new Compiler(isOnWindows);

        return compiler.TryCompile(compilerConfigurationProvider.TryGetValue("python"), "test_py", solutionsPath + "\\1")
            .exitCode == 0;
    }

    @GetMapping("/launch")
    public void Launch() throws IOException, InterruptedException {
        if (TryCompile()) {
            boolean isOnWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            ILauncherConfigurationProvider launcherConfigurationProvider = new LauncherConfigurationProvider();
            ITesterLauncher testerLauncher = new TesterLauncher(isOnWindows, solutionsPath + "\\1");
            boolean launchResults = testerLauncher.TryComparedTestLaunch(
                launcherConfigurationProvider.TryGetLauncherConfigurations(tasksPath + "/1" + "/test_py").get(0),
                "test_py"
            );
            System.out.println("abcd");
        }
    }
}
