package uk.co.intelitrack.intelizzz.components.timer_live;

import android.app.DatePickerDialog;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface LiveContract {

    interface View extends BaseView<LiveContract.Presenter> {
        void showData(Vehicle vehicle);

        Context getContext();

        void showAddress(String address);

        void addMarkerOnMap(LatLng latLng, int markerIcon, String title, String snippet);

        void drawPathOnMapFromLatLngArray(List<LatLng> latLngs, int color, float width);

        void startLoginActivity();

        void showLastKnownLocationComponents();

        void showLocationsComponents();

        void showDatePicker(Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener);

        //void showFirstCalendarDate(String date, String month, boolean isLastLocation);

        void showFirstCalendarDate(String date, String month, String year, boolean isLastLocation);

        //void showSecondCalendarDate(String date, String month);

        void showSecondCalendarDate(String date, String month, String year);

        //void showThirdCalendarDate(String date, String month);

        void showCustomCalendarDate(String date, String month, String year);
    }

    interface Presenter extends BasePresenter {

        void onCalendarFirstClick();

        void onCalendarSecondClick();

        void onCalendarCustomClick();

        void onNextClick();

//        void onNextArrowClick();
    }
}
