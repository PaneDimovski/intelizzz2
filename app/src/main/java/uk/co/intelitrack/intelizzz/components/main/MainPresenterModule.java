package uk.co.intelitrack.intelizzz.components.main;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class MainPresenterModule {
    private final MainActivity activity;

    public MainPresenterModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    MainContract.View provideView() {
        return activity;
    }
}
