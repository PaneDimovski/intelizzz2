package uk.co.intelitrack.intelizzz.common.api;

import android.support.annotation.NonNull;
import android.telecom.Call;

import io.reactivex.Single;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Response;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class IntelizzzDataSource {
    @NonNull
    private final ApiInterface apiInterface;

    public IntelizzzDataSource(@NonNull ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }
//    public retrofit2.Call<Token>login1(String username, String password){
//        return apiInterface.login1(username, password);
//    }

    public Single<Token> login(String username, String password) {
        return apiInterface.login(username, password);
    }
    public Single<Token> login1(String username, String password) {
        return apiInterface.login1(username, password);
    }

    public Single<Response> logout(String token) {
        return apiInterface.logout(token);
    }

    public Single<Response> getVehiclesResponse(String token) {
        return apiInterface.getVehicle(token);
    }

    public Single<Company[]> getGroupsResponse(String token) {
        return apiInterface.getGroups(token);
    }

    public Single<Response> getDeviceByVehicle(String token, String vehicleId) {
        return apiInterface.getDeviceByVehicle(token, vehicleId);
    }

    public Single<Response> getDeviceAlarm(String token, String beginTime, String endTime, String deviceIds,
                                           String vehiclesIds, String armType, String currentPage, String pageRecords) {
        return apiInterface.getDeviceAlarm(token, beginTime, endTime, deviceIds, vehiclesIds, armType, currentPage, pageRecords);
    }

    public Single<Response> getDeviceStatus(String token, String vehicleId) {
        return apiInterface.getDeviceStatus(token, vehicleId);
    }

    public Single<Response> getVehicleTravelPaths(String token, String vehicleId, String beginTime,
                                                  String endTime, String pageRecords) {
        return apiInterface.getTravelPaths(token, vehicleId, beginTime, endTime, pageRecords);
    }

    public Single<Company[]> addGroup(String token, String name, String id) {
        return apiInterface.addGroup(token, name, id);
    }

    public Single<String> deleteGroup(String token, String groupId) {
        return apiInterface.deleteGroup(token, groupId);
    }

    public Single<String> updateUnassignedVehicles(String token, String companyId, String vehicleId) {
        return apiInterface.updateMotorcade(token, companyId, vehicleId);
    }
}
