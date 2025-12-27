package io.github.painfu11y.buildnotifier.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.ui.FormBuilder;
import com.intellij.ui.components.OnOffButton;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.NotNull;
import io.github.painfu11y.buildnotifier.BuildNotifierSettingsResolver;
import io.github.painfu11y.buildnotifier.dto.EffectiveSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierSettings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class BuildNotifierSettingsPanel extends JPanel {

    private final Project project;

    private JComboBox<BuildNotifierSettings.NotificationMode> modeCombo;
    private OnOffButton telegramToggle;
    private JBTextField telegramTokenField;
    private OnOffButton emailToggle;
    private JBTextField emailField;
    private JComboBox<BuildNotifierSettings.Scope> scopeCombo;

    public BuildNotifierSettingsPanel(Project project) {
        this.project = project;

        modeCombo = new JComboBox<>(BuildNotifierSettings.NotificationMode.values());
        telegramToggle = new OnOffButton();
        telegramTokenField = new JBTextField();
        emailToggle = new OnOffButton();
        emailField = new JBTextField();
        scopeCombo = new JComboBox<>(BuildNotifierSettings.Scope.values());

        //  Load effective settings
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        setMode(effective.mode());
        setScope(effective.scope());
        setTelegramEnabled(effective.isSendTelegram());
        setTelegramToken(effective.telegramToken());
        setEmailEnabled(effective.isSendEmail());
        setEmail(effective.emailAddress());

        //  Listeners
        modeCombo.addActionListener(e -> saveMode());
        scopeCombo.addActionListener(e -> saveScope());

        telegramToggle.addActionListener(e -> {
            boolean enabled = telegramToggle.isSelected();
            telegramTokenField.setEnabled(enabled);
            saveTelegram(enabled, telegramTokenField.getText());
        });

        telegramTokenField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                saveTelegram(telegramToggle.isSelected(), telegramTokenField.getText());
            }
        });

        emailToggle.addActionListener(e -> {
            boolean enabled = emailToggle.isSelected();
            emailField.setEnabled(enabled);
            saveEmail(enabled, emailField.getText());
        });

        emailField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                saveEmail(emailToggle.isSelected(), emailField.getText());
            }
        });

        JPanel formPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Notification mode:", modeCombo)
                .addSeparator()
                .addComponent(new JLabel("Channels"))
                .addComponent(toggleRow("Telegram", telegramToggle))
                .addComponent(telegramTokenField)
                .addComponent(toggleRow("Email", emailToggle))
                .addComponent(emailField)
                .addSeparator()
                .addLabeledComponent("Scope:", scopeCombo)
                .getPanel();

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
    }

    private JPanel toggleRow(String text, OnOffButton toggle) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(text), BorderLayout.WEST);
        panel.add(toggle, BorderLayout.EAST);
        return panel;
    }

    // Save logic
    private void saveMode() {
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        if (effective.isAllProjects()) {
            BuildNotifierGlobalSettings g = BuildNotifierGlobalSettings.getInstance();
            g.getState().mode = (BuildNotifierSettings.NotificationMode) modeCombo.getSelectedItem();
        } else {
            BuildNotifierSettings s = BuildNotifierSettings.getInstance(project);
            s.setMode((BuildNotifierSettings.NotificationMode) modeCombo.getSelectedItem());
        }
    }

    private void saveScope() {
        BuildNotifierSettings s = BuildNotifierSettings.getInstance(project);
        s.setScope((BuildNotifierSettings.Scope) scopeCombo.getSelectedItem());
    }

    private void saveTelegram(boolean enabled, String token) {
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        if (effective.isAllProjects()) {
            BuildNotifierGlobalSettings g = BuildNotifierGlobalSettings.getInstance();
            g.getState().sendTelegram = enabled;
            g.getState().telegramToken = token;
        } else {
            BuildNotifierSettings s = BuildNotifierSettings.getInstance(project);
            s.setSendTelegram(enabled);
            s.setTelegramToken(token);
        }
    }

    private void saveEmail(boolean enabled, String email) {
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        if (effective.isAllProjects()) {
            BuildNotifierGlobalSettings g = BuildNotifierGlobalSettings.getInstance();
            g.getState().sendEmail = enabled;
            g.getState().emailAddress = email;
        } else {
            BuildNotifierSettings s = BuildNotifierSettings.getInstance(project);
            s.setSendEmail(enabled);
            s.setEmailAddress(email);
        }
    }

    public boolean isTelegramEnabled() { return telegramToggle.isSelected(); }
    public String getTelegramToken() { return telegramTokenField.getText(); }

    public boolean isEmailEnabled() { return emailToggle.isSelected(); }
    public String getEmail() { return emailField.getText(); }

    public BuildNotifierSettings.NotificationMode getMode() { return (BuildNotifierSettings.NotificationMode) modeCombo.getSelectedItem(); }
    public BuildNotifierSettings.Scope getScope() { return (BuildNotifierSettings.Scope) scopeCombo.getSelectedItem(); }

    public void setTelegramEnabled(boolean value) {
        telegramToggle.setSelected(value);
        telegramTokenField.setEnabled(value);
    }

    public void setTelegramToken(String value) { telegramTokenField.setText(value); }

    public void setEmailEnabled(boolean value) {
        emailToggle.setSelected(value);
        emailField.setEnabled(value);
    }

    public void setEmail(String value) { emailField.setText(value); }

    public void setMode(BuildNotifierSettings.NotificationMode mode) { modeCombo.setSelectedItem(mode); }

    public void setScope(BuildNotifierSettings.Scope scope) { scopeCombo.setSelectedItem(scope); }
}
