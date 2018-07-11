package uk.co.intelitrack.intelizzz.components.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.intelitrack.intelizzz.IntelizzzApplication;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzDataSource;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.api.RestApi;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;
import uk.co.intelitrack.intelizzz.common.utils.DialogUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;
import uk.co.intelitrack.intelizzz.components.main.MainActivity;
import uk.co.intelitrack.intelizzz.userdetails.UserDetailsActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    //region DI
    @Inject
    LoginPresenter mPresenter;
    @Inject
    DialogUtils mDialogUtils;
    //endregion

    //region VI
    @BindView(R.id.edit_username)
    EditText mUsername;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.check_keep_signed)
    CheckBox mKeepSigned;
    @BindView(R.id.slikaWeb)
    ImageView web;
    //endregion

    //region Fields
    public Token token;
    public RestApi api;
    private IntelizzzProgressDialog mProgressDialog;
    SharedPreferencesUtils sharedPreferencesUtils;
    IntelizzzDataSource intelizzzDataSource;
    String token2;

//    IntelizzzDataSource mData;
    //endregion

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLoginComponent.builder()
                .applicationComponent(((IntelizzzApplication) getApplication()).getComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter.subscribe(null);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webStrana();
            }
        });

        mPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    kopceLogIn();
                    return true;
                }
                return false;
            }
        });


    }

    //otvaranje na web
    public void webStrana() {
        Intent i = new Intent();
        i.putExtra(Intent.EXTRA_TEXT, "");
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://intelitrack.co.uk/"));
        startActivity(i);
        Toast.makeText(this, "odi na web", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    //region BaseView Methods
    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void toogleProgressBar(boolean show) {
        hideProgressBar();
//        if (show) {
//            mProgressDialog = DialogUtils.getProgressBarDialog(this);
//            mProgressDialog.show();
//        }
    }
    //endregion

    //region LoginView Methods
    @Override
    public void showAlertDialog(int title, int message) {
        mDialogUtils.showAlertDialog(this, getString(title), getString(message)).show();
    }

    @Override
    public void startVehiclesActivity() {
        MainActivity.start(this,true);
        finish();
    }
    //endregion

    //region ButterKnife Methods
//    @OnClick(R.id.button_login)
//    void onLoginClick() {
//
//        api = new RestApi(LoginActivity.this);
//        String a = mUsername.getText().toString();
//        String b = mPassword.getText().toString();
//        Call<Token> call = api.postAuthentication(a,b);
//        call.enqueue(new Callback<Token>() {
//            @Override
//            public void onResponse(Call<Token> call, Response<Token> response) {
//                if (response.isSuccessful()){
//                    token = new Token();
//                    token = response.body();
////
//                    Toast.makeText(LoginActivity.this, "Uspesno Retrofit", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(LoginActivity.this, "NE PROAGJA", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Token> call, Throwable t) {
//
//            }
//        });
//
//
//
//
//
//
//
//
//
//
//
//
//        mPresenter.login(mUsername.getText().toString().trim(), mPassword.getText().toString().trim(),
//                mKeepSigned.isChecked());
//    }
    //endregion
//    public void kopceLogIn() {
//        String username = mUsername.getText().toString();
//        String password = mPassword.getText().toString();
//        api = new RestApi(getApplicationContext());
//            Call<Token> call = api.login3(username,password);
//            call.enqueue(new Callback<Token>() {
//                @Override
//                public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
//                    if (response.isSuccessful()) {
//                        token = new Token();
//                        token = response.body();
//                        token2 = token.getJsession();
////                        sharedPreferencesUtils.setSharedPreferencesString(Constants.TOKEN,token2);
//////                        sharedPreferencesUtils.setSharedPreferencesString(Constants.TOKEN, token.getJsession());
////                        sharedPreferencesUtils.setSharedPreferencesString(Constants.USERNAME, username);
////                        sharedPreferencesUtils.setSharedPreferencesString(Constants.PASSWORD, password);
//                        logiranje2();
//                    } else {
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Token> call, Throwable t) {
//
//                }
//            });
//        }

//        public void logiranje2(){
//        api = new RestApi(getApplicationContext());
//            String username = mUsername.getText().toString();
//            String password = mPassword.getText().toString();
//            Call<Token> call2 = api.login4(username,password);
//            call2.enqueue(new Callback<Token>() {
//                @Override
//                public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
//                    if (response.isSuccessful()){
//                        token=response.body();
//                        String token3 = token.getJSESSIONID();
////                        sharedPreferencesUtils.setSharedPreferencesString(Constants.JSESSIONID,token3);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Token> call, Throwable t) {
//
//                }
//            });
//        }



//
//        mPresenter.login(mUsername.getText().toString().trim(), mPassword.getText().toString().trim(),
//                mKeepSigned.isChecked());


    //region Helper Methods
    private void hideProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    //endregion
    public void kopceLogIn() {
        mPresenter.login(mUsername.getText().toString().trim(), mPassword.getText().toString().trim(),
                mKeepSigned.isChecked());
    }
    @OnClick(R.id.create_account)
    public void click (View view) {
        Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
        startActivity(intent);
    }
}