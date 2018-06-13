package uk.co.intelitrack.intelizzz.components.preview;

import java.util.List;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface PreviewContract {

    interface View extends BaseView<PreviewContract.Presenter> {

        void setVehicles(List<Vehicle> vehicles);

        void setGroupNumber(int number);

        void setGroups(List<ParentVehicle> groups);

        void setGroup(ParentVehicle group);

        void setVehiclesListVisible();

       // void setUnitsList();

        void setGroupsListVisible();

        void startUnitActivity(String id);

        void startLoginActivity();

        void startMainActivity();

        void startGroupsActivity();

        void showToastMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void refreshGroups();

        void onUnitClick(String id);

        void onGroupClick(String id, boolean isGroup);

        void onMoveClick();

        void onDeleteClick();
    }
}
