package uk.co.intelitrack.intelizzz.common.injection.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    public Context getContext() {
        return mContext;
    }
}