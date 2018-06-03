package uk.co.intelitrack.intelizzz.components.login;

import android.content.Intent;
import android.text.TextUtils;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class LoginPresenter implements LoginContract.Presenter {

    //region fields
    private final IntelizzzRepository mRepository;
    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private LoginContract.View mView;
    //endregion

    public LoginPresenter(IntelizzzRepository repository, SharedPreferencesUtils sharedPreferencesUtils,
                          LoginContract.View view) {
        this.mRepository = repository;
        this.mSharedPreferencesUtils = sharedPreferencesUtils;
        this.mView = view;
    }

    //region BasePresenter
    @Override
    public void subscribe(Intent intent) {
        if (mSharedPreferencesUtils.getSharedPreferencesBoolean(Constants.KEEP_SINGED)) {
            if (!TextUtils.isEmpty(mSharedPreferencesUtils.getSharedPreferencesString(Constants.TOKEN))) {
                mView.startVehiclesActivity();
            } else {
                login(mSharedPreferencesUtils.getSharedPreferencesString(Constants.USERNAME),
                        mSharedPreferencesUtils.getSharedPreferencesString(Constants.PASSWORD), true);
            }
        }
    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }
    //endregion

    //region LoginContract.Presenter
    @Override
    public void login(String username, String password, boolean keepSigned) {
        mView.toogleProgressBar(true);
        mSharedPreferencesUtils.setSharedPreferencesBoolean(Constants.KEEP_SINGED, keepSigned);
        mSubscriptions.add(mRepository.login(username, password)
                .subscribe(
                        x -> {
                            int result = x.getResult();
                            if (result == 0) {
                                Timber.d("login successful");
                                mView.toogleProgressBar(false);

                                mView.startVehiclesActivity();
                            } else {
                                Timber.d("login successful");
                                int message = 0;
                                int title = R.string.login_alert_title;
                                if (result == 1) {
                                    message = R.string.username_error;
                                } else if (result == 2) {
                                    message = R.string.wrong_password_error;
                                } else if (result == 4) {
                                    message = R.string.user_expired_error;
                                } else {
                                    message = R.string.undefined_error;
                                }

                                mView.toogleProgressBar(false);
                                mView.showAlertDialog(title, message);
                            }
                        },
                        e -> {
                            Timber.e(e);

                            mView.toogleProgressBar(false);
                            mView.showAlertDialog(R.string.login_alert_title, R.string.undefined_error);
                        }));
    }
    //endregion
}