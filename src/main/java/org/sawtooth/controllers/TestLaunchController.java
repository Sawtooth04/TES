package org.sawtooth.controllers;

import org.sawtooth.compiler.abstractions.ICompiler;
import org.sawtooth.compiler.models.CompileResults;
import org.sawtooth.configuration.abstractions.ILanguageConfigurationProvider;
import org.sawtooth.configuration.models.LanguageConfiguration;
import org.sawtooth.launcher.configuration.abstractions.ILauncherConfigurationProvider;
import org.sawtooth.launcher.configuration.models.LauncherConfiguration;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.models.solutionlaunchresult.SolutionLaunchResult;
import org.sawtooth.services.tesenginepathesbuilder.ITESEnginePathsBuilder;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.sawtooth.tester.abstractions.ITesterLauncher;
import org.sawtooth.tester.models.TestLaunchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestLaunchController {
    private final ILanguageConfigurationProvider languageConfigurationProvider;
    private final ICompiler compiler;
    private final ITesterLauncher testerLauncher;
    private final ILauncherConfigurationProvider launcherConfigurationProvider;
    private final IStorage storage;
    private final ITESEnginePathsBuilder pathsBuilder;
    @Value("${tes.path.placeholder}")
    private String pathPlaceholder;
    @Value("${tes.solution.placeholder}")
    private String solutionPlaceholder;

    @Autowired
    public TestLaunchController(ILanguageConfigurationProvider provider, ICompiler compiler, IStorage storage,
        ITesterLauncher testerLauncher, ILauncherConfigurationProvider launcherConfigurationProvider, ITESEnginePathsBuilder pathsBuilder) {
        this.languageConfigurationProvider = provider;
        this.compiler = compiler;
        this.testerLauncher = testerLauncher;
        this.launcherConfigurationProvider = launcherConfigurationProvider;
        this.storage = storage;
        this.pathsBuilder = pathsBuilder;
    }

    private boolean TryCompile(LanguageConfiguration languageConfiguration, String rootPath, String solution) {
        try {
            CompileResults r = compiler.TryCompile(languageConfiguration, solution, rootPath);
            return r.exitCode == 0;
        }
        catch (InterruptedException exception) {
            return false;
        }
    }

    private TestLaunchResults Launch(LauncherConfiguration launcherConfiguration, String solution) {
        try {
            return testerLauncher.TryTestLaunch(launcherConfiguration, solution);
        }
        catch (InterruptedException exception) {
            return null;
        }
    }

    private void SetLaunchCommand(LanguageConfiguration languageConfiguration, String path, String solution) {
        languageConfiguration.launchingCommand = languageConfiguration.launchingCommand
            .replace(pathPlaceholder, path)
            .replace(solutionPlaceholder, solution);
    }

    private SolutionLaunchResult LaunchAll(LanguageConfiguration languageConfiguration, String solution,
            ArrayList<LauncherConfiguration> launcherConfigurations) {
        SolutionLaunchResult solutionLaunchResult = new SolutionLaunchResult();

        for (LauncherConfiguration launcherConfiguration : launcherConfigurations) {
            launcherConfiguration.languageConfiguration = languageConfiguration;
            solutionLaunchResult.AddLaunchResult(Launch(launcherConfiguration, solution));
        }
        return solutionLaunchResult;
    }

    @GetMapping("/launch")
    public ResponseEntity<SolutionLaunchResult> LaunchAll(int roomID, String language, int taskID, String solution)
            throws IOException, InstantiationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int variant = storage.GetRepository(IRoomCustomerRepository.class).GetVariant(authentication.getName(),roomID);
        RoomTaskVariant taskVariant = storage.GetRepository(IRoomTaskVariantRepository.class).Get(taskID, variant);
        ArrayList<LauncherConfiguration> launcherConfigurations = launcherConfigurationProvider.TryGetLauncherConfigurations(
            pathsBuilder.BuildTaskVariantPath(roomID, taskID, taskVariant.variant()).toString());
        LanguageConfiguration languageConfiguration = languageConfigurationProvider.TryGetValue(language);
        String rootPath = pathsBuilder.BuildSolutionPath(roomID, taskID, authentication.getName()).toString();
        SetLaunchCommand(languageConfiguration, rootPath, solution);
        if (TryCompile(languageConfiguration, rootPath, solution))
            return ResponseEntity.status(HttpStatus.OK).body(LaunchAll(languageConfiguration, solution,
                launcherConfigurations));
        return ResponseEntity.status(HttpStatus.OK).body(new SolutionLaunchResult(false));
    }
}
