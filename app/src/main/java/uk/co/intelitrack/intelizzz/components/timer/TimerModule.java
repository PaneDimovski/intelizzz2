package uk.co.intelitrack.intelizzz.components.timer;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.components.maps.MapsActivity;
import uk.co.intelitrack.intelizzz.components.maps.MapsPresenter;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class TimerModule {
    private final TimerActivity mActivity;

    public TimerModule(TimerActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    TimerPresenter provideMapsPresenter(IntelizzzRepository repository) {
        return new TimerPresenter(repository, mActivity);
    }
}