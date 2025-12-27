package io.github.painfu11y.buildnotifier;

import com.intellij.openapi.project.Project;
import io.github.painfu11y.buildnotifier.dto.EffectiveSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierSettings;

public final class BuildNotifierSettingsResolver {

    public static EffectiveSettings resolve(Project project) {

        BuildNotifierSettings projectSettings =
                BuildNotifierSettings.getInstance(project);

        if (projectSettings.getScope() == BuildNotifierSettings.Scope.ALL_PROJECTS) {
            BuildNotifierGlobalSettings global =
                    BuildNotifierGlobalSettings.getInstance();

            return EffectiveSettings.fromGlobal(global);
        }

        return EffectiveSettings.fromProject(projectSettings);
    }
}
