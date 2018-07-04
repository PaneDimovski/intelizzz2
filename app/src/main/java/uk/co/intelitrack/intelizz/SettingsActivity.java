package uk.co.intelitrack.intelizz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import io.reactivex.annotations.Nullable;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.groups.GroupsActivity;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;
import uk.co.intelitrack.intelizzz.components.preview.GroupsAdapter;
import uk.co.intelitrack.intelizzz.components.preview.PreviewActivity;
import uk.co.intelitrack.intelizzz.components.preview.VehiclesAdapter;
import uk.co.intelitrack.intelizzz.components.preview.VehiclesClickListener;
import uk.co.intelitrack.intelizzz.components.unit.UnitActivity;

public class SettingsActivity extends AppCompatActivity implements SettingsActivityInterface, SettingsContract.View, VehiclesClickListener {

    @Inject
    SettingsPresenter mPresenter;
    @BindView(R.id.btn_ok)
    ImageView kopceNext;

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
//        if (number == 1) {
//            bottomBarTypeName.setText(R.string.label_group);
//        } else {
//            bottomBarTypeName.setText(R.string.label_groups);
//        }
//        bottomBarTypeNumber.setText(String.valueOf(number));
//        mBtnMove.setVisibility(View.GONE);
//        delete2.setVisibility(View.GONE);
//        delete.setVisibility(View.GONE);
//        mBtnDelete.setVisibility(View.GONE);
//        add_unit.setVisibility(View.GONE);
    }

    @Override
    public void setGroups(List<ParentVehicle> groups) {
        if (mGroupsAdapter.isGroupExpanded(0)) {
            mGroupsAdapter.toggleGroup(0);
        }
        mGroupsAdapter.setData(groups, false);
    }

    @Override
    public void setGroup(ParentVehicle group) {
        List<ParentVehicle> groups = new ArrayList<>();
        groups.add(group);
        mGroupsAdapter.setData(groups, true);
        mGroupsAdapter.toggleGroup(0);
    }

    @Override
    public void setVehiclesListVisible() {
//        rvVehivles.setAdapter(mVehiclesAdapter);
//        rvVehivles.setVisibility(View.VISIBLE);
//        rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGroupsListVisible() {
        rv.setAdapter(mGroupsAdapter);
        rv.setVisibility(View.VISIBLE);
//        rvVehivles.setVisibility(View.GONE);
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
        MainActivity.start(this);
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


//    public void Pocni_Adapter(){
//        adapter = new GroupsAdapter(vehicles, mRespository, new GroupsClickListener() {
//            @Override
//            public void onCompanyItemClick(String id) {
//
//            }
//
//            @Override
//            public void onGroupItemClick(String groupId) {
//
//            }
//
//            @Override
//            public void onGroupChildItemClick(String vehicleId) {
//
//            }
//        },true);
//
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(adapter);
//    }


    private void hideProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
