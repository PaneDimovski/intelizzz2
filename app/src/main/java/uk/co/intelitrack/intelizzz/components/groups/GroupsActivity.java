package uk.co.intelitrack.intelizzz.components.groups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.crud_groups.CrudGroupActivity;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class GroupsActivity extends AppCompatActivity implements GroupsContract.View {

    @Inject
    GroupsPresenter mPresenter;
    @Inject
    VehiclesDataAdapter mVehiclesDataAdapter;
    @Inject
    UnassignedVehiclessDataAdapter mUnassignedVehiclesDataAdapter;

    @BindView(R.id.leftGroups)
    RecyclerView mLeftGroups;
    @BindView(R.id.rightGroups)
    RecyclerView mRightGroups;
    @BindView(R.id.floating_search_view)
    IntelizzzFloatingSearchView mIntelizzzFloatingSearchView;
    @BindView(R.id.bottom_bar_type_number)
    TextView mUnitsNumber;
    @BindView(R.id.btn_add)
    ImageView mBtnAdd;
    @BindView(R.id.toolbar_type_btn)
    ImageView mToolBarType;
    //region fields
    private IntelizzzProgressDialog mProgresDialog;
    private boolean mIsGroup;
    //endregion

    public static void start(Activity activity, boolean isGroup) {
        Intent intent = new Intent(activity, GroupsActivity.class);
        intent.putExtra(Constants.IS_GROUP, isGroup);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerGroupsComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .groupsPresenterModule(new GroupsPresenterModule(this))
                .groupsModule(new GroupsModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_groups);

        ButterKnife.bind(this);
        if (getIntent().getExtras() != null)
            mIsGroup = getIntent().getExtras().getBoolean(Constants.IS_GROUP);
        mPresenter.subscribe(getIntent());
        showAddButtonAndAddListeners();

        if (mIsGroup) {
            mToolBarType.setImageResource(R.drawable.groups);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(GroupsContract.Presenter presenter) {

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
    public void initGroupAdapters() {
        mLeftGroups.setAdapter(mVehiclesDataAdapter);
        mRightGroups.setAdapter(mUnassignedVehiclesDataAdapter);
    }

    @Override
    public void setUnitNumber(String number) {
        mUnitsNumber.setText(number);
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

    @Override
    public IntelizzzFloatingSearchView getSearchView() {
        return mIntelizzzFloatingSearchView;
    }

    @Override
    public void showGroupData(List<Vehicle> vehicles) {
        mVehiclesDataAdapter.setData(vehicles);
    }

    @Override
    public void setCompany(String companyId) {
        mUnassignedVehiclesDataAdapter.setCompanyId(companyId);
    }

    @Override
    public void refreshUnassignedVehicles() {
        mUnassignedVehiclesDataAdapter.refreshUnassignedVehicles();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_home)
    void onHomeClick() {
        MainActivity.start(this);
        finish();
    }

    @OnClick(R.id.btn_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_add)
    void onAddClick() {
        CrudGroupActivity.start(this);
    }

    @OnClick(R.id.arrowRight)
    void onArrowRightClicked() {
        mPresenter.onArrowRightClicked(mVehiclesDataAdapter.getSelectedVehicles());
    }

    @OnClick(R.id.arrowLeft)
    void onArrowLeftClicked() {
        mPresenter.onArrowLeftClicked(mUnassignedVehiclesDataAdapter.getUnassignedVehicless());
    }

    //region Helpers Methods
    private void showAddButtonAndAddListeners() {
        mIntelizzzFloatingSearchView.setOnSearchListener(mPresenter);
        mIntelizzzFloatingSearchView.setOnQueryChangeListener(mPresenter);
        mBtnAdd.setVisibility(View.VISIBLE);
    }
    //endregion
}
