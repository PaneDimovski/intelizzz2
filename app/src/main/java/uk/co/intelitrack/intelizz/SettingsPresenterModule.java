package uk.co.intelitrack.intelizz;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;
import uk.co.intelitrack.intelizzz.components.preview.PreviewActivity;
import uk.co.intelitrack.intelizzz.components.preview.PreviewContract;



@PerActivity
@Module
public class SettingsPresenterModule {
    private final SettingsActivity activity;


    public SettingsPresenterModule(SettingsActivity activity) {
        this.activity = activity;


    }

    @Provides
    SettingsContract.View provideView() {
        return activity;
    }


}
