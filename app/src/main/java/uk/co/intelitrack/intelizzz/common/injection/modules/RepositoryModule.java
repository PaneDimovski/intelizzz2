package uk.co.intelitrack.intelizzz.common.injection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzDataSource;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    IntelizzzRepository provideIntelizzzRepository(IntelizzzDataSource intelizzzDataSource,
                                                   SharedPreferencesUtils sharedPreferencesUtils) {
        return new IntelizzzRepository(intelizzzDataSource, sharedPreferencesUtils);
    }
}
