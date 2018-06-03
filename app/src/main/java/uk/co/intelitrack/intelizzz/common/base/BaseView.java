package uk.co.intelitrack.intelizzz.common.base;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void toogleProgressBar(boolean show);
}