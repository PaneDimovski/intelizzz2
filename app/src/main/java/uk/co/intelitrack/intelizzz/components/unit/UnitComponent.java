package uk.co.intelitrack.intelizzz.components.unit;


import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {UnitPresenterModule.class, UnitModule.class,})
public interface UnitComponent {
    void inject(UnitActivity activity);
}