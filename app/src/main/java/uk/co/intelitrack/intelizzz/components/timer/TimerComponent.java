package uk.co.intelitrack.intelizzz.components.timer;


import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;
import uk.co.intelitrack.intelizzz.components.maps.MapsActivity;
import uk.co.intelitrack.intelizzz.components.maps.MapsModule;
import uk.co.intelitrack.intelizzz.components.maps.MapsPresenterModule;


/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {TimerPresenterModule.class, TimerModule.class,})
public interface TimerComponent {
    void inject(TimerActivity activity);
}