package uk.co.intelitrack.intelizzz.components.preview;

import dagger.Component;
import uk.co.intelitrack.intelizz.SettingsActivity;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;



@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {PreviewPresenterModule.class, PreviewModule.class,})
public interface PreviewComponent {
    void inject(PreviewActivity activity);


}