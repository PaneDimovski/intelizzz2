package uk.co.intelitrack.intelizzz.common.api;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import uk.co.intelitrack.intelizzz.common.data.remote.Alarm;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Device;
import uk.co.intelitrack.intelizzz.common.data.remote.Response;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface ApiInterface {
    @GET("StandardApiAction_login.action")
    Call<Token> login3(@Query("account") String account, @Query("password") String password);

    @GET("808gps/LoginAction_loginMobile.action")
    Call<Token> login4(@Query("userAccount") String account, @Query("password") String password);

    @GET("StandardApiAction_login.action")
    Single<Token> login(@Query("account") String account, @Query("password") String password);

    @GET("808gps/LoginAction_loginMobile.action")
    Single<Token> login1(@Query("userAccount") String account, @Query("password") String password);

    @GET("StandardApiAction_logout.action")
    Single<Response> logout(@Query("jsession") String jsession);

    @GET("StandardApiAction_queryUserVehicle.action")
    Single<Response> getVehicle(@Query("jsession") String jsession);

    @GET("StandardApiAction_getDeviceByVehicle.action")
    Single<Response> getDeviceByVehicle(@Query("jsession") String jsession, @Query("vehiIdno") String vehicleId);

    @GET("StandardApiAction_getDeviceOlStatus.action")
    Single<Response> getDeviceOlStatus(@Query("jsession") String jsession, @Query("vehiIdno") String vehicleId,
                                       @Query("devIdno") String deviceId);

    @GET("StandardApiAction_getDeviceStatus.action")
    Single<Response> getDeviceStatus(@Query("jsession") String jsession, @Query("devIdno") String devIdno);

    @GET("StandardApiAction_queryTrackDetail.action")
    Single<Response> getTravelPaths(@Query("jsession") String jsession, @Query("devIdno") String devIdno,
                                    @Query("begintime") String beginTime, @Query("endtime") String endTime,
                                    @Query("pageRecords") String pageRecords);

    @GET("StandardApiAction_queryAlarmDetail.action?")
    Single<Response> getDeviceAlarm(@Query("jsession") String jsession, @Query("begintime") String beginTime,
                                    @Query("endtime") String endTime, @Query("devIdno") String devIdno,
                                    @Query("vehiIdno") String vehiIdno, @Query("armType") String armType,
                                    @Query("currentPage") String currentPage, @Query("pageRecords") String pageRecords);

    @FormUrlEncoded
    @POST("808gps/openPhp/getGroupsAndSubgroupsByUser.php")
    Single<Company[]> getGroups(@Field("jsession") String jsession);

    @FormUrlEncoded
    @POST("808gps/openPhp/addGroupById.php")
    Single<Company[]> addGroup(@Field("jsession") String jsession, @Field("company_name") String companyName, @Field("company_id") String companyId);

    @FormUrlEncoded
    @POST("808gps/OperationManagement/StandardVehicleTeamAction_delete.action")
    Single<String> deleteGroup(@Header("Cookie") String jsession, @Field("group_id") String groupId);

    @FormUrlEncoded
    @POST("808gps/openPhp/updateMotorcade.php")
    Single<String> updateMotorcade(@Field("jsession") String jsession, @Field("company_id") String companyId, @Field("vehicle_id") String vehicleId);


    @FormUrlEncoded
    @POST("808gps/OperationManagement/StandardVehicleTeamAction_delete.action?id=${groupId}")
    Single<String> delete1(@Header("jsession") String jsession, @Field("id") String groupId);

    @Multipart
    @POST("808gps/LocationManagement/StandardPositionAction_saveAlarmHandle.action")
    Call<Alarm> resetTamper(@Header("Cookie") String jsessionid,
                            @Part("json:") Alarm alarm);


    @POST("http://intelizzz-app.server.pkristijan.xyz/api/admin/accountcreate")
    Call<Company> createUser(@Body Company formData);


    @GET("StandardApiAction_addVehicle.action?")
    Call<Vehicle> addUnit(@Query("jsession") String jsession,
                          @Query("vehiIdno") String vehiIdno,
                          @Query("devIdno") String devIdno,
                          @Query("devType") String devType,
                          @Query("factoryType") int factoryType,
                          @Query("companyName") String compamyName,
                          @Query("account") String account);
    @POST(" http://intelizzz-app.server.pkristijan.xyz/api/alarms/addalarms")
    Call<Device> setWakeUpAlarm(@Header("deviceId") String deviceId, @Body ArrayList<String> wakeupTimes);

    @POST("api/admin/accountcreate")
    Call<Company> createUser (@Body HashMap<String, Object> data);


}
