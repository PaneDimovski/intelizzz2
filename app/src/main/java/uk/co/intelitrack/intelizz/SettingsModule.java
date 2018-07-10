package uk.co.intelitrack.intelizz;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzDataSource;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.components.preview.VehiclesAdapter;


@Module
public class SettingsModule {
    private final SettingsActivity mActivity;

    private Context mContext;
    private List<Vehicle>vehicle;
    private boolean isCompany;

    public SettingsModule(SettingsActivity activity) {
        this.mActivity = activity;

    }

    @Provides
    SettingsPresenter provideVehiclesPresenter(IntelizzzRepository repository,SharedPreferencesUtils sharedPreferencesUtils) {
        return new SettingsPresenter(repository, mActivity,sharedPreferencesUtils);
    }

    @Provides
    VehiclesAdapter provideVehiclesAdapter(IntelizzzRepository repository) {
        return new VehiclesAdapter(mActivity, repository, mContext);
    }
    @Provides
    ParentVehicle provideParentVehicle(IntelizzzRepository repository){
        return new ParentVehicle("",vehicle,"", isCompany);
    }

//    @Provides
//    IntelizzzRepository provideRespository(IntelizzzRepository repository, IntelizzzDataSource source,SharedPreferencesUtils sharedPreferencesUtils){
//        return new IntelizzzRepository(source,sharedPreferencesUtils);
//    }

//    @Provides
//    UnitAdapter provideUnitAdapter(IntelizzzRepository repository) {
//        return new UnitAdapter(mActivity,repository, mContext);
//    }

    @Provides
    SettingsGroupsAdapter provideGroupsAdapter(SettingsPresenter listener, IntelizzzRepository repository) {
        List<ParentVehicle> groups = new ArrayList<>();
        if (!repository.getCompanies().isEmpty()) {
            for (Company company : repository.getCompanies()) {
                groups.add(new ParentVehicle(company.getName(), company.getAllVehicles(), company.getId(), true));
                for (Group group : company.getGroups()) {
                    groups.add(new ParentVehicle(group.getName(), Arrays.asList(group.getVehicles()), group.getId(), false));
                }
            }
        }
        return new SettingsGroupsAdapter(groups, repository, listener, false,false,false,mContext);
    }
}