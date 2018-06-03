package uk.co.intelitrack.intelizzz.components.groups;

import java.util.List;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzFloatingSearchView;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface GroupsContract {

    interface View extends BaseView<GroupsContract.Presenter> {

        void initGroupAdapters();

        void setUnitNumber(String number);

        void startLoginActivity();

        IntelizzzFloatingSearchView getSearchView();

        void showGroupData(List<Vehicle> vehicles);

        void setCompany(String companyId);

        void refreshUnassignedVehicles();

        void showToastMessage(String message);
    }

    interface Presenter extends BasePresenter {

        void onArrowRightClicked(List<Vehicle> vehicles);

        void onArrowLeftClicked(List<Vehicle> unassignedVehicles);
    }
}