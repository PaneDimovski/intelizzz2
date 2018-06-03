package uk.co.intelitrack.intelizzz.components.unit;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class UnitModule {
    private final UnitActivity mActivity;

    public UnitModule(UnitActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    UnitPresenter provideUnitPresenter(IntelizzzRepository repository) {
        return new UnitPresenter(repository, mActivity);
    }
}