package uk.co.intelitrack.intelizzz.components.login;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class LoginPresenterModule {
    private final LoginActivity activity;

    public LoginPresenterModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    LoginContract.View provideView() {
        return activity;
    }
}