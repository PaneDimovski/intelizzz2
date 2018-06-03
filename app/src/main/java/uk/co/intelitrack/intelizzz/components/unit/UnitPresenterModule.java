package uk.co.intelitrack.intelizzz.components.unit;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class UnitPresenterModule {
    private final UnitActivity activity;

    public UnitPresenterModule(UnitActivity activity) {
        this.activity = activity;
    }

    @Provides
    UnitContract.View provideView() {
        return activity;
    }
}
