package uk.co.intelitrack.intelizzz.components.preview;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizz.SettingsActivity;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class PreviewModule {
    private final PreviewActivity mActivity;

    private Context mContext;

    public PreviewModule(PreviewActivity activity) {
        this.mActivity = activity;

    }

    @Provides
    PreviewPresenter provideVehiclesPresenter(IntelizzzRepository repository) {
        return new PreviewPresenter(repository, mActivity);
    }

    @Provides
    VehiclesAdapter provideVehiclesAdapter(IntelizzzRepository repository) {
        return new VehiclesAdapter(mActivity, repository, mContext);
    }

    @Provides
    GroupsAdapter provideGroupsAdapter(PreviewPresenter listener, IntelizzzRepository repository) {
        List<ParentVehicle> groups = new ArrayList<>();
        if (!repository.getCompanies().isEmpty()) {
            for (Company company : repository.getCompanies()) {
                groups.add(new ParentVehicle(company.getName(), company.getAllVehicles(), company.getId(), true));
                for (Group group : company.getGroups()) {
                    groups.add(new ParentVehicle(group.getName(), Arrays.asList(group.getVehicles()), group.getId(), false));
                }
            }
        }
        return new GroupsAdapter(groups, repository, listener, false);
    }
}