package uk.co.intelitrack.intelizzz.components.maps;

import android.app.DatePickerDialog;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface MapsContract {

    interface View extends BaseView<MapsContract.Presenter> {
        Context getContext();

        void showAddress(String address);

        void addMarkerOnMap(LatLng latLng, int markerIcon, String title, String snippet);

        void drawPathOnMapFromLatLngArray(List<LatLng> latLngs, int color, float width);

        void startLoginActivity();

        void showLastKnownLocationComponents();

        void showLocationsComponents();

        void showDatePicker(Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener);

        void showFirstCalendarDate(String date, String month, boolean isLastLocation);

        void showSecondCalendarDate(String date, String month);

        void showThirdCalendarDate(String date, String month);
    }

    interface Presenter extends BasePresenter {

        void onCalendarFirstClick();

        void onCalendarSecondClick();

        void onNextClick();

        void onNextArrowClick();
    }
}
