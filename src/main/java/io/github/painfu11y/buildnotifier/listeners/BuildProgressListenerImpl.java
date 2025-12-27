package io.github.painfu11y.buildnotifier.listeners;

import com.intellij.build.BuildProgressListener;
import com.intellij.build.events.BuildEvent;
import com.intellij.build.events.FinishBuildEvent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import io.github.painfu11y.buildnotifier.BuildNotifierSettingsResolver;
import io.github.painfu11y.buildnotifier.dto.EffectiveSettings;
import org.jetbrains.annotations.NotNull;

public class BuildProgressListenerImpl implements BuildProgressListener {

    private final Project project;

    public BuildProgressListenerImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onEvent(@NotNull Object buildId,
                        @NotNull BuildEvent event) {

        if (event instanceof FinishBuildEvent) {
            handleBuildFinished();
        }
    }

    private void handleBuildFinished() {
        EffectiveSettings settings =
                BuildNotifierSettingsResolver.resolve(project);

        switch (settings.mode()) {
            case ENABLED -> showNotification("Build finished!");
            case ASK_EVERY_TIME -> askUserForNotification();
            case DISABLED -> {}
        }
    }

    private void showNotification(String text) {
        ApplicationManager.getApplication().invokeLater(() ->
                Notifications.Bus.notify(
                        new Notification(
                                "Build Notifier",
                                "Build Status",
                                text,
                                NotificationType.INFORMATION
                        ),
                        project
                )
        );
    }

    private void askUserForNotification() {
        ApplicationManager.getApplication().invokeLater(() -> {
            int result = Messages.showYesNoDialog(
                    project,
                    "Show build notification?",
                    "Build Notifier",
                    Messages.getQuestionIcon()
            );

            if (result == Messages.YES) {
                showNotification("Build finished!");
            }
        });
    }
}
