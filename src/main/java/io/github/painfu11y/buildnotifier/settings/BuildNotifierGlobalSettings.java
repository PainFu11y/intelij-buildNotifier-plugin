package io.github.painfu11y.buildnotifier.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import io.github.painfu11y.buildnotifier.dto.enums.NotificationMode;
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
        public String emailAddress = "";

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
        return ApplicationManager.getApplication()
                .getService(BuildNotifierGlobalSettings.class);
    }

    public NotificationMode getMode() {
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

    public boolean isSoundEnabled() {
        return state.soundEnabled;
    }


}
