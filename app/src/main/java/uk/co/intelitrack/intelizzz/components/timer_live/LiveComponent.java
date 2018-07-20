package uk.co.intelitrack.intelizzz.components.timer_live;


import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;
import uk.co.intelitrack.intelizzz.components.timer.TimerActivity;
import uk.co.intelitrack.intelizzz.components.timer.TimerModule;
import uk.co.intelitrack.intelizzz.components.timer.TimerPresenterModule;


/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {LivePresenterModule.class, LiveModule.class,})
public interface LiveComponent {
    void inject(LiveActivity activity);
}