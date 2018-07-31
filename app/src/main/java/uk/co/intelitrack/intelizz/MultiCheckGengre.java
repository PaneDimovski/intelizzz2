package uk.co.intelitrack.intelizz;

import android.content.Intent;
import android.text.TextUtils;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Response;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Anti on 7/27/2018.
 */

public class MultiCheckGengre extends MultiCheckExpandableGroup  implements SettingsContract.Presenter {


    SettingsPresenter presenter;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private  SharedPreferencesUtils mSharedPreferencesUtils;
    private IntelizzzRepository mRepository;
    private SettingsContract.View mView;
    private boolean mIsGroup;
    private String mGroupId ;
    private String forDelete;
    private Vehicle mVehicle;
    private String mId;
    private String mName;
    private boolean mIsCompany;
    private List<Vehicle> mVehicles;



    public MultiCheckGengre(String name, List<Vehicle> items, String id, boolean isCompany) {
        super(name, items);
        this.mName = name;
        this.mVehicles = items;
        this.mId = id;
        this.mIsCompany = isCompany;

    }
    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Vehicle> getVehicles() {
        return mVehicles;
    }

    @Override
    public void subscribe(Intent intent) {


        mIsGroup = intent.getBooleanExtra(Constants.IS_GROUP, false);
        if (mIsGroup) {
            if (mRepository.getCompanies().isEmpty()) {
                mView.startMainActivity();
            } else {
                mView.setGroupNumber(mRepository.getGroupsAndCompaniesNumber());
                mView.setGroupsListVisible();
            }
        } else {
            if (mRepository.getVehicles().isEmpty()) {
                mView.startMainActivity();
            } else {
                mView.setVehicles(mRepository.getVehicles());
                mView.setVehiclesListVisible();

//                mView.setUnitsList();
            }
        }
    }



    public void setmVehicles(List<Vehicle> mVehicles) {
        this.mVehicles = mVehicles;
    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }
    //endregion
    @Override
    public String getDeviceId() {
        //String devi = mSharedPreferencesUtils.getSharedPreferencesString(Constants.OLI_ID);
        // devi = mVehicle.getId();
        if (mVehicle.getDl() != null && !TextUtils.isEmpty(mVehicle.getDevice().getId())) {
            mSubscriptions.add(
                    mRepository.getDeviceStatus(mVehicle.getDevice().getId())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(
                                    (Response x) -> {
                                        if (x.getResult().equals("0")) {
                                            Timber.d("getUnits successful");
                                            if (x.getStatuses() != null && x.getStatuses().length != 0) {
                                                String deviceID = mVehicle.getDevice().getId();
                                                mSharedPreferencesUtils.setSharedPreferencesString(Constants.DEVICEID,deviceID);
                                            }
                                            mView.toogleProgressBar(false);
                                        } else {
                                            Timber.d("getUnits is not successful");
                                            mView.toogleProgressBar(false);
                                        }
                                    },
                                    e -> {
                                        Timber.e(e);
                                        mView.toogleProgressBar(false);
                                    }));
        }
        return null;
    }

    @Override
    public void refreshGroups() {
        List<MultiCheckGengre> newGroupList = new ArrayList<>();
        if (!mRepository.getCompanies().isEmpty()) {
            for (Company company : mRepository.getCompanies()) {
                List<Vehicle> vehicles = company.getAllVehicles();
                vehicles.addAll(Arrays.asList(company.getUnassignedVehicles()));
                newGroupList.add(new MultiCheckGengre(company.getName(), vehicles, company.getId(), true));
                for (Group group : company.getGroups()) {
                    newGroupList.add(new MultiCheckGengre(group.getName(), Arrays.asList(group.getVehicles()), group.getId(), false));
                }
            }
        }
    }





    //region PreviewPresenter Methods
    @Override
    public void onUnitClick(String id) {
        // mView.startUnitActivity(id);

        // getDeviceId();
    }


    public void onUnitClick2(String id) {
//        mView.startUnitActivity(id);
    }


    @Override
    public void onGroupClick(String id, boolean isGroup) {
        mView.startUnitActivity(id);
    }

    @Override
    public void onMoveClick() {
        mView.startGroupsActivity();
    }

    public boolean isCompany() {
        return mIsCompany;
    }

