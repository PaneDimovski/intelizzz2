package uk.co.intelitrack.intelizzz.components.crud_groups;

import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {CrudGroupPresenterModule.class, CrudGroupModule.class,})
public interface CrudGroupComponent {
    void inject(CrudGroupActivity activity);
}