package uk.co.intelitrack.intelizz;

import dagger.Component;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;
import uk.co.intelitrack.intelizzz.components.preview.PreviewActivity;
import uk.co.intelitrack.intelizzz.components.preview.PreviewModule;
import uk.co.intelitrack.intelizzz.components.preview.PreviewPresenterModule;


@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {SettingsPresenterModule.class, SettingsModule.class,})
public interface SettingsComponent {
    void inject(SettingsActivity activity);


}