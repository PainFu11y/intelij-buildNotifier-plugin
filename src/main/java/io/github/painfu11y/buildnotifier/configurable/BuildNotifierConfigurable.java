package io.github.painfu11y.buildnotifier.configurable;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import io.github.painfu11y.buildnotifier.BuildNotifierSettingsResolver;
import io.github.painfu11y.buildnotifier.dto.EffectiveSettings;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierLocalSettings;
import io.github.painfu11y.buildnotifier.ui.BuildNotifierSettingsPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BuildNotifierConfigurable implements SearchableConfigurable {

    private final Project project;
    private BuildNotifierSettingsPanel panel;

    public BuildNotifierConfigurable(Project project) {
        this.project = project;
    }

    @Override
    public @NotNull String getId() {
        return "io.github.painfu11y.buildnotifier.settings";
    }

    @Override
    public @Nls String getDisplayName() {
        return "Build Notifier";
    }

    @Override
    public @Nullable JComponent createComponent() {
        panel = new BuildNotifierSettingsPanel(project);
        return panel;
    }

    @Override
    public boolean isModified() {
        if (panel == null) return false;

        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);

        NotificationMode uiMode =
                (NotificationMode) panel.getModeCombo().getSelectedItem();

        NotificationScope uiScope =
                (NotificationScope) panel.getScopeCombo().getSelectedItem();

        boolean uiTelegramEnabled = panel.getTelegramToggle().isSelected();
        String uiTelegramToken = panel.getTelegramTokenField().getText();

        boolean uiEmailEnabled = panel.getEmailToggle().isSelected();
        String uiEmail = panel.getEmailToField().getText();

        boolean uiSoundEnabled = panel.getSoundToggle().isSelected();

        return uiMode != effective.mode()
                || uiScope != effective.scope()
                || uiTelegramEnabled != effective.isSendTelegram()
                || !safeEquals(uiTelegramToken, effective.telegramToken())
                || uiEmailEnabled != effective.isSendEmail()
                || !safeEquals(uiEmail, effective.emailAddress())
                || uiSoundEnabled != effective.isSoundEnabled();
    }

    @Override
    public void apply() {
        if (panel == null) return;

        NotificationMode mode =
                (NotificationMode) panel.getModeCombo().getSelectedItem();

        NotificationScope scope =
                (NotificationScope) panel.getScopeCombo().getSelectedItem();

        boolean telegramEnabled = panel.getTelegramToggle().isSelected();
        String telegramToken = panel.getTelegramTokenField().getText();

        boolean emailEnabled = panel.getEmailToggle().isSelected();
        String email = panel.getEmailToField().getText();

        boolean soundEnabled = panel.getSoundToggle().isSelected();

        if (scope == NotificationScope.ALL_PROJECTS) {
            BuildNotifierGlobalSettings g = BuildNotifierGlobalSettings.getInstance();

            g.getState().mode = mode;
            g.getState().sendTelegram = telegramEnabled;
            g.getState().telegramToken = telegramToken;
            g.getState().sendEmail = emailEnabled;
            g.getState().emailAddress = email;
            g.getState().soundEnabled = soundEnabled;
        } else {
            BuildNotifierLocalSettings s =
                    BuildNotifierLocalSettings.getInstance(project);

            s.setMode(mode);
            s.setScope(NotificationScope.CURRENT_PROJECT);
            s.setSendTelegram(telegramEnabled);
            s.setTelegramToken(telegramToken);
            s.setSendEmail(emailEnabled);
            s.setEmailAddress(email);
            s.setSoundEnabled(soundEnabled);
        }
    }

    @Override
    public void reset() {
        if (panel == null) return;

        EffectiveSettings effective =
                BuildNotifierSettingsResolver.resolve(project);

        panel.setMode(effective.mode());
        panel.setScope(effective.scope());
        panel.setTelegramEnabled(effective.isSendTelegram());
        panel.setTelegramToken(effective.telegramToken());
        panel.setEmailEnabled(effective.isSendEmail());
        panel.setEmail(effective.emailAddress());
        panel.setSoundEnabled(effective.isSoundEnabled());
    }

    @Override
    public void disposeUIResources() {
        panel = null;
    }

    private boolean safeEquals(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }
}
