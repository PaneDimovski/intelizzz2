package uk.co.intelitrack.intelizz;

import java.util.HashMap;
import java.util.List;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;


public interface SettingsContract {

    interface View extends BaseView<SettingsContract.Presenter> {

        void showData(Vehicle vehicle);

        void setVehicles(List<Vehicle> vehicles);

        void setGroupNumber(int number);

        void setGroups(List<MultiCheckGengre> groups);

        void setGroup(MultiCheckGengre group);

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
        //endregion
        String getDeviceId();

        void refreshGroups();

        void onUnitClick(String id);

        void onGroupClick(String id, boolean isGroup);

        void onMoveClick();

        void onDeleteClick();

        void onDelete1(String id);

        void onDelete2(String id, String companija);

        void onDelete3(String jsesion, String id, String companija);

        void onDelete4(String jsesion, String id);

        void onDelete4(HashMap<String, String> cookie, String id);

        void onDelete5(HashMap<String, String> cookie, String id);

        void onDelete6(String s, String id);
    }
}
