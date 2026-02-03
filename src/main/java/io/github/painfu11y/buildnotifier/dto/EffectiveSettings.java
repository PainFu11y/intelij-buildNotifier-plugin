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
        String emailFrom,
        String emailAddress,
        String smtpHost,
        String smtpPort,
        String emailPassword,
        boolean isSoundEnabled,
        NotificationScope scope
) {

    public static EffectiveSettings fromGlobal(BuildNotifierGlobalSettings g) {
        return new EffectiveSettings(
                g.getMode(),
                g.isSendTelegram(),
                g.getTelegramToken(),
                g.isSendEmail(),
                g.getEmailFrom(),
                g.getEmailAddress(),
                g.getSmtpHost(),
                g.getSmtpPort(),
                g.getEmailPassword(),
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
                p.getEmailFrom(),
                p.getEmailAddress(),
                p.getSmtpHost(),
                p.getSmtpPort(),
                p.getEmailPassword(),
                p.isSoundEnabled(),
                NotificationScope.CURRENT_PROJECT
        );
    }
}
