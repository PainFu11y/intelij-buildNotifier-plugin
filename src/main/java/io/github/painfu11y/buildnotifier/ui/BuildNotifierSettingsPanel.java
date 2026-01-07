package io.github.painfu11y.buildnotifier.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.ui.FormBuilder;
import com.intellij.ui.components.OnOffButton;
import com.intellij.ui.components.JBTextField;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import org.jetbrains.annotations.NotNull;
import io.github.painfu11y.buildnotifier.BuildNotifierSettingsResolver;
import io.github.painfu11y.buildnotifier.dto.EffectiveSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierLocalSettings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class BuildNotifierSettingsPanel extends JPanel {

    private final Project project;

    private JComboBox<NotificationMode> modeCombo;

    private OnOffButton telegramToggle;
    private JBTextField telegramTokenField;

    private OnOffButton emailToggle;
    private JBTextField emailField;
    private JComboBox<NotificationScope> scopeCombo;

    private OnOffButton soundToggle;


    public BuildNotifierSettingsPanel(Project project) {
        this.project = project;

        modeCombo = new JComboBox<>(NotificationMode.values());
        telegramToggle = new OnOffButton();
        telegramTokenField = new JBTextField();
        emailToggle = new OnOffButton();
        emailField = new JBTextField();
        scopeCombo = new JComboBox<>(NotificationScope.values());
        soundToggle = new OnOffButton();


        //  Load effective settings
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        setMode(effective.mode());
        setScope(effective.scope());
        setTelegramEnabled(effective.isSendTelegram());
        setTelegramToken(effective.telegramToken());
        setEmailEnabled(effective.isSendEmail());
        setEmail(effective.emailAddress());
        setSoundEnabled(effective.isSoundEnabled());


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


        soundToggle.addActionListener(e -> {
            saveSound(soundToggle.isSelected());
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
                .addSeparator()
                .addComponent(toggleRow("Sound", soundToggle))
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
            g.getState().mode = (NotificationMode) modeCombo.getSelectedItem();
        } else {
            BuildNotifierLocalSettings s = BuildNotifierLocalSettings.getInstance(project);
            s.setMode((NotificationMode) modeCombo.getSelectedItem());
        }
    }

    private void saveScope() {
        BuildNotifierLocalSettings s = BuildNotifierLocalSettings.getInstance(project);
        s.setScope((NotificationScope) scopeCombo.getSelectedItem());
    }

    private void saveTelegram(boolean enabled, String token) {
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        if (effective.isAllProjects()) {
            BuildNotifierGlobalSettings g = BuildNotifierGlobalSettings.getInstance();
            g.getState().sendTelegram = enabled;
            g.getState().telegramToken = token;
        } else {
            BuildNotifierLocalSettings s = BuildNotifierLocalSettings.getInstance(project);
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
            BuildNotifierLocalSettings s = BuildNotifierLocalSettings.getInstance(project);
            s.setSendEmail(enabled);
            s.setEmailAddress(email);
        }
    }

    private void saveSound(boolean enabled) {
        EffectiveSettings effective = BuildNotifierSettingsResolver.resolve(project);
        if (effective.isAllProjects()) {
            BuildNotifierGlobalSettings g = BuildNotifierGlobalSettings.getInstance();
            g.getState().soundEnabled = enabled;
        } else {
            BuildNotifierLocalSettings s = BuildNotifierLocalSettings.getInstance(project);
            s.setSoundEnabled(enabled);
        }
    }



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

    public void setMode(NotificationMode mode) { modeCombo.setSelectedItem(mode); }

    public void setScope(NotificationScope scope) { scopeCombo.setSelectedItem(scope); }


    public void setSoundEnabled(boolean value) {
        soundToggle.setSelected(value);
    }


}
