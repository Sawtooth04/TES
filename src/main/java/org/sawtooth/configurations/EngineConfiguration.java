package org.sawtooth.configurations;

import org.sawtooth.compiler.abstractions.ICompiler;
import org.sawtooth.compiler.configuration.abstractions.ICompilerConfigurationProvider;
import org.sawtooth.compiler.configuration.parser.abstractions.ICompilerConfigurationParser;
import org.sawtooth.compiler.configuration.parser.realizations.CompilerConfigurationParser;
import org.sawtooth.compiler.configuration.realizations.CompilerConfigurationProvider;
import org.sawtooth.compiler.realizations.Compiler;
import org.sawtooth.launcher.abstractions.ILauncher;
import org.sawtooth.launcher.configuration.abstractions.ILauncherConfigurationProvider;
import org.sawtooth.launcher.configuration.realizations.LauncherConfigurationProvider;
import org.sawtooth.launcher.realizations.Launcher;
import org.sawtooth.tester.abstractions.ITesterLauncher;
import org.sawtooth.tester.realizations.TesterLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class EngineConfiguration {
    @Value("${tes.configurations.folder}")
    private String configurationsPath;
    @Value("${tes.solutions.folder}")
    private String solutionsPath;

    @Bean
    public ICompilerConfigurationParser CompilerConfigurationParser() {
        return new CompilerConfigurationParser();
    }

    @Bean
    @Autowired
    public ICompilerConfigurationProvider CompilerConfigurationProvider(ICompilerConfigurationParser compilerConfigurationParser)
        throws IOException {
        return new CompilerConfigurationProvider(compilerConfigurationParser, configurationsPath);
    }

    @Bean
    public ICompiler Compiler() {
        return new Compiler(System.getProperty("os.name").toLowerCase().startsWith("windows"));
    }

    @Bean
    public ILauncherConfigurationProvider LauncherConfigurationProvider() {
        return new LauncherConfigurationProvider();
    }

    @Bean
    public ITesterLauncher TesterLauncher() {
        return new TesterLauncher(System.getProperty("os.name").toLowerCase().startsWith("windows"),
            solutionsPath + "\\1");
    }

    @Bean
    public ILauncher Launcher() {
        return new Launcher(System.getProperty("os.name").toLowerCase().startsWith("windows"),
            solutionsPath + "\\1");
    }
}
