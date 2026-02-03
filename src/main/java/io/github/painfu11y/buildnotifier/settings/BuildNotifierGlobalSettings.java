package io.github.painfu11y.buildnotifier.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationScope;
import org.jetbrains.annotations.NotNull;


@State(
        name = "BuildNotifierGlobalSettings",
        storages = @Storage("buildNotifierGlobalSettings.xml")
)
@Service(Service.Level.APP)
public final class BuildNotifierGlobalSettings
        implements PersistentStateComponent<BuildNotifierGlobalSettings.State> {

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

        public NotificationScope scope = NotificationScope.ALL_PROJECTS;
        public boolean soundEnabled = false;
    }

    private State state = new State();

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public static BuildNotifierGlobalSettings getInstance() {
        return ApplicationManager.getApplication().getService(BuildNotifierGlobalSettings.class);
    }

    public NotificationMode getMode() {
        return state.mode;
    }

    public void setMode(NotificationMode mode) {
        state.mode = mode;
    }

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

    public boolean isSoundEnabled() {
        return state.soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        state.soundEnabled = soundEnabled;
    }

}
