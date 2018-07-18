package uk.co.intelitrack.intelizzz.components.unit;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface UnitContract {

    interface View extends BaseView<Presenter> {

        void showData(Vehicle vehicle);

        void startMapActivity(String id, boolean isLastKnownLocation);

        void startLoginActivity();
        void startTimeActivity(String id, boolean isLastKnownLocation);
    }

    interface Presenter extends BasePresenter {
        void onLastKnownLocation();
        void onLastKnownLocation1();

        void onPreviousLocations();
    }
}
