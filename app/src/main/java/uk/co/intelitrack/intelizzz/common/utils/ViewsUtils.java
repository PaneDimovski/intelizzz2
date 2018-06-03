package uk.co.intelitrack.intelizzz.common.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class ViewsUtils {

    public static void setViewGroupVisibility(ViewGroup group, int visibility) {
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = group.getChildAt(i);
            if (v instanceof ViewGroup) {
                v.setVisibility(visibility);
                setViewGroupVisibility((ViewGroup) v, visibility);
            } else {
                v.setVisibility(visibility);
            }
        }
    }
}
