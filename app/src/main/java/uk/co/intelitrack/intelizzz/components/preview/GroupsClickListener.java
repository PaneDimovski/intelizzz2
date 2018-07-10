package uk.co.intelitrack.intelizzz.components.preview;

import android.view.View;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface GroupsClickListener {
    void onCompanyItemClick(String id);

    void onGroupItemClick(String groupId);

    void onGroupChildItemClick(String vehicleId);

    void onItemClick(View view , int position);
    void onDelete(View view, int position);
}
