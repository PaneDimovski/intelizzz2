package uk.co.intelitrack.intelizzz.common.api;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Response;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface ApiInterface {
    @GET("StandardApiAction_login.action")
    Call<Token> login1(@Query("account") String account, @Query("password") String password);

    @GET("StandardApiAction_login.action")
    Single<Token> login(@Query("account") String account, @Query("password") String password);

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
    @POST("808gps/openPhp/deleteGroupById.php")
    Single<String> deleteGroup(@Field("jsession") String jsession, @Field("group_id") String groupId, @Field("company_id") String companyId);

    @FormUrlEncoded
    @POST("808gps/openPhp/updateMotorcade.php")
    Single<String> updateMotorcade(@Field("jsession") String jsession, @Field("company_id") String companyId, @Field("vehicle_id") String vehicleId);
}
