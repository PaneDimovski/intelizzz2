package uk.co.intelitrack.intelizzz.components.preview;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class PreviewPresenter implements PreviewContract.Presenter, GroupsClickListener {

    //region Fields
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private IntelizzzRepository mRepository;
    private PreviewContract.View mView;
    private boolean mIsGroup;
    private String mGroupId = "";
    private String forDelete;
    private String s;
    //endregion

    public PreviewPresenter(IntelizzzRepository repository, PreviewContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }

    //region BasePresenter Methods
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

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }
    //endregion

    @Override
    public void refreshGroups() {
        List<ParentVehicle> newGroupList = new ArrayList<>();
        if (!mRepository.getCompanies().isEmpty()) {
            for (Company company : mRepository.getCompanies()) {
                List<Vehicle> vehicles = company.getAllVehicles();
                vehicles.addAll(Arrays.asList(company.getUnassignedVehicles()));
                newGroupList.add(new ParentVehicle(company.getName(), vehicles, company.getId(), true));
                for (Group group : company.getGroups()) {
                    newGroupList.add(new ParentVehicle(group.getName(), Arrays.asList(group.getVehicles()), group.getId(), false));
                }
            }
        }
        mView.setGroups(newGroupList);
    }

    //region PreviewPresenter Methods
    @Override
    public void onUnitClick(String id) {
        mView.startUnitActivity(id);
    }


    public void onUnitClick2(String id) {
        mView.cancelTamper();
    }


    @Override
    public void onGroupClick(String id, boolean isGroup) {
        mView.startUnitActivity(id);
    }

    @Override
    public void onMoveClick() {
        mView.startGroupsActivity();
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
//        mSubscriptions.add(mRepository.deleteGroup(s,mGroupId)
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
    public void onCompanyItemClick(String id) {
        if (TextUtils.isEmpty(mGroupId)) {
            mGroupId = id;

            for (Company company : mRepository.getCompanies()) {
                if (company.getId().equals(id)) {
                    List<Vehicle> vehicles = company.getAllVehicles();
                    vehicles.addAll(Arrays.asList(company.getUnassignedVehicles()));
                    mView.setGroup(new ParentVehicle(company.getName(), vehicles, company.getId(), true));
                }
            }
        } else {
            mGroupId = null;
            refreshGroups();
        }
    }

    @Override
    public void onGroupItemClick(String groupId) {
        if (TextUtils.isEmpty(mGroupId)) {
            mGroupId = groupId;
            forDelete = groupId;

            for (Company company : mRepository.getCompanies()) {
                for (Group group : company.getGroups()) {
                    if (group.getId().equals(groupId)) {
                        mView.setGroup(new ParentVehicle(group.getName(), Arrays.asList(group.getVehicles()), group.getId(), false));
                    }
                }
            }
        } else {
            mGroupId = null;
            forDelete = null;
            refreshGroups();
        }
    }

    @Override
    public void onGroupChildItemClick(String vehicleId) {
        onUnitClick(vehicleId);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDelete(View view, int position) {

    }


    private void checkAndFetchGroups() {
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
    //endregion
}
