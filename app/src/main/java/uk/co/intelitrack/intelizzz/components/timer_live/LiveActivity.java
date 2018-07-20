package uk.co.intelitrack.intelizzz.components.timer_live;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.TimerAlarm;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.utils.ViewsUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;



/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class LiveActivity extends FragmentActivity implements OnMapReadyCallback, LiveContract.View {

    @BindView(R.id.txtTimerValue)
    TextView mTimerValue;

    @BindView(R.id.text_location_address)
    TextView mAddress;

    @BindView(R.id.root_last_location2)
    LinearLayout  mRootLastLocation;
//    @BindView(R.id.root_custom_locations)
//    RelativeLayout mRootCustomLocations;

    @Inject
    LivePresenter mPresenter;
    private GoogleMap mMap;
    private IntelizzzProgressDialog mProgresDialog;


    public static void start(Activity activity, String id, boolean isLastKnownLocation) {
        Intent intent = new Intent(activity, LiveActivity.class);
        intent.putExtra(Constants.ID, id);
        intent.putExtra(Constants.IS_LAST_KNOWN_LOCATION, isLastKnownLocation);
        activity.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLiveComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())


                .livePresenterModule(new LivePresenterModule(this))
                .liveModule(new LiveModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.live_timer);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPresenter.subscribe(getIntent());
        String id = getIntent().getStringExtra(Constants.ID);


        setTimer(id, true);
    }

    private void setTimer(String id, boolean isFirstTime) {
        Calendar current = Calendar.getInstance();
        Calendar tillAlarm = Calendar.getInstance();
        if (current.get(Calendar.HOUR_OF_DAY) == 8 && current.get(Calendar.MINUTE) <= 59) {
            tillAlarm.set(Calendar.HOUR_OF_DAY, 9);
            mTimerValue.setTextColor(getResources().getColor(R.color.red));
            isFirstTime = false;
        } else {
            mTimerValue.setTextColor(getResources().getColor(R.color.text_black));
            if (current.get(Calendar.HOUR_OF_DAY) > 1) {
                tillAlarm.add(Calendar.DAY_OF_MONTH, 1);
            }
            tillAlarm.set(Calendar.HOUR_OF_DAY, -6);
        }

        tillAlarm.set(Calendar.MINUTE, 0);
        tillAlarm.set(Calendar.SECOND, 0);
        tillAlarm.set(Calendar.MILLISECOND, 0);

        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this);
        if (!TextUtils.isEmpty(id)) {
            sharedPreferencesUtils.addTimer(new TimerAlarm(id, tillAlarm.getTimeInMillis()));
        }

        long milliseconds = tillAlarm.getTimeInMillis() - current.getTimeInMillis();

        CountDownTimer countDownTimer = new LiveActivity.MyTimer(milliseconds, 1000, isFirstTime, id);

        countDownTimer.start();
    }
    private class MyTimer extends CountDownTimer {

        private boolean isFirstTime;
        private String id;

        public MyTimer(long millisInFuture, long countDownInterval, boolean isFirstTime, String id) {
            super(millisInFuture, countDownInterval);
            this.isFirstTime = isFirstTime;
            this.id = id;
        }

        @Override
        public void onTick(long millis) {
            String timesString = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            mTimerValue.setText(timesString);
        }

        @Override
        public void onFinish() {
            String timesString = "00:00:00";
            mTimerValue.setText(timesString);
            if (isFirstTime) {
                setTimer(id, false);
            } else {
                finish();
            }
        }
    }

    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showAddress(String address) {
        mAddress.setText(address);

    }

    @Override
    public void addMarkerOnMap(LatLng latLng, int markerIcon, String title, String snippet) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(markerIcon))
                .title(title)
                .snippet(snippet));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));

    }

    @Override
    public void drawPathOnMapFromLatLngArray(List<LatLng> latLngs, int color, float width) {
        mMap.addPolyline(new PolylineOptions()
                .addAll(latLngs)
                .color(color)
                .width(width)
                .startCap(new RoundCap())
                .endCap(new RoundCap()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 15.0f));

    }

    @Override
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();

    }

    @Override
    public void showLastKnownLocationComponents() {
        ViewsUtils.setViewGroupVisibility(mRootLastLocation, View.VISIBLE);
//        ViewsUtils.setViewGroupVisibility(mRootCustomLocations, View.GONE);

    }

    @Override
    public void showLocationsComponents() {
        ViewsUtils.setViewGroupVisibility(mRootLastLocation, View.GONE);
//        ViewsUtils.setViewGroupVisibility(mRootCustomLocations, View.VISIBLE);

    }

    @Override
    public void showDatePicker(Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void showFirstCalendarDate(String date, String month, String year, boolean isLastLocation) {

    }

    @Override
    public void showSecondCalendarDate(String date, String month, String year) {

    }

    @Override
    public void showCustomCalendarDate(String date, String month, String year) {

    }

    @Override
    public void setPresenter(LiveContract.Presenter presenter) {

    }

    @Override
    public void toogleProgressBar(boolean show) {
        if (show) {
            if (mProgresDialog == null) {
                mProgresDialog = DialogUtils.getProgressBarDialog(this);
            }
            mProgresDialog.show();
        } else {
            if (mProgresDialog != null) {
                mProgresDialog.dismiss();
            }
        }

    }


}
