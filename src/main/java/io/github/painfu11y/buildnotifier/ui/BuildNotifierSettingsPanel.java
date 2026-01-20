package io.github.painfu11y.buildnotifier.ui;

import com.intellij.openapi.project.Project;
import com.intellij.util.ui.FormBuilder;
import com.intellij.ui.components.OnOffButton;
import com.intellij.ui.components.JBTextField;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;

import javax.swing.*;
import java.awt.*;

public class BuildNotifierSettingsPanel extends JPanel {

    private final Project project;

    private JComboBox<NotificationMode> modeCombo;

    private OnOffButton telegramToggle;
    private JBTextField telegramTokenField;

    private OnOffButton emailToggle;
    private JBTextField emailToField;
    private JComboBox<NotificationScope> scopeCombo;

    private OnOffButton soundToggle;


    public BuildNotifierSettingsPanel(Project project) {
        this.project = project;

        modeCombo = new JComboBox<>(NotificationMode.values());

        telegramToggle = new OnOffButton();
        telegramTokenField = new JBTextField();

        emailToggle = new OnOffButton();
        emailToField = new JBTextField();

        scopeCombo = new JComboBox<>(NotificationScope.values());

        soundToggle = new OnOffButton();

        // UI-only listeners
        telegramToggle.addActionListener(e ->
                telegramTokenField.setEnabled(telegramToggle.isSelected())
        );

        emailToggle.addActionListener(e ->
                emailToField.setEnabled(emailToggle.isSelected())
        );

        JPanel formPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Notification mode:", modeCombo)
                .addSeparator()
                .addComponent(new JLabel("Channels"))
                .addComponent(toggleRow("Telegram", telegramToggle))
                .addComponent(telegramTokenField)
                .addComponent(toggleRow("Email", emailToggle))
                .addComponent(emailToField)
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


    public void setTelegramEnabled(boolean value) {
        telegramToggle.setSelected(value);
        telegramTokenField.setEnabled(value);
    }

    public void setTelegramToken(String value) { telegramTokenField.setText(value); }

    public Project getProject() {
        return project;
    }

    public JComboBox<NotificationMode> getModeCombo() {
        return modeCombo;
    }

    public OnOffButton getTelegramToggle() {
        return telegramToggle;
    }

    public JBTextField getTelegramTokenField() {
        return telegramTokenField;
    }

    public OnOffButton getEmailToggle() {
        return emailToggle;
    }

    public JBTextField getEmailToField() {
        return emailToField;
    }

    public JComboBox<NotificationScope> getScopeCombo() {
        return scopeCombo;
    }

    public OnOffButton getSoundToggle() {
        return soundToggle;
    }

    public void setEmailEnabled(boolean value) {
        emailToggle.setSelected(value);
        emailToField.setEnabled(value);
    }

    public void setEmail(String value) { emailToField.setText(value); }

    public void setMode(NotificationMode mode) { modeCombo.setSelectedItem(mode); }

    public void setScope(NotificationScope scope) { scopeCombo.setSelectedItem(scope); }


    public void setSoundEnabled(boolean value) {
        soundToggle.setSelected(value);
    }


    public NotificationMode getMode() {
        return (NotificationMode) modeCombo.getSelectedItem();
    }

    public NotificationScope getScope() {
        return (NotificationScope) scopeCombo.getSelectedItem();
    }

    public boolean isTelegramEnabled() {
        return telegramToggle.isSelected();
    }

    public String getTelegramToken() {
        return telegramTokenField.getText();
    }

    public boolean isEmailEnabled() {
        return emailToggle.isSelected();
    }

    public String getEmail() {
        return emailToField.getText();
    }

    public boolean isSoundEnabled() {
        return soundToggle.isSelected();
    }


}
