package io.github.painfu11y.buildnotifier.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.ui.JBUI;

public class BuildNotifierToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(
            @NotNull Project project,
            @NotNull ToolWindow toolWindow
    ) {

        BuildNotifierSettingsPanel settingsPanel =
                new BuildNotifierSettingsPanel(project);

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

}
