package uk.co.intelitrack.intelizzz.components.timer;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.components.maps.MapsActivity;
import uk.co.intelitrack.intelizzz.components.maps.MapsContract;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class TimerPresenterModule {
    private final TimerActivity activity;

    public TimerPresenterModule(TimerActivity activity) {
        this.activity = activity;
    }

    @Provides
    TimerContract.View provideView() {
        return activity;
    }
}
