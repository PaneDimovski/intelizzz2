package uk.co.intelitrack.intelizzz.common.api;

import android.support.annotation.NonNull;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.SuggestionsItem;
import uk.co.intelitrack.intelizzz.common.data.remote.Alarm;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Device;
import uk.co.intelitrack.intelizzz.common.data.remote.Dl;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.Response;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class IntelizzzRepository {
    @NonNull
    private final IntelizzzDataSource mIntelizzzDataSource;
    @NonNull
    private final SharedPreferencesUtils mSharedPreferencesUtils;

    private List<Vehicle> mVehicles = new ArrayList<>();
    private List<Company> mCompanies = new ArrayList<>();

    @Inject
    public IntelizzzRepository(@NonNull IntelizzzDataSource intelizzzDataSource,
                               @NonNull SharedPreferencesUtils sharedPreferencesUtils) {
        this.mIntelizzzDataSource = intelizzzDataSource;
        this.mSharedPreferencesUtils = sharedPreferencesUtils;
    }

    public Single<Token> login(String username, String password) {
        return mIntelizzzDataSource.login(username, password)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(token -> {
                    mSharedPreferencesUtils.setSharedPreferencesString(Constants.TOKEN, token.getJsession());
                    mSharedPreferencesUtils.setSharedPreferencesString(Constants.USERNAME, username);
                    mSharedPreferencesUtils.setSharedPreferencesString(Constants.PASSWORD, password);
                    return Single.just(token);
                });
    }

    public Single<Response> logout() {
        String token = mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN);
        mSharedPreferencesUtils.setSharedPreferencesString(Constants.TOKEN, "");
        mSharedPreferencesUtils.setSharedPreferencesString(Constants.USERNAME, "");
        mSharedPreferencesUtils.setSharedPreferencesString(Constants.PASSWORD, "");
        mSharedPreferencesUtils.setSharedPreferencesBoolean(Constants.KEEP_SINGED, false);
        mVehicles = new ArrayList<>();
        mCompanies = new ArrayList<>();

        return mIntelizzzDataSource.logout(token)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(response -> {
                    return Single.just(response);
                });
    }

    public Single<Response> getVehiclesResponse() {
        return mIntelizzzDataSource.getVehiclesResponse(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN))
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(vehiclesResponse -> {
                    for (Vehicle vehicle : vehiclesResponse.getVehicles()) {
                        vehicle.setDevice(vehicle.getDl()[0]);
                        mVehicles.add(vehicle);
                    }

                    return Single.just(vehiclesResponse);
                });
    }

    public Single<Company[]> getGroupsResponse() {
        return mIntelizzzDataSource.getGroupsResponse(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN))
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(companies -> {
                    mCompanies = Arrays.asList(companies);
                    return Single.just(companies);
                });
    }

    public Single<Response> getDeviceStatus(String id) {
        return mIntelizzzDataSource.getDeviceStatus(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), id)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    return Single.just(result);
                });
    }

    public Single<Response> getVehicleTravelPaths(String vehicleId, String beginTime,
                                                  String endTime, String pageRecords) {
        return mIntelizzzDataSource.getVehicleTravelPaths(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), vehicleId, beginTime, endTime, pageRecords)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    return Single.just(result);
                });
    }

    public Single<Response> getDeviceByVehicle(String ids) {
        return mIntelizzzDataSource.getDeviceByVehicle(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), ids)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    fillDevicesForCompanyVehicles(result);
                    return Single.just(result);
                });
    }

    public Single<Response> getDeviceAlarm(String[] ids, String beginTime, String endTime, String type, boolean isForCompanies, String currentPage) {
        String finalIds = "";
        for (String id : ids) {
            finalIds = finalIds + id + ",";
        }
        finalIds = finalIds.substring(0, finalIds.length() - 1);
        String devicesId = null;
        String vehiclesId = null;

        if (isForCompanies) {
            vehiclesId = finalIds;
        } else {
            devicesId = finalIds;
        }

        return mIntelizzzDataSource.getDeviceAlarm(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), beginTime, endTime,
                devicesId, vehiclesId, type, String.valueOf(currentPage), "200")
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    List<Vehicle> vehicles = isForCompanies ? getAllVehiclesFromCompanies() : getVehicles();
                    if (type == Constants.FIRST_TYPE_ALARM) {
                        handleFirstTypeAlarm(result, vehicles, isForCompanies);
                    } else {
                        handleSecondTypeAlarm(result, vehicles);
                    }
                    return Single.just(result);
                });
    }

    public Single<String> addVehicleToGroup(String groupId, String vehicleId) {
        return mIntelizzzDataSource.updateUnassignedVehicles(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), groupId, vehicleId)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    return Single.just(result);
                });
    }

    public Single<Company[]> addGroup(String groupName, String companyName) {
        return mIntelizzzDataSource.addGroup(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), groupName, companyName)
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    return Single.just(result);
                });
    }

    public Single<String> deleteGroup(String groupId) {
        return mIntelizzzDataSource.deleteGroup(
                mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN), groupId, getCompanies().get(0).getId())
                .compose(RxUtils.applySingleSchedulers())
                .flatMap(result -> {
                    return Single.just(result);
                });
    }
    //endregion

    //region Helpers Methods
    public List<Vehicle> getVehicles() {
        return mVehicles;
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    public int getGroupsAndCompaniesNumber() {
        int result = 0;
        for (Company company : mCompanies) {
            result++;
            for (Group group : company.getGroups()) {
                result++;
            }
        }
        return result;
    }

    public void addLocallyGroup(Group group) {
        final int N = getCompanies().get(0).getGroups().length;
        getCompanies().get(0).setGroups(Arrays.copyOf(getCompanies().get(0).getGroups(), N + 1));
        getCompanies().get(0).getGroups()[N] = group;
    }

    public Group getGroupById(String id) {
        for (Company company : mCompanies) {
            for (Group group : company.getGroups()) {
                if (group.getId().equals(id)) {
                    return group;
                }
            }
        }
        return null;
    }

    public void clearCompanies() {
        mCompanies = new ArrayList<>();
    }

    public List<Vehicle> getVehiclesFromCompanies() {
        List<Vehicle> results = new ArrayList<>();
        if (mCompanies == null) {
            return results;
        }
        for (Company company : mCompanies) {
            for (Group group : company.getGroups()) {
                for (Vehicle vehicle : group.getVehicles()) {
                    results.add(vehicle);
                }
            }
        }
        return results;
    }

    public List<Vehicle> getAllVehiclesFromCompanies() {
        List<Vehicle> results = new ArrayList<>();
        if (mCompanies == null) {
            return results;
        }
        for (Company company : mCompanies) {
            for (Group group : company.getGroups()) {
                for (Vehicle vehicle : group.getVehicles()) {
                    results.add(vehicle);
                }
            }
            results.addAll(Arrays.asList(company.getUnassignedVehicles()));
        }
        return results;
    }

    public List<Vehicle> getUnassingVehiclesFromCompanies() {
        List<Vehicle> results = new ArrayList<>();
        if (mCompanies == null) {
            return results;
        }
        for (Company company : mCompanies) {
            for (Vehicle vehicle : company.getUnassignedVehicles()) {
                results.add(vehicle);
            }
        }
        return results;
    }

    public List<SuggestionsItem> getAllSuggestionsVehicles() {
        List<SuggestionsItem> suggestionsItems = new ArrayList<>();
        for (Vehicle vehicle : getVehicles()) {
            suggestionsItems.add(new SuggestionsItem(vehicle.getName(), vehicle.getId()));
        }
        for (Vehicle vehicle : getVehiclesFromCompanies()) {
            suggestionsItems.add(new SuggestionsItem(vehicle.getName(), vehicle.getId()));
        }
        return suggestionsItems;
    }

    public List<SuggestionsItem> getAllSuggestionsGroups() {
        List<SuggestionsItem> suggestionsItems = new ArrayList<>();
        for (Company company : getCompanies()) {
            for (Group group : company.getGroups()) {
                suggestionsItems.add(new SuggestionsItem(group.getName(), group.getId()));
            }
        }
        return suggestionsItems;
    }

    public Vehicle getVehicleById(String id) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.addAll(mVehicles);
        vehicles.addAll(getVehiclesFromCompanies());
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    private void fillDevicesForCompanyVehicles(Response response) {
        for (Device device : response.getDevices()) {
            getVehicleById(device.getVid()).setDl(new Dl(device.getDid()));
        }
    }

    private void handleFirstTypeAlarm(Response response, List<Vehicle> vehicles, boolean isGroup) {
        if (response.getResult().equals("0") && response.getAlarms() != null && response.getAlarms().length != 0) {

            for (Vehicle vehicle : vehicles) {
                List<Alarm> alarms = new ArrayList<>();
                for (Alarm alarm : response.getAlarms()) {
                    if (isGroup) {
                        if (alarm.getVid().equals(vehicle.getName())) {
                            hasVehicleAlarmForDate(vehicle, alarm);
                            alarms.add(alarm);
                        }
                    } else {
                        if (vehicle.getDevice() != null && alarm.getDid().equals(vehicle.getDevice().getId())) {
                            hasVehicleAlarmForDate(vehicle, alarm);
                            alarms.add(alarm);
                        }
                    }
                }
                vehicle.setWarning(isAlarmOutOfWakeUpTime(alarms));
            }
        }
    }

    private void handleSecondTypeAlarm(Response response, List<Vehicle> vehicles) {
        if (response.getResult().equals("0") && response.getAlarms() != null && response.getAlarms().length != 0) {
            for (Vehicle vehicle : vehicles) {
                vehicle.setHasGeofenceAlarm(hasGeofenceAlarm(response.getAlarms(), vehicle));
            }
        }
    }

    private boolean isAlarmOutOfWakeUpTime(List<Alarm> alarms) {
        boolean result = false;
        if (alarms.isEmpty()) {
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = GregorianCalendar.getInstance();
        for (Alarm alarm : alarms) {
            cal.setTimeInMillis(Long.parseLong(alarm.getDid()));
            if (!sdf.format(cal.getTime()).equals("08:00")) {
                result = true;
            }
        }

        return result;
    }

    private boolean hasGeofenceAlarm(Alarm[] alarms, Vehicle vehicle) {
        for (Alarm alarm : alarms) {
            if (vehicle.getDl() != null && alarm.getDid() == vehicle.getDevice().getId()) {
                return true;
            }
        }

        return false;
    }

    private void hasVehicleAlarmForDate(Vehicle vehicle, Alarm alarm) {
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.setTimeInMillis(Long.parseLong(alarm.getStm()));
        Calendar endDate = Calendar.getInstance();
        int day = Days.daysBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime())).getDays();

        if (day > -1 && day < 8) {
            vehicle.getDays()[day] = true;
        }
    }
    //endregion
}