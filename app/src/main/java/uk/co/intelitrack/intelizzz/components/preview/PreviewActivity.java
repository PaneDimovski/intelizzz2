package uk.co.intelitrack.intelizzz.components.preview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
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
import uk.co.intelitrack.Proba.ListViewItemCheckboxBaseAdapter;
import uk.co.intelitrack.Proba.ListViewItemDTO;
import uk.co.intelitrack.intelizz.SettingsActivity;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
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
//    @Inject
//    UnitAdapter unitsAdapter;

    @Inject
    GroupsAdapter mGroupsAdapter;
    @Inject
    IntelizzzRepository mRepository;
    //endregion

    //region VI
    @BindView(R.id.rvVehicles)
    RecyclerView mRvVehicles;
//    @Nullable
//    @BindView(R.id.delete_units)
//    RecyclerView mRVunits;
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
    @BindView(R.id.unit_delete)
    ImageView unit_delete;
    @BindView(R.id.group_delete)
    ImageView group_delete;
    @BindView(R.id.picSettings)
    ImageView setings;
    @BindView(R.id.toolbar_type_btn)
    ImageView mToolBarType;

    ListViewItemCheckboxBaseAdapter listViewDataAdapter;
    //endregion
    int posit;
    private List<Vehicle> mVehicles = new ArrayList<>();
    Vehicle vehicle;

    //region Fields
    private IntelizzzProgressDialog mProgressDialog;
    private boolean mIsGroup;
    //endregion
    // private ArrayAdapter<Vehicle> arrayAdapter;
    String text="";
    private List<Company> mCompanies = new ArrayList<>();




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
        //   mBtnDelete.setVisibility(mIsGroup ? View.VISIBLE : View.GONE);

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
        group_delete.setVisibility(View.GONE);
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
        group_delete.setVisibility(View.VISIBLE);
        unit_delete.setVisibility(View.GONE);
        mBtnDelete.setVisibility(View.GONE);
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


//    @Override
//    public void setUnitsList() {
//        mRVunits.setAdapter(unitsAdapter);
//        mRVunits.setVisibility(View.VISIBLE);
//        mRvGroups.setVisibility(View.GONE);
//    }

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

    @Override
    public void onItemClick2(String vehicleId) {
        mPresenter.onUnitClick2(vehicleId);
    }

    @Override
    public void onItemClick3(String vehicleId) {
        mPresenter.onUnitClick2(vehicleId);
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

    @OnClick(R.id.add_unit)
    void onAddClick(View v) {


        AlertDialog.Builder dialog2 = new AlertDialog.Builder(PreviewActivity.this);
        dialog2.setCancelable(true);


        dialog2.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog2.setView(getLayoutInflater().inflate(R.layout.alert_dialog_add_unit, null));
        AlertDialog alert2 = dialog2.create();
        alert2.show();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));


    }

    //    public List<Vehicle> getVehicles() {
