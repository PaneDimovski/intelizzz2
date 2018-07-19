package uk.co.intelitrack.intelizz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.api.RestApi;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Device;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreff;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.groups.GroupsActivity;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;
import uk.co.intelitrack.intelizzz.components.preview.PreviewActivity;
import uk.co.intelitrack.intelizzz.components.preview.VehiclesAdapter;
import uk.co.intelitrack.intelizzz.components.preview.VehiclesClickListener;
import uk.co.intelitrack.intelizzz.components.unit.UnitActivity;

/**
 * Created by Oliver :-).
 */
public class SettingsActivity extends AppCompatActivity implements SettingsActivityInterface, SettingsContract.View, VehiclesClickListener {

    @Inject
    SettingsPresenter mPresenter;
    //    @BindView(R.id.btn_ok)
//    ImageView kopceNext;
    @BindView(R.id.btn_ok_settings)
    ImageView kopceBrisi;

    @BindView(R.id.rvSelect)
    RecyclerView rv;

    private boolean mIsGroup;
    @Nullable
    @BindView(R.id.bottom_bar_type_name)
    public TextView bottomBarTypeName;
    @Nullable
    @BindView(R.id.bottom_bar_type_number)
    TextView bottomBarTypeNumber;
    @Nullable
    @BindView(R.id.btn_move)
    ImageView mBtnMove;
    @Nullable
    @BindView(R.id.add_unit)
    ImageView add_unit;
    @Nullable
    @BindView(R.id.btn_delete)
    ImageView mBtnDelete;


    @Nullable
    @BindView(R.id.toolbar_type_btn)
    ImageView mToolBarType;
    private IntelizzzProgressDialog mProgressDialog;
    @Inject
    SettingsGroupsAdapter mGroupsAdapter;
    List<ParentVehicle> vehicles = new ArrayList<>();
    IntelizzzRepository mRespository;
    @Inject
    VehiclesAdapter mVehiclesAdapter;
    @Inject
    ParentVehicle parentVehicle;
    @Inject
    SharedPreferencesUtils mSharedPreferencesUtils;
    RestApi api;
    String kompanija = "2";
    String pane = "24";


    public static void start(Activity activity, boolean isGroup) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        intent.putExtra(Constants.IS_GROUP, isGroup);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        settingsComponent = DaggerCrudGroupComponent
        DaggerSettingsComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .settingsPresenterModule(new SettingsPresenterModule(this))
                .settingsModule(new SettingsModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_settings_select);


        ButterKnife.bind(this);
        if (getIntent().getExtras() != null)
            mIsGroup = getIntent().getExtras().getBoolean(Constants.IS_GROUP);
        mPresenter.subscribe(getIntent());

        //   mBtnDelete.setVisibility(mIsGroup ? View.VISIBLE : View.GONE);

//        if (mIsGroup) {
//            mToolBarType.setImageResource(R.drawable.groups);
//        }


//        LinearLayout linear1 =(LinearLayout)findViewById(R.id.linear1);
//        LinearLayout linear2 =(LinearLayout)findViewById(R.id.linear2);
//        LinearLayout linear3 =(LinearLayout)findViewById(R.id.linear3);
//        LinearLayout linear4 =(LinearLayout)findViewById(R.id.linear4);
//
//        final int sdk = android.os.Build.VERSION.SDK_INT;
//        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            linear1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
//        } else {
//            linear1.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
//        }
//        kopceNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setContentView(R.layout.activity_vehicles);
//
//
////                mBtnMove.setVisibility(mIsGroup ? View.VISIBLE : View.GONE);
////                onGroupsClick();
//                setGroup(parentVehicle);
//                setGroups(vehicles);
//                setGroupsListVisible();
//            }
//        });

        //   Ova e za da se menja pozadinata na odbereniot layout , koga ke se odbere kopceto eden, t.e. prviot timer

    }

    void onGroupsClick() {
        PreviewActivity.start(this, true);
    }


//    @OnClick(R.id.btn_ok_settings)
//    public void klik() {
//        String prv = "08:00";
//        String vtor = "12:00";
//        String tret = "15:00";
//        String cetri = "22:00";
//        ArrayList<String> alarmi = new ArrayList<>();
//        alarmi.add(prv);
//        alarmi.add(vtor);
//        alarmi.add(tret);
//        alarmi.add(cetri);
//        String deviceId = "15";
//
//        api = new RestApi(getApplicationContext());
//        Call<Device> call = api.setupAlarm(deviceId,alarmi);
//        call.enqueue(new Callback<Device>() {
//            @Override
//            public void onResponse(Call<Device> call, Response<Device> response) {
//                if (response.isSuccessful()){
//                    Toast.makeText(SettingsActivity.this, "USPESNO", Toast.LENGTH_SHORT).show();
//                } else if (!response.isSuccessful()){
//                    Toast.makeText(SettingsActivity.this, "MAJKATI U PICKA", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Device> call, Throwable t) {
//
//            }
//        });






//        OVA E ZA DELETE GROUP SHTO RABOTI !!!!
//        String groupId = mSharedPreferencesUtils.getSharedPreferencesString(Constants.OLI_ID);
//        String jsession = mSharedPreferencesUtils.getSharedPreferencesString(Constants.JSESSIONID);
//        mPresenter.onDelete6(jsession,groupId);
//        mGroupsAdapter.notifyDataSetChanged();



    @Override
    protected void onResume() {
        super.onResume();
        if (mIsGroup) {
            mPresenter.refreshGroups();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {

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
    public void setVehicles(List<Vehicle> vehicles) {
//        if (vehicles.size() == 1) {
//            bottomBarTypeName.setText(R.string.label_unit);
//        } else {
//            bottomBarTypeName.setText(R.string.label_units);
//        }
//        bottomBarTypeNumber.setText(String.valueOf(vehicles.size()));
//        mBtnMove.setVisibility(View.GONE);
//        delete2.setVisibility(View.GONE);
    }

    //    @Override
    public void setGroupNumber(int number) {
//
    }

    @Override
    public void setGroups(List<ParentVehicle> groups) {
        if (mGroupsAdapter.isGroupExpanded(0)) {
            mGroupsAdapter.toggleGroup(0);
        }
        mGroupsAdapter.setData(groups, false, false, false);
    }

    @Override
    public void setGroup(ParentVehicle group) {
        List<ParentVehicle> groups = new ArrayList<>();
        groups.add(group);
        mGroupsAdapter.setData(groups, true, true, true);
        mGroupsAdapter.toggleGroup(0);
    }

    @Override
    public void setVehiclesListVisible() {
    }

    @Override
    public void setGroupsListVisible() {
        rv.setAdapter(mGroupsAdapter);
        rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void startUnitActivity(String id) {
        UnitActivity.start(this, id);
    }

    @Override
    public void startGroupsActivity() {
        GroupsActivity.start(this, true);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

    @Override
    public void startMainActivity() {
        MainActivity.start(this,false);
        finish();
    }

    @Override
    public void onItemClick(String vehicleId) {
        mPresenter.onUnitClick(vehicleId);
    }

    @Override
    public void onItemClick2(String vehicleId) {
        mPresenter.onUnitClick2(vehicleId);
    }

    @Override
    public void onItemClick3(String vehicleId) {
        mPresenter.onUnitClick2(vehicleId);
    }



    private void hideProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
