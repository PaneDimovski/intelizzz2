package uk.co.intelitrack.intelizzz;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import uk.co.intelitrack.intelizzz.common.injection.component.ApplicationComponent;
import uk.co.intelitrack.intelizzz.common.injection.component.DaggerApplicationComponent;
import uk.co.intelitrack.intelizzz.common.injection.modules.ApplicationModule;
import uk.co.intelitrack.intelizzz.common.injection.modules.RemoteModule;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class IntelizzzApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .remoteModule(new RemoteModule())
                .build();
        JodaTimeAndroid.init(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
