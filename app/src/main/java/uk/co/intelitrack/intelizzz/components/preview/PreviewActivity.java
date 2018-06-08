package uk.co.intelitrack.intelizzz.components.preview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.groups.GroupsActivity;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;
import uk.co.intelitrack.intelizzz.components.unit.UnitActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class PreviewActivity extends AppCompatActivity implements PreviewContract.View, VehiclesClickListener,
        FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener {

    //region DI
    @Inject
    PreviewPresenter mPresenter;
    @Inject
    VehiclesAdapter mVehiclesAdapter;
    @Inject
    GroupsAdapter mGroupsAdapter;
    @Inject
    IntelizzzRepository mRepository;
    //endregion

    //region VI
    @BindView(R.id.rvVehicles)
    RecyclerView mRvVehicles;
    @BindView(R.id.rvGroups)
    RecyclerView mRvGroups;
    @BindView(R.id.floating_search_view)
    IntelizzzFloatingSearchView mIntelizzzFloatingSearchView;
    @BindView(R.id.bottom_bar_type_name)
    TextView bottomBarTypeName;
    @BindView(R.id.bottom_bar_type_number)
    TextView bottomBarTypeNumber;
    @BindView(R.id.btn_move)
    ImageView mBtnMove;
    @BindView(R.id.add_unit)
    ImageView add_unit;
    @BindView(R.id.btn_delete)
    ImageView mBtnDelete;
    @BindView(R.id.toolbar_type_btn)
    ImageView mToolBarType;
    //endregion

    //region Fields
    private IntelizzzProgressDialog mProgressDialog;
    private boolean mIsGroup;
    //endregion

    public static void start(Activity activity, boolean isGroup) {
        Intent intent = new Intent(activity, PreviewActivity.class);
        intent.putExtra(Constants.IS_GROUP, isGroup);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPreviewComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .previewPresenterModule(new PreviewPresenterModule(this))
                .previewModule(new PreviewModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_vehicles);

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null)
            mIsGroup = getIntent().getExtras().getBoolean(Constants.IS_GROUP);

        mPresenter.subscribe(getIntent());

        mIntelizzzFloatingSearchView.setOnSearchListener(this);
        mIntelizzzFloatingSearchView.setOnQueryChangeListener(this);

        mBtnMove.setVisibility(mIsGroup ? View.VISIBLE : View.GONE);
        mBtnDelete.setVisibility(mIsGroup ? View.VISIBLE : View.GONE);
        if (mIsGroup) {
            mToolBarType.setImageResource(R.drawable.groups);
        }
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

    //region BaseView Methods
    @Override
    public void setPresenter(PreviewContract.Presenter presenter) {

    }

    @Override
    public void toogleProgressBar(boolean show) {
        hideProgressBar();
        if (show) {
            mProgressDialog = DialogUtils.getProgressBarDialog(this);
            mProgressDialog.show();
        }
    }
    //endregion

    //region VehiclesView Methods
    @Override
    public void setVehicles(List<Vehicle> vehicles) {
        if (vehicles.size() == 1) {
            bottomBarTypeName.setText(R.string.label_unit);
        } else {
            bottomBarTypeName.setText(R.string.label_units);
        }
        bottomBarTypeNumber.setText(String.valueOf(vehicles.size()));
        mBtnMove.setVisibility(View.GONE);
    }

    @Override
    public void setGroupNumber(int number) {
        if (number == 1) {
            bottomBarTypeName.setText(R.string.label_group);
        } else {
            bottomBarTypeName.setText(R.string.label_groups);
        }
        bottomBarTypeNumber.setText(String.valueOf(number));
        mBtnMove.setVisibility(View.VISIBLE);
        add_unit.setVisibility(View.GONE);
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
        mRvVehicles.setAdapter(mVehiclesAdapter);
        mRvVehicles.setVisibility(View.VISIBLE);
        mRvGroups.setVisibility(View.GONE);
    }

    @Override
    public void setGroupsListVisible() {
        mRvGroups.setAdapter(mGroupsAdapter);
        mRvGroups.setVisibility(View.VISIBLE);
        mRvVehicles.setVisibility(View.GONE);
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
    //endregion

    //region FloatingSearchView Methods
    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (mIsGroup) {
            mGroupsAdapter.filter(newQuery.toLowerCase());
        } else {
            mVehiclesAdapter.filter(newQuery.toLowerCase());
        }
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {
        if (mIsGroup) {
            mGroupsAdapter.filter(currentQuery.toLowerCase());
        } else {
            mVehiclesAdapter.filter(currentQuery.toLowerCase());
        }
    }
    //endregion

    //region ButterKnife Methods
    @OnClick(R.id.btn_home)
    void onHomeClick() {
        MainActivity.start(this);
        finish();
    }

    @OnClick(R.id.btn_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.heading_unit_id)
    void onUnitIdSortByClick() {
        if (mIsGroup) {
            mGroupsAdapter.sortByName();
        } else {
            mVehiclesAdapter.sortByName();
        }
    }

    @OnClick(R.id.heading_warning)
    void onUnitIdSortByWarning() {
        if (mIsGroup) {
            mGroupsAdapter.sortByWarning();
        } else {
            mVehiclesAdapter.sortByWarning();
        }
    }

    @OnClick(R.id.heading_number)
    void onUnitNumberSortByClick() {
        if (mIsGroup) {
            mGroupsAdapter.sortByNumber();
        } else {
            mVehiclesAdapter.sortByNumber();
        }
    }

    @OnClick({R.id.heading_7, R.id.heading_6, R.id.heading_5, R.id.heading_4, R.id.heading_3, R.id.heading_2, R.id.heading_1})
    void onUnitDaysSortByClick() {
        if (mIsGroup) {
            mGroupsAdapter.sortByDays();
        } else {
            mVehiclesAdapter.sortByDays();
        }
    }

    @OnClick(R.id.heading_gsimbol)
    void onUnitGeoSortByClick() {
        if (mIsGroup) {
            mGroupsAdapter.sortByGeo();
        } else {
            mVehiclesAdapter.sortByGeo();
        }
    }

    @OnClick(R.id.btn_move)
    void onMoveClick() {
        mPresenter.onMoveClick();
    }

    @OnClick(R.id.btn_delete)
    void onDeleteClick() {
        mPresenter.onDeleteClick();
    }
    //endregion

    //region Helper Methods
    private void hideProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    //endregion
}
