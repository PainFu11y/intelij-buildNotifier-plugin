package io.github.painfu11y.buildnotifier;

import com.intellij.openapi.project.Project;
import io.github.painfu11y.buildnotifier.dto.EffectiveSettings;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierLocalSettings;

public final class BuildNotifierSettingsResolver {

    public static EffectiveSettings resolve(Project project) {

        BuildNotifierLocalSettings projectSettings = BuildNotifierLocalSettings.getInstance(project);
        if (projectSettings.getScope() == NotificationScope.ALL_PROJECTS) {
            BuildNotifierGlobalSettings global = BuildNotifierGlobalSettings.getInstance();
            return EffectiveSettings.fromGlobal(global);
        }
        return EffectiveSettings.fromProject(projectSettings);
    }
}