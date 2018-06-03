package uk.co.intelitrack.intelizzz.common.injection.component;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.common.api.ApiInterface;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.injection.modules.ApplicationModule;
import uk.co.intelitrack.intelizzz.common.injection.modules.RemoteModule;
import uk.co.intelitrack.intelizzz.common.injection.modules.RepositoryModule;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Singleton
@Component(modules = {ApplicationModule.class, RemoteModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(IntelizzzApplication application);

    Context getContext();

    OkHttpClient getOkHttpClient();

    Gson getGson();

    ApiInterface getApiInterface();

    IntelizzzRepository getIntelizzzRepository();

    SharedPreferencesUtils getSharedPreferencesUtils();
}
