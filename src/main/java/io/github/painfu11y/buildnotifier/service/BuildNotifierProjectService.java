package io.github.painfu11y.buildnotifier.service;

import com.intellij.build.BuildViewManager;
import com.intellij.openapi.project.Project;
import io.github.painfu11y.buildnotifier.listeners.BuildProgressListenerImpl;
import org.jetbrains.annotations.NotNull;

public final class BuildNotifierProjectService {

    private final Project project;

    public BuildNotifierProjectService(@NotNull Project project) {
        this.project = project;

        System.out.println("BuildNotifierProjectService started for " + project.getName());

        BuildViewManager buildViewManager =
                project.getService(BuildViewManager.class);

        if (buildViewManager == null) {
            System.out.println("BuildViewManager is null");
            return;
        }

        buildViewManager.addListener(
                new BuildProgressListenerImpl(project),
                project
        );
    }
}
