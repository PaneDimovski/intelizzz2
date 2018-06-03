package uk.co.intelitrack.intelizzz.components.crud_groups;

import uk.co.intelitrack.intelizzz.common.base.BasePresenter;
import uk.co.intelitrack.intelizzz.common.base.BaseView;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface CrudGroupContract {

    interface View extends BaseView<Presenter> {

        void startLoginActivity();

        void refreshFields();

        void closeActivity();

        void showToastMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void onSave(String name, String under);
    }
}
