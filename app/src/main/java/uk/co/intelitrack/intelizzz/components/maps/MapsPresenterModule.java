package uk.co.intelitrack.intelizzz.components.maps;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class MapsPresenterModule {
    private final MapsActivity activity;

    public MapsPresenterModule(MapsActivity activity) {
        this.activity = activity;
    }

    @Provides
    MapsContract.View provideView() {
        return activity;
    }
}
