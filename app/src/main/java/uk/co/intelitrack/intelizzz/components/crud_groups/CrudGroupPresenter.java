package uk.co.intelitrack.intelizzz.components.crud_groups;

import android.content.Intent;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.utils.RxUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class CrudGroupPresenter implements CrudGroupContract.Presenter {

    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private IntelizzzRepository mRepository;
    private CrudGroupContract.View mView;

    public CrudGroupPresenter(IntelizzzRepository repository, CrudGroupContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }

    //region BasePresenter
    @Override
    public void subscribe(Intent intent) {
    }

    @Override
    public void unsubscribe() {
        RxUtils.unsubscribe(mSubscriptions);
    }

    @Override
    public void onSave(String name, String under) {
        mView.toogleProgressBar(true);
        mSubscriptions.add(mRepository.addGroup(name, mRepository.getCompanies().get(0).getId()).
                subscribe(
                        x -> {
                            for (Company company : x) {
                                for (Group group : company.getGroups()) {
                                    if (mRepository.getGroupById(group.getId()) == null) {
                                        mRepository.addLocallyGroup(group);
                                    }
                                }
                            }

                            mView.refreshFields();
                            //TODO: put this in strings
                            mView.showToastMessage("Group successfully added");
                            mView.toogleProgressBar(false);
                        },
                        e -> {
                            Timber.e(e);
                            mView.toogleProgressBar(false);
                        }));
    }
    //endregion
}