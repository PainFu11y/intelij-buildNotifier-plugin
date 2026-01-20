package io.github.painfu11y.buildnotifier.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.painfu11y.buildnotifier.listeners.OnChangeDocumentListener;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierLocalSettings;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.ui.JBUI;


public class BuildNotifierToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(
            @NotNull Project project,
            @NotNull ToolWindow toolWindow
    ) {
        //creating ui
        BuildNotifierSettingsPanel settingsPanel =
                new BuildNotifierSettingsPanel(project);

        // Initial load (from settings → UI)
        loadFromSettings(project, settingsPanel);

        // Live binding (UI → settings)
        bindLiveSettings(project, settingsPanel);

       //toolwindow layout
        SimpleToolWindowPanel rootPanel =
                new SimpleToolWindowPanel(true, true);

        rootPanel.setContent(settingsPanel);

        rootPanel.setBorder(JBUI.Borders.empty(8));

        Content content = ContentFactory.getInstance()
                .createContent(rootPanel, "", false);

        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }


    private void loadFromSettings(Project project,
                                  BuildNotifierSettingsPanel panel) {

        var settings = BuildNotifierLocalSettings.getInstance(project);

        panel.setMode(settings.getMode());
        panel.setScope(settings.getScope());

        panel.setTelegramEnabled(settings.isSendTelegram());
        panel.setTelegramToken(settings.getTelegramToken());

        panel.setEmailEnabled(settings.isSendEmail());
        panel.setEmail(settings.getEmailAddress());

        panel.setSoundEnabled(settings.isSoundEnabled());
    }

    private void bindLiveSettings(Project project,
                                  BuildNotifierSettingsPanel panel) {

        var settings = BuildNotifierLocalSettings.getInstance(project);

        panel.getModeCombo().addActionListener(e ->
                settings.setMode(panel.getMode())
        );

        panel.getScopeCombo().addActionListener(e ->
                settings.setScope(panel.getScope())
        );

        panel.getTelegramToggle().addActionListener(e ->
                settings.setSendTelegram(panel.isTelegramEnabled())
        );

        panel.getTelegramTokenField()
                .getDocument()
                .addDocumentListener(new OnChangeDocumentListener() {
                    @Override
                    protected void onChange() {
                        settings.setTelegramToken(panel.getTelegramToken());
                    }
                });

        panel.getEmailToggle().addActionListener(e ->
                settings.setSendEmail(panel.isEmailEnabled())
        );

        panel.getEmailToField()
                .getDocument()
                .addDocumentListener(new OnChangeDocumentListener() {
                    @Override
                    protected void onChange() {
                        settings.setEmailAddress(panel.getEmail());
                    }
                });

        panel.getSoundToggle().addActionListener(e ->
                settings.setSoundEnabled(panel.isSoundEnabled())
        );
    }
}
