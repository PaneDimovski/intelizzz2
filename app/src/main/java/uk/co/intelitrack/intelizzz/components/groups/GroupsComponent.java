package uk.co.intelitrack.intelizzz.components.groups;

import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {GroupsPresenterModule.class, GroupsModule.class,})
public interface GroupsComponent {
    void inject(GroupsActivity activity);
}