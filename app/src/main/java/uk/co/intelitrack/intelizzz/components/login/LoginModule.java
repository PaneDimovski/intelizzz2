package uk.co.intelitrack.intelizzz.components.login;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class LoginModule {
    private final LoginActivity mActivity;

    public LoginModule(LoginActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    DialogUtils provideDialogUtils() {
        return new DialogUtils();
    }

    @Provides
    LoginPresenter provideLoginPresenter(IntelizzzRepository repository, SharedPreferencesUtils sharedPreferencesUtils) {
        return new LoginPresenter(repository, sharedPreferencesUtils, mActivity);
    }
}