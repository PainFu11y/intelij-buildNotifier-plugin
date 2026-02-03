package io.github.painfu11y.buildnotifier.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.ui.components.OnOffButton;
import com.intellij.ui.components.JBTextField;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import jakarta.mail.MessagingException;

import javax.swing.*;
import java.awt.*;

public class BuildNotifierSettingsPanel extends JPanel {

    private final Project project;

    private JComboBox<NotificationMode> modeCombo;

    private OnOffButton telegramToggle;
    private JBTextField telegramTokenField;

    // Email fields
    private OnOffButton emailToggle;
    private JBTextField emailFromField;
    private JBTextField emailToField;
    private JBTextField smtpHostField;
    private JBTextField smtpPortField;
    private JBPasswordField emailPasswordField;
    private JButton testConnectionButton;

    private JComboBox<NotificationScope> scopeCombo;

    private OnOffButton soundToggle;


    public BuildNotifierSettingsPanel(Project project) {
        this.project = project;

        modeCombo = new JComboBox<>(NotificationMode.values());

        telegramToggle = new OnOffButton();
        telegramTokenField = new JBTextField();

        emailToggle = new OnOffButton();
        emailFromField = new JBTextField();
        emailToField = new JBTextField();
        smtpHostField = new JBTextField();
        smtpPortField = new JBTextField();
        emailPasswordField = new JBPasswordField();
        testConnectionButton = new JButton("Test Connection");

        testConnectionButton.addActionListener(e -> {
            try {
                testEmailConnection();
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }
        });

        scopeCombo = new JComboBox<>(NotificationScope.values());

        soundToggle = new OnOffButton();



        JPanel formPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Notification mode:", modeCombo)
                .addSeparator()
                .addComponent(new JLabel("Channels"))
                // telegram
                .addComponent(toggleRow("Telegram", telegramToggle))
                .addComponent(telegramTokenField)
                // email
                .addComponent(toggleRow("Email", emailToggle))
                .addLabeledComponent("From:", emailFromField)
                .addLabeledComponent("To:", emailToField)
                .addLabeledComponent("SMTP Host:", smtpHostField)
                .addLabeledComponent("Port:", smtpPortField)
                .addLabeledComponent("Password:", emailPasswordField)
                .addComponent(testConnectionButton)
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

    public JBPasswordField getEmailPasswordField() {
        return emailPasswordField;
    }

    public JBTextField getSmtpPortField() {
        return smtpPortField;
    }

    public JBTextField getSmtpHostField() {
        return smtpHostField;
    }

    public JBTextField getEmailToField() {
        return emailToField;
    }

    public JBTextField getEmailFromField(){return emailFromField; }

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


    private void testEmailConnection() throws MessagingException {
        service.EmailSenderService.sendEmail(smtpHostField.getText(), smtpPortField.getText(),
                                             emailFromField.getText(),emailPasswordField.getText(),
                                                 emailToField.getText(),"test","test");
        JOptionPane.showMessageDialog(this,
                "Testing connection...\nFrom: " + emailFromField.getText() +
                        "\nTo: " + emailToField.getText() +
                        "\nHost: " + smtpHostField.getText() +
                        "\nPort: " + smtpPortField.getText(),
                "Test Email Connection", JOptionPane.INFORMATION_MESSAGE);
    }


}
