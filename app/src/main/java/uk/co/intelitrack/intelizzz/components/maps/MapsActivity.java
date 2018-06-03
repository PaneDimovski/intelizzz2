package uk.co.intelitrack.intelizzz.components.maps;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.ViewsUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsContract.View {

    //region DI
    @Inject
    MapsPresenter mPresenter;
    //endregion

    //region VI
    @BindView(R.id.root_last_location)
    RelativeLayout mRootLastLocation;
    @BindView(R.id.root_custom_locations)
    RelativeLayout mRootCustomLocations;
    @BindView(R.id.tv_day)
    TextView mFirstCalendarDay;
    @BindView(R.id.tv_month)
    TextView mFirstCalendarMonth;
    @BindView(R.id.tv_custom_day)
    TextView mFirstCustomCalendarDay;
    @BindView(R.id.tv_custom_month)
    TextView mFirstCustomCalendarMonth;
    @BindView(R.id.tv_second_day)
    TextView mSecondCalendarDay;
    @BindView(R.id.tv_second_month)
    TextView mSecondCalendarMonth;
    @BindView(R.id.tv_third_day)
    TextView mThirdCalendarDay;
    @BindView(R.id.tv_third_month)
    TextView mThirdCalendarMonth;
    @BindView(R.id.text_location_address)
    TextView mAddress;
    //endregion

    //region fields
    private GoogleMap mMap;
    private IntelizzzProgressDialog mProgresDialog;
    //endregion

    public static void start(Activity activity, String id, boolean isLastKnownLocation) {
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtra(Constants.ID, id);
        intent.putExtra(Constants.IS_LAST_KNOWN_LOCATION, isLastKnownLocation);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMapsComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .mapsPresenterModule(new MapsPresenterModule(this))
                .mapsModule(new MapsModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPresenter.subscribe(getIntent());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void setPresenter(MapsContract.Presenter presenter) {

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
        ViewsUtils.setViewGroupVisibility(mRootCustomLocations, View.GONE);
    }

    @Override
    public void showLocationsComponents() {
        ViewsUtils.setViewGroupVisibility(mRootLastLocation, View.GONE);
        ViewsUtils.setViewGroupVisibility(mRootCustomLocations, View.VISIBLE);

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
    public void showFirstCalendarDate(String date, String month, boolean isLastLocation) {
        if (isLastLocation) {
            mFirstCalendarDay.setText(date);
            mFirstCalendarMonth.setText(month);
        } else {
            mFirstCustomCalendarDay.setText(date);
            mFirstCustomCalendarMonth.setText(month);
        }
    }

    @Override
    public void showSecondCalendarDate(String date, String month) {
        mSecondCalendarDay.setText(date);
        mSecondCalendarMonth.setText(month);
    }

    @Override
    public void showThirdCalendarDate(String date, String month) {
        mThirdCalendarDay.setText(date);
        mThirdCalendarMonth.setText(month);
    }

    @OnClick(R.id.btn_home)
    void onHomeClick() {
        MainActivity.start(this);
        finish();
    }

    @OnClick({R.id.btn_back, R.id.btn_back_custom})
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.rl_calendar_parent)
    void onCalendarFirstClick() {
        mPresenter.onCalendarFirstClick();
    }

    @OnClick(R.id.rl_second_calendar_parent)
    void onCalendarSecondClick() {
        mPresenter.onCalendarSecondClick();
    }

    @OnClick(R.id.btn_next)
    void onNextClick() {
        mPresenter.onNextClick();
    }

    @OnClick(R.id.btn_arrow_next)
    void onArrowClick() {
        mPresenter.onNextArrowClick();
    }
}
