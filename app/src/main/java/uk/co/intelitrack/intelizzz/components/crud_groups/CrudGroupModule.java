package uk.co.intelitrack.intelizzz.components.crud_groups;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class CrudGroupModule {
    private final CrudGroupActivity mActivity;

    public CrudGroupModule(CrudGroupActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    CrudGroupPresenter provideCrudGroupPresenter(IntelizzzRepository repository) {
        return new CrudGroupPresenter(repository, mActivity);
    }
}