package uk.co.intelitrack.intelizzz.common.data.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}