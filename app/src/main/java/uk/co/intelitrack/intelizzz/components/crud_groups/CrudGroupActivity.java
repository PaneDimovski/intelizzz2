package uk.co.intelitrack.intelizzz.components.crud_groups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class CrudGroupActivity extends AppCompatActivity implements CrudGroupContract.View {

    @Inject
    CrudGroupPresenter mPresenter;

    @BindView(R.id.bottom_bar_type_name)
    TextView mCrudGroupNumber;
    @BindView(R.id.bottom_bar_type_number)
    TextView mCrudGroupName;
    @BindView(R.id.edit_new_name)
    EditText mEtName;
    @BindView(R.id.edit_create_under)
    EditText mEtUnder;
    @BindView(R.id.unit_delete)
    ImageView delete;
    @BindView(R.id.group_delete)
    ImageView delete2;
    @BindView(R.id.add_unit)
    ImageView add_unit;
    //region fields
    private IntelizzzProgressDialog mProgresDialog;
    //endregion

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CrudGroupActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCrudGroupComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .crudGroupPresenterModule(new CrudGroupPresenterModule(this))
                .crudGroupModule(new CrudGroupModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_add_group);

        ButterKnife.bind(this);

        mPresenter.subscribe(getIntent());
        mCrudGroupNumber.setVisibility(View.GONE);
        mCrudGroupName.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        delete2.setVisibility(View.GONE);
        add_unit.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(CrudGroupContract.Presenter presenter) {

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
    public void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

    @Override
    public void refreshFields() {
        mEtName.setText("");
        mEtUnder.setText("");
    }

    @Override
    public void closeActivity() {
        finish();
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


    @OnClick(R.id.btn_ok)
    void onSaveClick() {
        mPresenter.onSave(mEtName.getText().toString().trim(), mEtUnder.getText().toString().trim());
    }
}
