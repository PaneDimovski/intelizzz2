package uk.co.intelitrack.intelizzz.components.main;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class MainModule {
    private final MainActivity mActivity;

    public MainModule(MainActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    MainPresenter provideMainPresenter(IntelizzzRepository repository) {
        return new MainPresenter(repository, mActivity);
    }
}