package uk.co.intelitrack.intelizzz.components.maps;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.widget.DatePicker;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.SuggestionsItem;
import uk.co.intelitrack.intelizzz.common.data.remote.Response;
import uk.co.intelitrack.intelizzz.common.data.remote.Track;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class MapsPresenter implements MapsContract.Presenter, DatePickerDialog.OnDateSetListener, FloatingSearchView.OnSearchListener, FloatingSearchView.OnQueryChangeListener {

    private final static int FIRST_CALENDAR = 1;
    private final static int SECOND_CALENDAR = 2;
    private final static int CUSTOM_CALENDAR = 3;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private final SimpleDateFormat sdf_days = new SimpleDateFormat("dd MM YY");
//    private final SimpleDateFormat sdf_month = new SimpleDateFormat("");
    private final SimpleDateFormat sdf_days = new SimpleDateFormat("dd");
    private final SimpleDateFormat sdf_month = new SimpleDateFormat("MMM");
    private final SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");

    //region Fields
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private IntelizzzRepository mRepository;
    private MapsContract.View mView;
    private Vehicle mVehicle;
    private boolean mIsLastKnownLocation;
    private Calendar firstCalendar;
    private Calendar secondCalendar;
    private Calendar customCalendar;
    private int mDialog;
    //endregion

    private List<SuggestionsItem> mAllSuggestionsItems;

    public MapsPresenter(IntelizzzRepository repository, MapsContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }

    //region BasePresenter
    @Override
    public void subscribe(Intent intent) {
        String id = intent.getStringExtra(Constants.ID);
        mView.setListeners();



        if (!TextUtils.isEmpty(id)) {
            mVehicle = mRepository.getVehicleById(id);
        }
        mIsLastKnownLocation = intent.getBooleanExtra(Constants.IS_LAST_KNOWN_LOCATION, false);

        firstCalendar = Calendar.getInstance();
//        mView.showFirstCalendarDate(sdf_days.format(firstCalendar.getTime()), sdf_month.format(firstCalendar.getTime()), mIsLastKnownLocation);
        mView.showFirstCalendarDate(sdf_days.format(firstCalendar.getTime()), sdf_month.format(firstCalendar.getTime()), sdf_year.format(firstCalendar.getTime()), mIsLastKnownLocation);
        //mView.showThirdCalendarDate(sdf_days.format(firstCalendar.getTime()), sdf_month.format(firstCalendar.getTime()));
        fetchLastKnownLocation();
        if (mIsLastKnownLocation) {
            mView.showLastKnownLocationComponents();
        } else {
            secondCalendar = Calendar.getInstance();
            secondCalendar.add(Calendar.DATE, -7);
//            mView.showSecondCalendarDate(sdf_days.format(secondCalendar.getTime()), sdf_month.format(secondCalendar.getTime()));
              mView.showSecondCalendarDate(sdf_days.format(secondCalendar.getTime()), sdf_month.format(secondCalendar.getTime()), sdf_year.format(secondCalendar.getTime()));
            mView.showLocationsComponents();
            customCalendar = Calendar.getInstance();
            fetchLocations();



        }





    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }

    @Override
    public void onCalendarFirstClick() {
        if (!mIsLastKnownLocation) {
            mDialog = FIRST_CALENDAR;
            mView.showDatePicker(firstCalendar, this);
        }
    }

    @Override
    public void onCalendarSecondClick() {
        mDialog = SECOND_CALENDAR;
        mView.showDatePicker(secondCalendar, this);
    }

    @Override
    public void onCalendarCustomClick() {
        mDialog = CUSTOM_CALENDAR;
        mView.showDatePicker(firstCalendar, this);
    }

    @Override
    public void onNextClick() {
        fetchLocations();
    }

//    @Override
//    public void onNextArrowClick() {
//
//    }
    //endregion

    //region interface OnDateSetListener
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (mDialog == FIRST_CALENDAR) {
            firstCalendar.set(year, month, dayOfMonth);
//            mView.showFirstCalendarDate(sdf_days.format(firstCalendar.getTime()), sdf_month.format(firstCalendar.getTime()), mIsLastKnownLocation);
            mView.showFirstCalendarDate(sdf_days.format(firstCalendar.getTime()), sdf_month.format(firstCalendar.getTime()), sdf_year.format(firstCalendar.getTime()), mIsLastKnownLocation);
        } else if (mDialog == SECOND_CALENDAR) {
            secondCalendar.set(year, month, dayOfMonth);
//            mView.showSecondCalendarDate(sdf_days.format(secondCalendar.getTime()), sdf_month.format(secondCalendar.getTime()));
            mView.showSecondCalendarDate(sdf_days.format(secondCalendar.getTime()), sdf_month.format(secondCalendar.getTime()), sdf_year.format(secondCalendar.getTime()));
        } else {
            customCalendar.set(year, month, dayOfMonth);
            mView.showCustomCalendarDate(sdf_days.format(customCalendar.getTime()), sdf_month.format(customCalendar.getTime()), sdf_year.format(customCalendar.getTime()));
        }
    }
    //endregion

    //region Helpers Methods
    private void fetchLastKnownLocation() {
        mView.toogleProgressBar(true);
        if (mVehicle.getDl() != null && !TextUtils.isEmpty(mVehicle.getDevice().getId())) {
            mSubscriptions.add(
                    mRepository.getDeviceStatus(mVehicle.getDevice().getId())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(
                                    (Response x) -> {
                                        if (x.getResult().equals("0")) {
                                            Timber.d("getUnits successful");
                                            if (x.getStatuses() != null && x.getStatuses().length != 0) {
                                                if (mIsLastKnownLocation) {
                                                    double lat = Double.valueOf(x.getStatuses()[0].getlat());
                                                    double lng = Double.valueOf(x.getStatuses()[0].getlng());
                                                    LatLng latLng = new LatLng(lat, lng);
                                                    mView.addMarkerOnMap(latLng, R.drawable.map_car_pin_green, "title", "snipet");
                                                    String address = getAddress(lat, lng);
                                                    if (!TextUtils.isEmpty(address)) {
                                                        mView.showAddress(address);
                                                    }
                                                } else {
                                                    Date startDate;
                                                    try {
                                                        startDate = sdf.parse(x.getStatuses()[0].getDate());
                                                        String days = sdf_days.format(startDate);
//                                                        mView.showFirstCalendarDate(days, sdf_month.format(startDate), mIsLastKnownLocation);
                                                        mView.showFirstCalendarDate(days, sdf_month.format(startDate), sdf_year.format(startDate), mIsLastKnownLocation);
                                                    } catch (NullPointerException | ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
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
    }

    private void fetchLocations() {
        mView.toogleProgressBar(true);
        if (mVehicle.getDl() != null && !TextUtils.isEmpty(mVehicle.getDevice().getId())) {
            mSubscriptions.add(
                    mRepository.getVehicleTravelPaths(mVehicle.getDevice().getId(), sdf.format(secondCalendar.getTime()),
                            sdf.format(customCalendar.getTime()), "200")
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(x -> {
                                        if (x.getResult().equals("0")) {
                                            Timber.d("getUnits successful");
                                            showTracks(x);
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
    }

    private void showTracks(Response response) {
        List<LatLng> results = new ArrayList<>();
        if (response.getTracks() == null || response.getTracks().length == 0) {
            return;
        }
        for (Track track : response.getTracks()) {
            if (track.getlat() != null && track.getlng() != null) {
                results.add(new LatLng(Double.parseDouble(track.getlat()), Double.parseDouble(track.getlng())));

            }
        }

        if (!results.isEmpty()) {
            if (results.size() == 1) {
                mView.addMarkerOnMap(results.get(0), R.drawable.map_car_pin_green, "title", "snipet");
            } else {
                mView.drawPathOnMapFromLatLngArray(results, R.color.colorAccent, 10);
            }
            String address = getAddress(results.get(0).latitude, results.get(0).longitude);
            if (!TextUtils.isEmpty(address)) {
                mView.showAddress(address);
            }
        }
    }

    private String getAddress(double lat, double lng) {
        String result = "";
        Geocoder geocoder = new Geocoder(mView.getContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);
            //String country = addresses.get(0).getAddressLine(two);
            result = address + ", " + city;
        } catch (NullPointerException | IOException e) {

            e.printStackTrace();
        }
        return result;
    }
    //endregion
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
            mView.startMapsActivity(id);
        }
    }

    @Override
    public void onSearchAction(String currentQuery) {
        mView.getSearchView().showProgress();
        mView.getSearchView().swapSuggestions(filter(currentQuery));
        mView.getSearchView().hideProgress();
    }
}
