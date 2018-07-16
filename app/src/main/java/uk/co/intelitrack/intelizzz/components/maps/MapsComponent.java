package uk.co.intelitrack.intelizzz.components.maps;


import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;


/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {MapsPresenterModule.class, MapsModule.class,})
public interface MapsComponent {
    void inject(MapsActivity activity);
}