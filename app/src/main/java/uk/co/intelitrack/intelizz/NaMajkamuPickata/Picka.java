package uk.co.intelitrack.intelizz.NaMajkamuPickata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;
import uk.co.intelitrack.intelizz.MultiCheckGengre;
import uk.co.intelitrack.intelizz.SettingsGroupsAdapter;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;

public class Picka extends AppCompatActivity {

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

    List<ParentVehicle> vehicles = new ArrayList<>();
    List<MultiCheckGengre> lista;

    IntelizzzRepository mRespository;

    SettingsGroupsAdapter adapter;



    public static void start(Activity activity, boolean isGroup) {
        Intent intent = new Intent(activity, Picka.class);
        intent.putExtra(Constants.IS_GROUP, isGroup);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_select);

        ButterKnife.bind(this);
        if (getIntent().getExtras() != null)
            mIsGroup = getIntent().getExtras().getBoolean(Constants.IS_GROUP);
//        mPresenter.subscribe(getIntent());


    }





}