    private boolean isGeo(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            return true;
        }
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.hasGeofenceAlarm()) {
                return false;
            }
        }
        return true;
    }

    private boolean isWarning(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            return false;
        }
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isWarning()) {
                return false;
            }
        }
        return true;
    }

    private int getActiveDaysNumber() {
        int result = 0;
        for (int i = 0; i <= 6; i++) {
            if (isActiveDay(getVehicles(), i)) {
                result++;
            }
        }
        return result;
    }

    private boolean isActiveDay(List<Vehicle> vehicles, int day) {
        if (vehicles == null || vehicles.isEmpty()) {
            return true;
        }
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getDays() == null || vehicle.getDays().length == 0 || !vehicle.getDays()[day]) {
                return false;
            }
        }
        return true;
    }

    public static class NameComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    }

    public static class IdComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            return p1.getId().compareToIgnoreCase(p2.getId());
        }
    }

    public static class GeoComparator implements Comparator<MultiCheckGengre> {
        public int compare(MultiCheckGengre p1, MultiCheckGengre p2) {
            return Boolean.compare(p1.isGeo(p1.getVehicles()), p2.isGeo(p2.getVehicles()));
        }
    }

    public static class WarningComparator implements Comparator<MultiCheckGengre> {
        public int compare(MultiCheckGengre p1, MultiCheckGengre p2) {
            return Boolean.compare(p1.isWarning(p1.getVehicles()), p2.isWarning(p2.getVehicles()));
        }
    }

    public static class DaysComparator implements Comparator<MultiCheckGengre> {
        public int compare(MultiCheckGengre p1, MultiCheckGengre p2) {
            int age1 = p1.getActiveDaysNumber();
            int age2 = p2.getActiveDaysNumber();

            if (age1 == age2)
                return 0;
            else if (age1 > age2)
                return 1;
            else
                return -1;
        }
    }
    @Override
    public void onDeleteClick() {
//        if (TextUtils.isEmpty(forDelete)) {
//            //TODO: put this in  strings
//            mView.showToastMessage("Please select group");
//            return;
//        }
//
//        mView.toogleProgressBar(true);
//        mSubscriptions.add(mRepository.deleteGroup(mGroupId)
//                .subscribe(
//                        x -> {
//                            mRepository.clearCompanies();
//                            checkAndFetchGroups();
//                            mView.showToastMessage("Group successfully deleted");
//                        },
//                        e -> {
//                            Timber.e(e);
//                            mView.showToastMessage("Delete group fail");
//                            mView.toogleProgressBar(false);
//                        }));
    }

    @Override
    public void onDelete1(String id) {

    }

    @Override
    public void onDelete2(String id, String companija) {

    }

    @Override
    public void onDelete3(String jsesion, String id, String companija) {

    }

    @Override
    public void onDelete4(String jsesion, String id) {

    }

    @Override
    public void onDelete4(HashMap<String, String> cookie, String id) {

    }

    @Override
    public void onDelete5(HashMap<String, String> cookie, String id) {

    }

//    @Override
//    public void onCompanyItemClick(String id) {
//        if (TextUtils.isEmpty(mGroupId)) {
//            mGroupId = id;
//
//            for (Company company : mRepository.getCompanies()) {
//                if (company.getId().equals(id)) {
//                    List<Vehicle> vehicles = company.getAllVehicles();
//                    vehicles.addAll(Arrays.asList(company.getUnassignedVehicles()));
//                    mView.setGroup(new ParentVehicle(company.getName(), vehicles, company.getId(), true));
//                }
//            }
//        } else {
//            mGroupId = null;
//            refreshGroups();
//        }
//    }

//    @Override
//    public void onGroupItemClick(String groupId) {
//        if (TextUtils.isEmpty(mGroupId)) {
//            mGroupId = groupId;
//            forDelete = groupId;
//
//            for (Company company : mRepository.getCompanies()) {
//                for (Group group : company.getGroups()) {
//                    if (group.getId().equals(groupId)) {
//                        mView.setGroup(new ParentVehicle(group.getName(), Arrays.asList(group.getVehicles()), group.getId(), false));
//                    }
//                }
//            }
//        } else {
//            mGroupId = null;
//            forDelete = null;
//            refreshGroups();
//        }
//    }
//
//    @Override
//    public void onGroupChildItemClick(Vehicle vehicle) {
////        if (vehicle.isChecked()){
////            vehicle.setChecked(false);
////            list.remove(vehicle.getId());
////        }else {
////            vehicle.setChecked(true);
////            list.add(vehicle.getId());
////        }
//    }

