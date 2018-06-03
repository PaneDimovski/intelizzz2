package uk.co.intelitrack.intelizzz.components.maps;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class MapsModule {
    private final MapsActivity mActivity;

    public MapsModule(MapsActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    MapsPresenter provideMapsPresenter(IntelizzzRepository repository) {
        return new MapsPresenter(repository, mActivity);
    }
}