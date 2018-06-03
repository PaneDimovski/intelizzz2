package uk.co.intelitrack.intelizzz.common.base;

import android.content.Intent;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface BasePresenter {
    void subscribe(Intent intent);

    void unsubscribe();
}