//        return mVehicles;
//    }
    @OnClick(R.id.unit_delete)
    void deleteUint() {

        AlertDialog.Builder dialog3 = new AlertDialog.Builder(PreviewActivity.this);


        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.alert_dialog_delete_unit, null);

        dialog3.setView(convertView);

        ListView listView = (ListView)  convertView.findViewById(R.id.listView1);

        // Initiate listview data.
        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();

        // Create a customtwo list view adapter with checkbox control.
        ListViewItemCheckboxBaseAdapter listAdapter = new ListViewItemCheckboxBaseAdapter (getBaseContext() , initItemList);

        listAdapter.notifyDataSetChanged();

        convertView.setTag(dialog3);


        // When list view item is clicked.

         listView.setOnItemClickListener((adapterView, view, itemIndex, l) -> {
             // Get user selected item.
             Object itemObject = adapterView.getAdapter().getItem(itemIndex);

             // Translate the selected item to DTO object.
             ListViewItemDTO itemDto = (ListViewItemDTO) itemObject;

             // Get the checkbox.
             CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.checkMark5);

             // Reverse the checkbox and clicked item check state.
             if (itemDto.isChecked()) {
                 itemCheckbox.setChecked(false);
                 itemDto.setChecked(false);
             } else {
                 itemCheckbox.setChecked(true);
                 itemDto.setChecked(true);
             }

             //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
         });

        // Click this button to select all listview items with checkbox checked.
        ImageView selectAllButton = (ImageView) convertView.findViewById(R.id.del_unit);
        selectAllButton.setOnClickListener(view -> {
            int size = initItemList.size();
            for (int i = 0; i < size; i++) {
                ListViewItemDTO dto = initItemList.get(i);
                dto.setChecked(true);
            }

            listAdapter.notifyDataSetChanged();
        });

        listView.setTag(dialog3);

        listView.setAdapter(listAdapter);

        AlertDialog alert3 = dialog3.create();



        alert3.show();

        alert3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));
    }


    private List<ListViewItemDTO> getInitViewItemDtoList() {

        ArrayList<String> vehicles = new ArrayList<String>();

        for (Vehicle vehicle1 : mRepository.getVehicles()) {

            vehicles.add(vehicle1.getNm());
        }

        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        int length = vehicles.size();

        for (int i = 0; i < length; i++) {
            String itemText = vehicles.get(i);

            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText);

            ret.add(dto);
        }

        return ret;
    }


    @OnClick(R.id.group_delete)
    void deleteGroup() {

        AlertDialog.Builder dialog4 = new AlertDialog.Builder(PreviewActivity.this);


        LayoutInflater inflater2 = getLayoutInflater();

        View convertView2 = (View) inflater2.inflate(R.layout.alert_dialog_delete_group, null);

        dialog4.setView(convertView2);

        ListView listView2 = (ListView)  convertView2.findViewById(R.id.listViewGroup);

        // Initiate listview data.
        final List<ListViewItemDTO> groupList = this.getGroup();

        // Create a customtwo list view adapter with checkbox control.
        ListViewItemCheckboxBaseAdapter listAdapter2 = new ListViewItemCheckboxBaseAdapter (getBaseContext() , groupList);

       // listAdapter2.notifyDataSetChanged();

       // convertView2.setTag(dialog4);
        listView2.setAdapter(listAdapter2);


        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View convertView2, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) convertView2.findViewById(R.id.checkMark5);

                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked())
                {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                }else
                {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });






        // Click this button to select all listview items with checkbox checked.
//        ImageView selectAllButton = (ImageView) convertView2.findViewById(R.id.del_group);
//        selectAllButton.setOnClickListener(view -> {
//            int size = groupList.size();
//            for (int i = 0; i < size; i++) {
//                ListViewItemDTO dto = groupList.get(i);
//                dto.setChecked(true);
//            }
//
//            listAdapter2.notifyDataSetChanged();
//        });




//       listView2.setTag(dialog4);

        AlertDialog alert4 = dialog4.create();

        alert4.show();

        alert4.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    private List<ListViewItemDTO> getGroup() {



        ArrayList<String> groups = new ArrayList<String>();
        for (Group group1 : mRepository.getCompanies().get(0).getGroups()) {

            groups.add(group1.getName());
        }


// vrakja NULL na itemText lista null vrednost

        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        int length = groups.size();

        for (int i = 0; i < length; i++) {
            String itemText2 = groups.get(i);

            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText2);

            ret.add(dto);
        }

        return ret;

    }


    @OnClick(R.id.picSettings)
    void setingsClick() {

        Intent intent = new Intent(PreviewActivity.this, SettingsActivity.class);
        startActivity(intent);

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

//    @OnClick(R.id.btn_delete)
//    void onDeleteClick() {                                        //  DELETE opcija namestena za vo activiti za GRUPITE
//        mPresenter.onDeleteClick();
//    }
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
