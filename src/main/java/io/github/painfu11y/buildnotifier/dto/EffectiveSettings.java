package io.github.painfu11y.buildnotifier.dto;

import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierSettings;

public record EffectiveSettings(
        BuildNotifierSettings.NotificationMode mode,
        boolean isSendTelegram,
        String telegramToken,
        boolean isSendEmail,
        String emailAddress,
        BuildNotifierSettings.Scope scope
) {
    public static EffectiveSettings fromGlobal(BuildNotifierGlobalSettings g) {
        return new EffectiveSettings(
                g.getMode(),
                g.isSendTelegram(),
                g.getTelegramToken(),
                g.isSendEmail(),
                g.getEmailAddress(),
                BuildNotifierSettings.Scope.ALL_PROJECTS
        );
    }

    public static EffectiveSettings fromProject(BuildNotifierSettings p) {
        return new EffectiveSettings(
                p.getMode(),
                p.isSendTelegram(),
                p.getTelegramToken(),
                p.isSendEmail(),
                p.getEmailAddress(),
                p.getScope()
        );
    }

    public boolean isAllProjects() {
        return scope == BuildNotifierSettings.Scope.ALL_PROJECTS;
    }
}

