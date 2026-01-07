package io.github.painfu11y.buildnotifier.listeners;

import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListenerAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.UIUtil;
import io.github.painfu11y.buildnotifier.BuildNotifierSettingsResolver;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class TaskFinishedListener extends ExternalSystemTaskNotificationListenerAdapter {
    private boolean isSendNotification = false;


//    @Override
//    public void onEnd(ExternalSystemTaskId id) {
//        super.onEnd(id);
//        playSound();
//    }


    @Override
    public void onStart(@NotNull ExternalSystemTaskId id, String workingDir) {
        super.onStart(id, workingDir);

        Project project = id.findProject();
        if (project == null) return;

        var settings = BuildNotifierSettingsResolver.resolve(project);


        if (settings.mode().toString() == NotificationMode.ASK_EVERY_TIME.toString()) {

            SwingUtilities.invokeLater(() -> {
                int result = showConfirmationDialog();

                if (result == Messages.YES) {
                    isSendNotification = true;
                } else if (result == Messages.NO) {
                    isSendNotification = false;
                }
            });

        }
        if (settings.mode() == NotificationMode.DISABLED) {
            isSendNotification = false;
        } else {
            isSendNotification = true;
        }


    }

    @Override
    public void onSuccess(ExternalSystemTaskId id) {
        super.onSuccess(id);
        if (!isSendNotification) {
            return;
        }

        Project project = id.findProject();
        if (project == null) return;

        var settings = BuildNotifierSettingsResolver.resolve(project);

        if (settings.isSoundEnabled()) {
            playSuccessSound();
        }
    }

    @Override
    public void onFailure(@NotNull ExternalSystemTaskId id, @NotNull Exception e) {
        super.onFailure(id, e);
        if (!isSendNotification) {
            return;
        }

        Project project = id.findProject();
        if (project == null) return;

        var settings = BuildNotifierSettingsResolver.resolve(project);

        if (settings.isSoundEnabled()) {
            playSuccessSound();
        }
    }

    private void playSuccessSound() {
        UIUtil.playSoundFromResource("/sounds/success.wav");
    }

    private void playFailureSound() {
        UIUtil.playSoundFromResource("/sounds/finished.wav");
    }


    private int showConfirmationDialog() {
        return Messages.showYesNoDialog("Do you want to receive notification?", "Submitting", Messages.getQuestionIcon());
    }


}