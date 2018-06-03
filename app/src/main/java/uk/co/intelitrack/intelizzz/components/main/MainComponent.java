package uk.co.intelitrack.intelizzz.components.main;

import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {MainPresenterModule.class, MainModule.class,})
public interface MainComponent {
    void inject(MainActivity activity);
}