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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.intelitrack.Proba.CustomAdapter;
import uk.co.intelitrack.Proba.Item;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.api.RestApi;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Alarm;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.groups.GroupsActivity;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;
import uk.co.intelitrack.intelizzz.components.timerSettings.TimerSettingsActivity;
import uk.co.intelitrack.intelizzz.components.unit.UnitActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class PreviewActivity extends AppCompatActivity implements PreviewContract.View, VehiclesClickListener,
        FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener {

    RestApi api;


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

    private  Item itemHandler;
    private CustomAdapter adapter;
    private ArrayList<Item> itemList;


    //endregion
    int posit;
    private List<Vehicle> mVehicles = new ArrayList<>();
    public Vehicle vehicle;

    //region Fields
    private IntelizzzProgressDialog mProgressDialog;
    private boolean mIsGroup;
    //endregion
    // private ArrayAdapter<Vehicle> arrayAdapter;
    String text = "";
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
    public void cancelTamper() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    //  builder.setMessage("Do you want to remove ?");
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.alert_dialog_reset, null);
        builder.setView(convertView);
//                    builder.setView(R.layout.alert_dialog_reset);
        Button buttonYes = (Button) convertView.findViewById(R.id.yes);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
                RestApi api = new RestApi(getApplicationContext());
                String JSESSIONIN = sharedPreferencesUtils.getSharedPreferencesString(Constants.JSESSIONID);
                String ss = "JSESSIONID=" + JSESSIONIN;

                String condiIdno = "handled";
                String typeIdno = "17";
                String aaa = "590C8609DEDE47598B0338BD41DBD2EB";


                String sourceIdno = getCurrentDateAndTimeFirst();
                String vehiColor = getCurrentDateAndTimeSecond();
                Alarm alarm = new Alarm();
                alarm.setCondiIdno(condiIdno);
                alarm.setGuid(aaa);
                alarm.setTypeIdno(typeIdno);
                alarm.setSourceIdno(sourceIdno);
                alarm.setVehiColor(vehiColor);

                Call<Alarm> call = api.resetTamper(ss, alarm);
                call.enqueue(new Callback<Alarm>() {
                    @Override
                    public void onResponse(Call<Alarm> call, Response<Alarm> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "successfully Tamper reset", Toast.LENGTH_SHORT).show();
                        } else if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "NEUSPESNO TAMPER", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Alarm> call, Throwable t) {

                    }
                });
            }
        });


        builder.setNegativeButton(
                "",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
        alert11.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 200, 200, 200)));
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

    public static String getCurrentDateAndTimeFirst() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentDateAndTimeSecond() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void startMainActivity() {
        MainActivity.start(this, false);
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
        MainActivity.start(this, false);
    }

    @OnClick(R.id.add_unit)
    void onAddClick(View v) {


        AlertDialog.Builder dialog2 = new AlertDialog.Builder(PreviewActivity.this);
        dialog2.setCancelable(true);


        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.alert_dialog_add_unit, null);

        dialog2.setView(convertView);

        EditText init_id = (EditText) convertView.findViewById(R.id.init_id);
        EditText init_name = (EditText) convertView.findViewById(R.id.init_name);
        Button ok = (Button) convertView.findViewById(R.id.okkopce);


        convertView.setTag(dialog2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferencesUtils mSharedpref = new SharedPreferencesUtils(getApplicationContext());
                String jsession = mSharedpref.getSharedPreferencesString(Constants.TOKEN);
                String vehIdno = init_id.getText().toString();
                String devIdno = init_name.getText().toString();
                String devType = "2";
                int factoryType = 0;
                String companyName = "testDesktop";
                String account = "admin";


                api = new RestApi(PreviewActivity.this);
                {
                    Call<Vehicle> call = api.postaddUnit(jsession, vehIdno, devIdno, devType, factoryType, companyName, account);
                    call.enqueue(new Callback<Vehicle>() {
                        @Override
                        public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                            if (response.isSuccessful()) {
                                vehicle = response.body();
                                Toast.makeText(PreviewActivity.this, "Unit successfully added", Toast.LENGTH_SHORT).show();
                            } else if (!response.isSuccessful()) {
                                Toast.makeText(PreviewActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Vehicle> call, Throwable t) {
                            Toast.makeText(PreviewActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        AlertDialog alert2 = dialog2.create();

        alert2.show();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));



    }

    @OnClick(R.id.unit_delete)
    void deleteUint() {

        AlertDialog.Builder dialog3 = new AlertDialog.Builder(PreviewActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.alert_dialog_delete_unit, null);

        dialog3.setView(convertView);

        ListView listView = (ListView) convertView.findViewById(R.id.listView1);

        final List<Item> initItemList = this.getInitViewItemDtoList();

        CustomAdapter listAdapter = new CustomAdapter(getBaseContext(), initItemList);

        convertView.setTag(dialog3);

        listView.setTag(dialog3);

        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener((adapterView, view, itemIndex, l) -> {
          listAdapter.setCheckBox(itemIndex);

        });


        ImageView selectAllButton = (ImageView) convertView.findViewById(R.id.del_unit);
        selectAllButton.setOnClickListener(view -> {

            String y = "";
            for(Item hold: listAdapter.getAllData()){
                if(hold.isCheckbox()){
                    y += " "  + hold.getId();
                }
            }
            Toast.makeText(PreviewActivity.this,"Odbereni ID: " + y + "\n",Toast.LENGTH_LONG).show();

         listAdapter.notifyDataSetChanged();
        });


        AlertDialog alert3 = dialog3.create();


        alert3.show();
        alert3.setCanceledOnTouchOutside(true);
        alert3.setCancelable(true);
        alert3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));
    }


    private List<Item> getInitViewItemDtoList() {

        ArrayList<String> vehicles = new ArrayList<String>();
        ArrayList<String> vehicles2 = new ArrayList<String>();
        for (Vehicle vehicle1 : mRepository.getVehicles()) {

            vehicles.add(vehicle1.getNm());
        }
        for (Vehicle vehicle3 : mRepository.getVehicles()) {

            vehicles2.add(vehicle3.getId());
        }
        List<Item> ret = new ArrayList<Item>();

        int length = vehicles.size();

        for (int i = 0; i < length; i++) {
            String itemText = vehicles.get(i);
            String itemText4 = vehicles2.get(i);

            Item dto = new Item();
            // dto.setChecked(false);
            dto.setId(itemText4);
            dto.setName(itemText);

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

        ListView listView2 = (ListView) convertView2.findViewById(R.id.listViewGroup);

        final List<Item> groupList = this.getGroup();

        CustomAdapter listAdapter2 = new CustomAdapter(getBaseContext(), groupList);

        convertView2.setTag(dialog4);

        listView2.setAdapter(listAdapter2);
        listView2.setTag(dialog4);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter2.setCheckBox(position);
            }
        });

        ImageView delete = (ImageView) convertView2.findViewById(R.id.del_group);
        delete.setOnClickListener((View view) -> {

            String x = "";
            for(Item hold: listAdapter2.getAllData()){
                if(hold.isCheckbox()){
                    x += " "  + hold.getId();
                }
            }
            Toast.makeText(PreviewActivity.this,"Odbereni ID : " + x + "\n",Toast.LENGTH_LONG).show();


           listAdapter2.notifyDataSetChanged();
        });

        AlertDialog alert4 = dialog4.create();

        alert4.show();

        alert4.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    private List<Item> getGroup() {

        ArrayList<String> groups = new ArrayList<String>();

        ArrayList<String> groups2 = new ArrayList<>();

        for ( Group group2 : mRepository.getCompanies().get(posit).getGroups()   ) {
            groups2.add(group2.getId());
        }

        for (Group group1 : mRepository.getCompanies().get(posit).getGroups()) {
            groups.add(group1.getName());
        }

        List<Item> ret = new ArrayList<Item>();
        int length = groups.size();
        for (int i = 0; i < length; i++) {

            String itemText2 = groups.get(i);
            String itemText3 = groups2.get(i);

            Item dto = new Item();

            dto.setCheckbox(false);
            dto.setName(itemText2);
            dto.setId(itemText3);

            ret.add(dto);

        }
        return ret;


    }






    @OnClick(R.id.picSettings)
    void setingsClick() {

        Intent intent = new Intent(PreviewActivity.this, TimerSettingsActivity.class);
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
