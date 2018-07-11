package uk.co.intelitrack.intelizzz.common.api;

import android.content.Context;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;


public class RestApi {

    Token token;
    public static final int request_max_time_in_secconds = 20;
    private Context activity;

    public RestApi(Context context) {
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
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiInterface request() {
        return getRetrofitInstance().create(ApiInterface.class);
    }


    public retrofit2.Call<Token>login3(String username, String password){
        return request().login3(username, password);
    }
    public retrofit2.Call<Token>login4(String username, String password){
        return request().login4(username, password);
    }
//    public Call<Token> postAuthentication(String account, String password) {
//        return request().login1(account,password);
//    }



}
