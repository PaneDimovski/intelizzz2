package uk.co.intelitrack.intelizzz.components.main;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizz.SettingsActivity;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.mojaProba.PaginationActivity;
import uk.co.intelitrack.intelizzz.components.preview.PreviewActivity;
import uk.co.intelitrack.intelizzz.components.timerSettings.TimerSettingsActivity;
import uk.co.intelitrack.intelizzz.components.unit.UnitActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {


    public static final String LOAD = "load";




    //region DI
    @Inject
    MainPresenter mPresenter;
    //endregion
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    //region VI
    @BindView(R.id.picVideo)
    ImageView video;
    @BindView(R.id.picHome)
    ImageView home;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_groups_number)
    TextView mGroupsNumber;
    @BindView(R.id.tv_units_number)
    TextView mUnitsNumber;
    @BindView(R.id.picSettings1)
    ImageView setings1;




    @BindView(R.id.floating_search_view)
    IntelizzzFloatingSearchView mIntelizzzFloatingSearchView;
    //endregion

    //region Fields
    SharedPreferencesUtils mSharedPreferencesUtils;
    private IntelizzzProgressDialog mProgressDialog;
    //endregion

//    public static void start(Activity activity) {
//        Intent intent = new Intent(activity, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        activity.startActivity(intent);
//        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//    }

    public static void start(Activity activity, boolean load) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(LOAD, load);
        activity.startActivity(intent);
        if(!load) {
            activity.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .mainPresenterModule(new MainPresenterModule(this))
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SharedPreferencesUtils pref = new SharedPreferencesUtils(getApplicationContext());

        String user = pref.getSharedPreferencesString(Constants.USERNAME);
        txtUserName.setText(user);


//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        mPresenter.subscribe(null);
        mPresenter.subscribe(getIntent());
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playVideo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    //region Base Methods
    @Override
    public void setPresenter(MainContract.Presenter presenter) {

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

    //region MainView Interface
    @Override
    public void setListeners() {
        mIntelizzzFloatingSearchView.setOnSearchListener(mPresenter);
        mIntelizzzFloatingSearchView.setOnQueryChangeListener(mPresenter);
    }

    @Override
    public void showUnitsNumber(int number) {
        mUnitsNumber.setText(String.valueOf(number));
    }

    @Override
    public void showGroupsNumber(int number) {
        mGroupsNumber.setText(String.valueOf(number));
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

    @Override
    public void startUnitActivity(String vehicleId) {
        UnitActivity.start(this, vehicleId);
    }

    @Override
    public IntelizzzFloatingSearchView getSearchView() {
        return mIntelizzzFloatingSearchView;
    }
    //endregion

    //region ButterKnife Methods
    @OnClick(R.id.btn_units)
    void onUnitsClick() {
        PreviewActivity.start(this, false);
    }

    @OnClick(R.id.btn_groups)
    void onGroupsClick() {
        PreviewActivity.start(this, true);
    }
//
    @OnClick(R.id.picHome)
    void onHomeClick() {
        mPresenter.onHomeClick();
    }
    //endregion

    //region Helper Methods
    private void hideProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    public void playVideo() throws IOException {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/videos/star-long-exposure-starry-sky-sky-6962/"));
//        intent.setDataAndType(Uri.parse("https://pixabay.com/en/videos/star-long-exposure-starry-sky-sky-6962/"), "video/*");
//        startActivity(intent);
        String url = "https://pixabay.com/en/videos/star-long-exposure-starry-sky-sky-6962/"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare(); // might take long! (for buffering, etc)
        mediaPlayer.start();
    }

    @OnClick (R.id.picSettings1)
            public void intentClick(View view){

     Intent intent = new Intent(MainActivity.this, TimerSettingsActivity.class);
        startActivity(intent);

}
    //endregion
}