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
public Call<Alarm>resetTamper(String JSESSIONID, Alarm alarm){
    return request().resetTamper(JSESSIONID,alarm);
}

    public Call<Vehicle> postaddUnit (String jsession, String vehiIdno, String devIdno, String devType, int factoryType,String companyName, String account){
        return request().addUnit(jsession,vehiIdno,devIdno,devType,factoryType,companyName, account);
    }

    public Call<Company> creatUSer (HashMap<String, Object> data){
        return request().createUser(data);
    }
    public Call<Device> setupAlarm(String deviceId, ArrayList<String> time){
        return request().setWakeUpAlarm(deviceId,time);
    }

}
