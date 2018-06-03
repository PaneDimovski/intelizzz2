package uk.co.intelitrack.intelizzz.components.groups;

import android.content.Intent;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

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
import uk.co.intelitrack.intelizzz.common.data.SuggestionsItem;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class GroupsPresenter implements GroupsContract.Presenter,
        FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener {

    //region Fields
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private IntelizzzRepository mRepository;
    private GroupsContract.View mView;
    private List<SuggestionsItem> mAllSuggestionsItems;
    private String mGroupName;
    private String mGroupId;
    private String mCompanyId;
    //endregion

    public GroupsPresenter(IntelizzzRepository repository, GroupsContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }

    //region BasePresenter
    @Override
    public void subscribe(Intent intent) {
        mView.initGroupAdapters();
        mView.setUnitNumber(String.valueOf(mRepository.getUnassingVehiclesFromCompanies().size()));
    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }
    //endregion

    //region GroupsContract.Presenter
    @Override
    public void onArrowRightClicked(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            //TODO: put this in strings
            mView.showToastMessage("Please select some vehicles");
            return;
        }
        if (mGroupName == null || mGroupId == null) {
            //TODO: put this in strings
            mView.showToastMessage("Please select group");
            return;
        }
        for (Vehicle vehicle : vehicles) {
            addVehicle(vehicle.getId(), mCompanyId);
        }
    }

    @Override
    public void onArrowLeftClicked(List<Vehicle> unassignedVehicles) {
        if (unassignedVehicles == null || unassignedVehicles.isEmpty()) {
            //TODO: put this in strings
            mView.showToastMessage("Please select some vehicles");
            return;
        }
        if (mGroupName == null || mGroupId == null) {
            //TODO: put this in strings
            mView.showToastMessage("Please select group");
            return;
        }
        for (Vehicle vehicle : unassignedVehicles) {
            addVehicle(vehicle.getId(), mGroupId);
        }
    }
    //endregion

    //region FloatingSearchView Methods
    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (mAllSuggestionsItems == null || mAllSuggestionsItems.isEmpty()) {
            mAllSuggestionsItems = mRepository.getAllSuggestionsGroups();
        }
        if (!oldQuery.equals("") && newQuery.equals("")) {
            mView.getSearchView().clearSuggestions();
        } else {
            mView.getSearchView().showProgress();
            mView.getSearchView().swapSuggestions(filter(newQuery));
            mView.getSearchView().hideProgress();
        }
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        Group group = getGroupByName(searchSuggestion.getBody().toString().trim());
        if (group != null) {
            mView.showGroupData(Arrays.asList(group.getVehicles()));
        }
    }

    @Override
    public void onSearchAction(String currentQuery) {
        mView.getSearchView().showProgress();
        mView.getSearchView().swapSuggestions(filter(currentQuery));
        mView.getSearchView().hideProgress();
    }
    //endregion

    //region Helpers Methods
    private List<SuggestionsItem> filter(String text) {
        List<SuggestionsItem> temp = new ArrayList();
        for (SuggestionsItem suggestionsItem : mAllSuggestionsItems) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (suggestionsItem.getName().toLowerCase().contains(text.toLowerCase())) {
                temp.add(suggestionsItem);
            }
        }
        return temp;
    }

    private Group getGroupByName(String name) {
        for (Company company : mRepository.getCompanies()) {
            for (Group group : company.getGroups()) {
                if (group.getName().equalsIgnoreCase(name)) {
                    mGroupName = group.getName();
                    mGroupId = group.getId();
                    mCompanyId = company.getId();
                    mView.setCompany(company.getId());
                    return group;
                }
            }
        }
        return null;
    }

    private void addVehicle(String vehicleId, String groupId) {
        mView.toogleProgressBar(true);
        mSubscriptions.add(mRepository.addVehicleToGroup(groupId, vehicleId)
                .subscribe(
                        x -> {
                            mRepository.clearCompanies();
                            checkAndFetchGroups();
                        },
                        e -> {
                            Timber.e(e);
                            mView.toogleProgressBar(false);
                        }));
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
                        .repeat()
                        .subscribe(
                                x -> {
                                    if (x.getResult().equals("0")) {
                                        if (x.getPagination() != null && x.getPagination().getHasNextPage()) {
                                            getSecondAlarmType(ids, beginTime, endTime, x.getPagination().getNextPage());
                                        } else {
                                            Timber.d("getSecondAlarmType successful");

                                            Group group = getGroupByName(mGroupName);
                                            if (group != null) {
                                                mView.showGroupData(Arrays.asList(group.getVehicles()));
                                            }
                                            mView.refreshUnassignedVehicles();
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
