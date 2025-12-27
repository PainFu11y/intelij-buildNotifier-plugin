package io.github.painfu11y.buildnotifier.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "BuildNotifierSettings",
        storages = {
                @Storage("buildNotifierSettings.xml")
        }
)
@Service(Service.Level.PROJECT)
public final class BuildNotifierSettings implements PersistentStateComponent<BuildNotifierSettings.State>, DumbAware {

    public static class State {
        public NotificationMode mode = NotificationMode.ASK_EVERY_TIME;

        public boolean sendTelegram = true;
        public String telegramToken = "";

        public boolean sendEmail = true;
        public String emailAddress = "";

        public boolean forAllProjects = true;
        public Scope scope = Scope.ALL_PROJECTS;
    }

    public enum NotificationMode { DISABLED, ENABLED, ASK_EVERY_TIME }

    public enum Scope { ALL_PROJECTS, CURRENT_PROJECT }

    private State state = new State();


    @Override
    public @Nullable State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
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

    public boolean isForAllProjects() {
        return state.forAllProjects;
    }

    public void setForAllProjects(boolean forAllProjects) {
        state.forAllProjects = forAllProjects;
    }

    public Scope getScope() {
        return state.scope;
    }

    public void setScope(Scope scope) {
        state.scope = scope;
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


    public static BuildNotifierSettings getInstance(Project project) {
        return project.getService(BuildNotifierSettings.class);
    }
}
