package io.github.painfu11y.buildnotifier.dto;

import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierGlobalSettings;
import io.github.painfu11y.buildnotifier.settings.BuildNotifierLocalSettings;

public record EffectiveSettings(
        NotificationMode mode,
        boolean isSendTelegram,
        String telegramToken,
        boolean isSendEmail,
        String emailAddress,
        boolean isSoundEnabled,
        NotificationScope scope
) {
    public static EffectiveSettings fromGlobal(BuildNotifierGlobalSettings g) {
        return new EffectiveSettings(
                g.getMode(),
                g.isSendTelegram(),
                g.getTelegramToken(),
                g.isSendEmail(),
                g.getEmailAddress(),
                g.isSoundEnabled(),
                NotificationScope.ALL_PROJECTS
        );
    }

    public static EffectiveSettings fromProject(BuildNotifierLocalSettings p) {
        return new EffectiveSettings(
                p.getMode(),
                p.isSendTelegram(),
                p.getTelegramToken(),
                p.isSendEmail(),
                p.getEmailAddress(),
                p.isSoundEnabled(),
                NotificationScope.CURRENT_PROJECT
        );
    }
    public boolean isAllProjects(){
        return scope == NotificationScope.ALL_PROJECTS;
    }

}

