package uk.co.intelitrack.intelizzz.components.main;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void setListeners();

        void showUnitsNumber(int number);

        void showGroupsNumber(int number);

        void startLoginActivity();

        void startUnitActivity(String vehicleId);

        IntelizzzFloatingSearchView getSearchView();
    }

    interface Presenter extends BasePresenter {
        void onHomeClick();
    }
}
