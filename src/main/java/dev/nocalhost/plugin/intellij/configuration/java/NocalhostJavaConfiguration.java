package dev.nocalhost.plugin.intellij.configuration.java;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.nocalhost.plugin.intellij.configuration.NocalhostConfiguration;
import dev.nocalhost.plugin.intellij.configuration.NocalhostProfileState;
import dev.nocalhost.plugin.intellij.configuration.NocalhostSettingsEditor;

public class NocalhostJavaConfiguration
        extends LocatableConfigurationBase<NocalhostJavaConfiguration>
        implements NocalhostConfiguration {

    protected NocalhostJavaConfiguration(@NotNull Project project,
                                         @NotNull ConfigurationFactory factory) {
        super(project, factory);
    }

    @Override
    @NotNull
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new NocalhostSettingsEditor();
    }

    @Override
    @Nullable
    public RunProfileState getState(
            @NotNull Executor executor,
            @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new NocalhostProfileState(environment);
    }
}
