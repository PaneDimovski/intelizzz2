package uk.co.intelitrack.intelizzz.components.groups;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class GroupsPresenterModule {
    private final GroupsActivity activity;

    public GroupsPresenterModule(GroupsActivity activity) {
        this.activity = activity;
    }

    @Provides
    GroupsContract.View provideView() {
        return activity;
    }
}
