package io.github.painfu11y.buildnotifier.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "BuildNotifierSettings",
        storages = @Storage("buildNotifierSettings.xml")
)
@Service(Service.Level.PROJECT)
public final class BuildNotifierLocalSettings
        implements PersistentStateComponent<BuildNotifierLocalSettings.State>, DumbAware {

    public static class State {
        public NotificationMode mode = NotificationMode.ASK_EVERY_TIME;

        public boolean sendTelegram = false;
        public String telegramToken = "";

        public boolean sendEmail = false;
        public String emailFrom = "";
        public String emailAddress = "";
        public String smtpHost = "";
        public String smtpPort = "";
        public String emailPassword = "";

        public NotificationScope scope = NotificationScope.CURRENT_PROJECT;
        public boolean soundEnabled = false;
    }

    private State state = new State();

    @Override
    public @Nullable State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public static BuildNotifierLocalSettings getInstance(Project project) {
        return project.getService(BuildNotifierLocalSettings.class);
    }

    // Mode
    public NotificationMode getMode() {
        return state.mode;
    }

    public void setMode(NotificationMode mode) {
        state.mode = mode;
    }

    // Telegram
    public boolean isSendTelegram() {
        return state.sendTelegram;
    }

    public void setSendTelegram(boolean sendTelegram) {
        state.sendTelegram = sendTelegram;
    }

    public String getTelegramToken() {
        return state.telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        state.telegramToken = telegramToken;
    }

    // Email
    public boolean isSendEmail() {
        return state.sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        state.sendEmail = sendEmail;
    }

    public String getEmailFrom() {
        return state.emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        state.emailFrom = emailFrom;
    }

    public String getEmailAddress() {
        return state.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        state.emailAddress = emailAddress;
    }

    public String getSmtpHost() {
        return state.smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        state.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return state.smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        state.smtpPort = smtpPort;
    }

    public String getEmailPassword() {
        return state.emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        state.emailPassword = emailPassword;
    }


    // Scope
    public NotificationScope getScope() {return state.scope;}

    public void setScope(NotificationScope scope) {state.scope = scope;}

    // Sound
    public boolean isSoundEnabled() {
        return state.soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        state.soundEnabled = soundEnabled;
    }
}
