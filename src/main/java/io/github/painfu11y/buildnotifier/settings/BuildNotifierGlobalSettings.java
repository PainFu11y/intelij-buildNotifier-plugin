package io.github.painfu11y.buildnotifier.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
@State(
        name = "BuildNotifierGlobalSettings",
        storages = @Storage("buildNotifierGlobalSettings.xml")
)
public final class BuildNotifierGlobalSettings
        implements PersistentStateComponent<BuildNotifierGlobalSettings.State> {

    public static class State {
        public BuildNotifierSettings.NotificationMode mode = BuildNotifierSettings.NotificationMode.ASK_EVERY_TIME;
        public boolean sendTelegram = true;
        public String telegramToken = "";
        public boolean sendEmail = true;
        public String emailAddress = "";
    }

    public void setState(State state) {
        this.state = state;
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
        return ApplicationManager.getApplication()
                .getService(BuildNotifierGlobalSettings.class);
    }

    public BuildNotifierSettings.NotificationMode getMode() {
        return state.mode;
    }

    public boolean isSendTelegram() {
        return state.sendTelegram;
    }

    public String getTelegramToken() {
        return state.telegramToken;
    }

    public boolean isSendEmail() {
        return state.sendEmail;
    }

    public String getEmailAddress() {
        return state.emailAddress;
    }

}
