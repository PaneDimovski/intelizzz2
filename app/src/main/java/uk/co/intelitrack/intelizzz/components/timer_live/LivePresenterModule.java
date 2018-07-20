package uk.co.intelitrack.intelizzz.components.timer_live;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.components.timer.TimerActivity;
import uk.co.intelitrack.intelizzz.components.timer.TimerContract;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class LivePresenterModule {
    private final LiveActivity activity;

    public LivePresenterModule(LiveActivity activity) {
        this.activity = activity;
    }

    @Provides
    LiveContract.View provideView() {
        return activity;
    }
}
