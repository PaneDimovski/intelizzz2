package uk.co.intelitrack.intelizzz.components.timer_live;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;


/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class LiveModule {
    private final LiveActivity mActivity;

    public LiveModule(LiveActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    LivePresenter provideMapsPresenter(IntelizzzRepository repository) {
        return new LivePresenter(repository, mActivity);
    }
}