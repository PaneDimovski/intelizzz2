package uk.co.intelitrack.intelizzz.components.main;

import android.content.Intent;
import android.text.TextUtils;

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
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class MainPresenter implements MainContract.Presenter,
        FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener {

    //region Fields
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private IntelizzzRepository mRepository;
    private MainContract.View mView;
    private List<SuggestionsItem> mAllSuggestionsItems;
    //endregion

    //region Constructors
    public MainPresenter(IntelizzzRepository repository, MainContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }
    //endregion

    //region BasePresenter Methods
    @Override
    public void subscribe(Intent intent) {
        checkAndFetchVehicles();
        checkAndFetchGroups();
        mView.setListeners();
    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }
    //endregion

    //region MainPresenter Methods
    @Override
    public void onHomeClick() {
        logout();
    }
    //endregion

    //region Helpers Methods
    private void checkAndFetchVehicles() {
        mView.toogleProgressBar(true);
        if (mRepository.getVehicles().isEmpty()) {
            mSubscriptions.add(mRepository.getVehiclesResponse()
                    .subscribe(
                            x -> {
                                if (x.getResult().equals("0")) {
                                    Timber.d("getUnits successful");
                                    mView.showUnitsNumber(x.getVehicles().length);
                                    if (x.getVehicles().length != 0) {

                                        int chunk = 60;
                                        for (int i = 0; i < x.getVehicles().length; i += chunk) {
                                            getVehicleExtraData(Arrays.asList(Arrays.copyOfRange(x.getVehicles(), i, Math.min(x.getVehicles().length, i + chunk))), false);
                                        }
                                    } else {
                                        mView.toogleProgressBar(false);
                                    }
                                } else if (x.getResult().equals("5")) {
                                    mView.toogleProgressBar(false);
                                    logout();
                                } else {
                                    mView.toogleProgressBar(false);
                                    logout();
                                    Timber.d("getUnits is not successful");
                                }
                            },
                            e -> {
                                Timber.e(e);
                                mView.toogleProgressBar(false);
                                logout();
                            }));
        }
    }

    private void checkAndFetchGroups() {
        mView.toogleProgressBar(true);
        if (mRepository.getCompanies().isEmpty()) {
            mSubscriptions.add(mRepository.getGroupsResponse()
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(
                            x -> {
                                if (x.length > 0) {
                                    Timber.d("getCompanies successful");
                                    mView.showGroupsNumber(mRepository.getGroupsAndCompaniesNumber());

                                    if (!mRepository.getAllVehiclesFromCompanies().isEmpty()) {
                                        getVehicleExtraData(mRepository.getAllVehiclesFromCompanies(), true);
                                    } else {
                                        mView.toogleProgressBar(false);
                                    }
                                } else {
                                    Timber.d("getCompanies is not successful");
                                    mView.toogleProgressBar(false);
                                    logout();
                                }
                            },
                            e -> {
                                Timber.e(e);
                                mView.toogleProgressBar(false);
                                logout();
                            }));
        }
    }

    private void logout() {
        mSubscriptions.add(mRepository.logout()
                .subscribe(
                        x -> {
                            if (x.getResult().equals("0")) {
                                Timber.d("logout successful");
                            } else {
                                Timber.d("logout is not successful");
                            }
                            mView.startLoginActivity();
                        },
                        e -> {
                            Timber.e(e);
                            mView.startLoginActivity();
                        }));
    }

    private void getVehicleExtraData(List<Vehicle> vehicles, boolean isGroup) {
        if (vehicles.isEmpty()) {
            return;
        }
        String[] ids = new String[vehicles.size()];

        for (int i = 0; i < vehicles.size(); i++) {
            if (isGroup) {
                ids[i] = vehicles.get(i).getName();
            } else {
                if (vehicles.get(i).getDl() != null) {
                    ids[i] = vehicles.get(i).getDl()[0].getId();
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        getFirstAlarmType(ids, sdf.format(calendar.getTime()), sdf.format(endDate), isGroup, "0");
    }

    private void getFirstAlarmType(String[] ids, String beginTime, String endTime, boolean isGroup, String currentPage) {
        mSubscriptions.add(
                mRepository.getDeviceAlarm(ids, beginTime, endTime, Constants.FIRST_TYPE_ALARM, isGroup, currentPage)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                x -> {
                                    if (x.getResult().equals("0")) {
                                        if (x.getPagination() != null && x.getPagination().getHasNextPage()) {
                                            getFirstAlarmType(ids, beginTime, endTime, isGroup, x.getPagination().getNextPage());
                                        } else {
                                            getSecondAlarmType(ids, beginTime, endTime, isGroup, "0");
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

    private void getSecondAlarmType(String[] ids, String beginTime, String endTime, boolean isGroup, String currentPage) {
        mSubscriptions.add(
                mRepository.getDeviceAlarm(ids, beginTime, endTime, Constants.SECOND_TYPE_ALARM, isGroup, currentPage)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                x -> {
                                    if (x.getResult().equals("0")) {
                                        Timber.d("getSecondAlarmType successful");
                                        if (x.getPagination() != null && x.getPagination().getHasNextPage()) {
                                            getSecondAlarmType(ids, beginTime, endTime, isGroup, x.getPagination().getNextPage());
                                        } else {
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

    private String getIdByName(String name) {
        for (SuggestionsItem suggestionsItem : mAllSuggestionsItems) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (suggestionsItem.getName().equalsIgnoreCase(name)) {
                return suggestionsItem.getId();
            }
        }
        return null;
    }
    //endregion

    //region FloatingSearchView Methods
    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (mAllSuggestionsItems == null || mAllSuggestionsItems.isEmpty()) {
            mAllSuggestionsItems = mRepository.getAllSuggestionsVehicles();
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
        String id = getIdByName(searchSuggestion.getBody().toString().trim());
        if (!TextUtils.isEmpty(id)) {
            mView.startUnitActivity(id);
        }
    }

    @Override
    public void onSearchAction(String currentQuery) {
        mView.getSearchView().showProgress();
        mView.getSearchView().swapSuggestions(filter(currentQuery));
        mView.getSearchView().hideProgress();
    }
    //endregion
}