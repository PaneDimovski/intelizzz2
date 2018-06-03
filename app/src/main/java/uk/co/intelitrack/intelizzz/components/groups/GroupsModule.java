package uk.co.intelitrack.intelizzz.components.groups;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class GroupsModule {
    private final GroupsActivity mActivity;

    public GroupsModule(GroupsActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    GroupsPresenter provideGroupsPresenter(IntelizzzRepository repository) {
        return new GroupsPresenter(repository, mActivity);
    }

    @Provides
    UnassignedVehiclessDataAdapter provideUnassignedVehiclessDataAdapter(IntelizzzRepository repository) {
        return new UnassignedVehiclessDataAdapter(repository);
    }

    @Provides
    VehiclesDataAdapter provideVehiclesDataAdapter() {
        return new VehiclesDataAdapter();
    }
}