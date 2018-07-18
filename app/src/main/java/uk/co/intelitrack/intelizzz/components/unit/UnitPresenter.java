package uk.co.intelitrack.intelizzz.components.unit;

import android.content.Intent;
import android.text.TextUtils;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class UnitPresenter implements UnitContract.Presenter {

    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private IntelizzzRepository mRepository;
    private UnitContract.View mView;
    private Vehicle mVehicle;

    public UnitPresenter(IntelizzzRepository repository, UnitContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }

    //region BasePresenter
    @Override
    public void subscribe(Intent intent) {
        String id = intent.getStringExtra(Constants.ID);
        if (!TextUtils.isEmpty(id)) {
            mVehicle = mRepository.getVehicleById(id);
            mView.showData(mVehicle);
        }
    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }

    @Override
    public void onLastKnownLocation() {
        mView.startMapActivity(mVehicle.getId(), true);
    }

    @Override
    public void onLastKnownLocation1() {
        mView.startTimeActivity(mVehicle.getId(), true);

    }

    @Override
    public void onPreviousLocations() {
        mView.startMapActivity(mVehicle.getId(), false);
    }
    //endregion
}