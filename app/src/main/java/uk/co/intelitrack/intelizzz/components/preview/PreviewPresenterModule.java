package uk.co.intelitrack.intelizzz.components.preview;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizz.SettingsActivity;
import uk.co.intelitrack.intelizzz.common.data.dagger.PerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@PerActivity
@Module
public class PreviewPresenterModule {
    private final PreviewActivity activity;


    public PreviewPresenterModule(PreviewActivity activity) {
        this.activity = activity;


    }

    @Provides
    PreviewContract.View provideView() {
        return activity;
    }


}
