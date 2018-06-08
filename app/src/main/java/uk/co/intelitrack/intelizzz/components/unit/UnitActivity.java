package uk.co.intelitrack.intelizzz.components.unit;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.broadcastreceivers.SmsDeliveredBroadcastReceiver;
import uk.co.intelitrack.intelizzz.common.broadcastreceivers.SmsSentBroadcastReceiver;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.TimerAlarm;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;
import uk.co.intelitrack.intelizzz.components.maps.MapsActivity;
import uk.co.intelitrack.intelizzz.components.timer.TimerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class UnitActivity extends AppCompatActivity implements UnitContract.View {

    //region static Fields
    private static final int SEND_SMS_PERMISSIONS_REQUEST = 1478;
    private static final String SMS_SENT = "SMS_SENT";
    private static final String SMS_DELIVERED = "SMS_DELIVERED";
    //endregion

    //region DI
    @Inject
    UnitPresenter mPresenter;
    @Inject
    SharedPreferencesUtils mSharedPreferencesUtils;
    //endregion

    //region VI
    @BindView(R.id.number_7)
    TextView day7;
    @BindView(R.id.number_6)
    TextView day6;
    @BindView(R.id.number_5)
    TextView day5;
    @BindView(R.id.number_4)
    TextView day4;
    @BindView(R.id.number_3)
    TextView day3;
    @BindView(R.id.number_2)
    TextView day2;
    @BindView(R.id.number_1)
    TextView day1;

    @BindView(R.id.item_unit_id)
    TextView mId;
    @BindView(R.id.item_number)
    TextView mNumber;
    @BindView(R.id.item_battery)
    ImageView mBattery;
    @BindView(R.id.item_gsimbol)
    ImageView mGsimbol;
    @BindView(R.id.icon_warning)
    ImageView mWarning;
    @BindView(R.id.btn_activate_tracker)
    ImageView mBtnActivateTracker;
    @BindView(R.id.floating_search_view)
    IntelizzzFloatingSearchView mSearchView;
    //endregion

    //region Fields
    private PendingIntent mSentPendingIntent;
    private PendingIntent mDeliveredPendingIntent;

    private SmsSentBroadcastReceiver mSmsSentBroadcastReceiver;
    private SmsDeliveredBroadcastReceiver mSmsDeliveredBroadcastReceiver;

    private IntelizzzProgressDialog mProgressDialog;

    private String mPhone;
    private String mVehicleId;
    //endregion

    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, UnitActivity.class);
        intent.putExtra(Constants.ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUnitComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .unitPresenterModule(new UnitPresenterModule(this))
                .unitModule(new UnitModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_unit);

        ButterKnife.bind(this);

        mPresenter.subscribe(getIntent());

        mSentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        mDeliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceivers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceivers();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(UnitContract.Presenter presenter) {

    }

    @Override
    public void toogleProgressBar(boolean show) {
        hideProgressBar();
        if (show) {
            mProgressDialog = DialogUtils.getProgressBarDialog(this);
            mProgressDialog.show();
        }
    }

    @Override
    public void showData(Vehicle vehicle) {
        if (vehicle.getDl() != null && !TextUtils.isEmpty(vehicle.getDevice().getSim())) {
            mPhone = vehicle.getDevice().getSim();
        } else {
            mPhone = vehicle.getPhone();
        }
        mVehicleId = vehicle.getId();
        showVehicle(vehicle);
    }

    @Override
    public void startMapActivity(String id, boolean isLastKnownLocation) {
        MapsActivity.start(this, id, isLastKnownLocation);
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

    @OnClick(R.id.btn_last_known_location)
    void onLastKnownLocationClicked() {
        mPresenter.onLastKnownLocation();
    }

    @OnClick(R.id.btn_previous_locations)
    void onPreviousLocationsClicked() {
        mPresenter.onPreviousLocations();
    }

//    @OnClick(R.id.btn_home)
//    void onHomeClick() {
//        MainActivity.start(this);
//        finish();
//    }

    @OnClick(R.id.btn_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_activate_tracker)
    public void onActivateTrackerClicked() {
        Calendar calendar = Calendar.getInstance();
        TimerAlarm alarm = mSharedPreferencesUtils.getTimer(mVehicleId);
        if (alarm != null) {
            if (alarm.getDate() < calendar.getTimeInMillis()) {
                mSharedPreferencesUtils.removeTimer(alarm.getId());
                alarm = null;
            }
        }
        if (alarm == null) {
            if (TextUtils.isEmpty(mPhone)) {
                Toast.makeText(this, "Phone error", Toast.LENGTH_LONG).show();
                return;
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        SEND_SMS_PERMISSIONS_REQUEST);
            } else {
                sendSms(mPhone, "ZZ,1,10,60#");
            }
        } else {
            startActivity(new Intent(this, TimerActivity.class));
        }
    }

    //region Helper Methods
    private void hideProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void sendSms(String phoneNumber, String smsBody) {

        SmsManager smsManager = SmsManager.getDefault();
        // Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, mSentPendingIntent, mDeliveredPendingIntent);
        Toast.makeText(this, "Tracker has been activated", Toast.LENGTH_LONG).show();
    }

    private void registerReceivers() {
        // For when the SMS has been sent
        mSmsSentBroadcastReceiver = new SmsSentBroadcastReceiver(mVehicleId);
        registerReceiver(mSmsSentBroadcastReceiver, new IntentFilter(SMS_SENT));

        // For when the SMS has been delivered
        mSmsDeliveredBroadcastReceiver = new SmsDeliveredBroadcastReceiver();
        registerReceiver(mSmsDeliveredBroadcastReceiver, new IntentFilter(SMS_DELIVERED));
    }

    private void unregisterReceivers() {
        if (mSmsSentBroadcastReceiver != null) {
            unregisterReceiver(mSmsSentBroadcastReceiver);
        }
        if (mSmsDeliveredBroadcastReceiver != null) {
            unregisterReceiver(mSmsDeliveredBroadcastReceiver);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSIONS_REQUEST) {
            if (permissions[0].compareTo(Manifest.permission.SEND_SMS) == 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (TextUtils.isEmpty(mPhone)) {
                    Toast.makeText(this, "Phone error", Toast.LENGTH_LONG).show();
                    return;
                }
                sendSms(mPhone, "ZZ,1,10,60#");
            }
        }
    }

    private void showVehicle(Vehicle vehicle) {
        mSearchView.setSearchText(vehicle.getNm());
        mNumber.setText(vehicle.getId());
        mId.setText(vehicle.getNm());

        if (vehicle.hasGeofenceAlarm()) {
            mGsimbol.setVisibility(View.INVISIBLE);
        } else {
            mGsimbol.setVisibility(View.VISIBLE);
        }

        if (vehicle.isWarning()) {
            mWarning.setVisibility(View.VISIBLE);
        } else {
            mWarning.setVisibility(View.INVISIBLE);
        }

        if (vehicle.getDays()[0]) {
            day1.setBackgroundResource(R.drawable.green_rectangle);
        }
        if (vehicle.getDays()[1]) {
            day2.setBackgroundResource(R.drawable.green_rectangle);
        }
        if (vehicle.getDays()[2]) {
            day3.setBackgroundResource(R.drawable.green_rectangle);
        }
        if (vehicle.getDays()[3]) {
            day4.setBackgroundResource(R.drawable.green_rectangle);
        }
        if (vehicle.getDays()[4]) {
            day5.setBackgroundResource(R.drawable.green_rectangle);
        }
        if (vehicle.getDays()[5]) {
            day6.setBackgroundResource(R.drawable.green_rectangle);
        }
        if (vehicle.getDays()[6]) {
            day7.setBackgroundResource(R.drawable.green_rectangle);
        }
    }
    //endregion
}
