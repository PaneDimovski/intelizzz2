package uk.co.intelitrack.intelizzz.common.api;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Alarm;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Device;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;


public class RestApi2 {

    Token token;
    public static final int request_max_time_in_secconds = 20;
    private Context activity;

    public RestApi2(Context context) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .writeTimeout(request_max_time_in_secconds,TimeUnit.SECONDS)
                .readTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiInterface request() {
        return getRetrofitInstance().create(ApiInterface.class);
    }


    public Call<Company> creatUSer (HashMap<String, Object> data){
        return request().createUser(data);
    }

}
