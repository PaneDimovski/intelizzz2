package uk.co.intelitrack.intelizzz.components.login;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showAlertDialog(int message, int title);

        void startVehiclesActivity();
    }

    interface Presenter extends BasePresenter {

        void login(String username, String password, boolean keepSigned);

    }
}