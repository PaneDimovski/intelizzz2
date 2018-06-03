package uk.co.intelitrack.intelizzz.common.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.intelitrack.intelizzz.BuildConfig;
import uk.co.intelitrack.intelizzz.common.api.ApiInterface;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzDataSource;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class RemoteModule {

    @NonNull
    public static Gson getGson() {
        return new GsonBuilder()
                .create();
    }

    @NonNull
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(5, TimeUnit.SECONDS);

        return builder.build();
    }

    @NonNull
    public static ApiInterface getApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Singleton
    @Provides
    SharedPreferencesUtils provideSharedPreferencesUtils(Context context) {
        return new SharedPreferencesUtils(context);
    }

    @Singleton
    @Provides
    IntelizzzDataSource provideIntelizzzDataSource(ApiInterface apiInterface) {
        return new IntelizzzDataSource(apiInterface);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return getOkHttpClient();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return getGson();
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(Gson gson, OkHttpClient okHttpClient) {
        return getApiInterface(getRetrofit(gson, okHttpClient));
    }

    @NonNull
    public Retrofit getRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}