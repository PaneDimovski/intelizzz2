package uk.co.intelitrack.intelizzz.components.crud_groups;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class CrudGroupPresenterModule {
    private final CrudGroupActivity activity;

    public CrudGroupPresenterModule(CrudGroupActivity activity) {
        this.activity = activity;
    }

    @Provides
    CrudGroupContract.View provideView() {
        return activity;
    }
}
