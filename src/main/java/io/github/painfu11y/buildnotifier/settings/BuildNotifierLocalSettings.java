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
        public String emailAddress = "";

        public boolean soundEnabled = false;

        public static NotificationScope scope = NotificationScope.CURRENT_PROJECT;
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

    public boolean isSendEmail() {
        return state.sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        state.sendEmail = sendEmail;
    }

    public String getTelegramToken() {
        return state.telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        state.telegramToken = telegramToken;
    }

    public String getEmailAddress() {
        return state.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        state.emailAddress = emailAddress;
    }

    public boolean isSoundEnabled() {
        return state.soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        state.soundEnabled = soundEnabled;
    }

    public NotificationScope getScope() {
        return State.scope;
    }

    public void setScope(NotificationScope scope) {
        State.scope = scope;
    }
}