//    @Override
//    public void onItemClick(View view, int position) {
//
//    }
//
//    @Override
//    public void onDelete(View view, int position) {
//
//    }


    @Override
    public void onDelete6(String s, String id) {
        mGroupId = id;
        mSubscriptions.add(mRepository.deleteGroup(s,mGroupId)
                .subscribe(
                        (String x) -> {
                            mRepository.clearCompanies();
                            checkAndFetchGroups2();
                            mView.showToastMessage("Group successfully deleted");
                        },
                        e -> {
                            Timber.e(e);
                            mView.showToastMessage("Delete group fail");
                            mView.toogleProgressBar(false);
                        }));
        refreshGroups();
    }


    public void checkAndFetchGroups2() {
        if (mRepository.getCompanies().isEmpty()) {
            mSubscriptions.add(mRepository.getGroupsResponse()
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(
                            x -> {
                                if (x.length > 0) {
                                    Timber.d("getCompanies successful");
                                    int totalNumberOfGroups = 0;
                                    for (Company company : x) {
                                        totalNumberOfGroups = totalNumberOfGroups + company.getGroups().length;
                                    }

                                    if (!mRepository.getAllVehiclesFromCompanies().isEmpty()) {
                                        getVehicleExtraData(mRepository.getAllVehiclesFromCompanies());
                                    } else {
                                        refreshGroups();
                                        mView.toogleProgressBar(false);
                                    }
                                } else {
                                    Timber.d("getCompanies is not successful");
                                    mView.toogleProgressBar(false);
                                }
                            },
                            e -> {
                                Timber.e(e);
                                mView.toogleProgressBar(false);
                            }));
        }
    }

    private void getVehicleExtraData(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            return;
        }
        String[] ids = new String[vehicles.size()];

        for (int i = 0; i < vehicles.size(); i++) {
            ids[i] = vehicles.get(i).getName();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        getFirstAlarmType(ids, sdf.format(calendar.getTime()), sdf.format(endDate), "0");
    }

    private void getFirstAlarmType(String[] ids, String beginTime, String endTime, String currentPage) {
        mSubscriptions.add(
                mRepository.getDeviceAlarm(ids, beginTime, endTime, Constants.FIRST_TYPE_ALARM, true, currentPage)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                x -> {
                                    if (x.getResult().equals("0")) {
                                        Timber.d("getFirstAlarmType successful");
                                        if (x.getPagination() != null && x.getPagination().getHasNextPage()) {
                                            getFirstAlarmType(ids, beginTime, endTime, x.getPagination().getNextPage());
                                        } else {
                                            getSecondAlarmType(ids, beginTime, endTime, "0");
                                        }
                                    } else {
                                        Timber.d("getFirstAlarmType is not successful");
                                        mView.toogleProgressBar(false);
                                    }
                                },
                                e -> {
                                    Timber.e(e);
                                    mView.toogleProgressBar(false);
                                }));
    }

    private void getSecondAlarmType(String[] ids, String beginTime, String endTime, String currentPage) {
        mSubscriptions.add(
                mRepository.getDeviceAlarm(ids, beginTime, endTime, Constants.SECOND_TYPE_ALARM, true, currentPage)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                x -> {
                                    if (x.getResult().equals("0")) {
                                        Timber.d("getSecondAlarmType successful");
                                        if (x.getPagination() != null && x.getPagination().getHasNextPage()) {
                                            getSecondAlarmType(ids, beginTime, endTime, x.getPagination().getNextPage());
                                        } else {
                                            refreshGroups();
                                            mView.toogleProgressBar(false);
                                        }
                                    } else {
                                        Timber.d("getSecondAlarmType is not successful");
                                        mView.toogleProgressBar(false);
                                    }
                                },
                                e -> {
                                    Timber.e(e);
                                    mView.toogleProgressBar(false);
                                }));
    }
}

