package uk.co.intelitrack.intelizzz.components.preview;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface GroupsClickListener {
    void onCompanyItemClick(String id);

    void onGroupItemClick(String groupId);

    void onGroupChildItemClick(String vehicleId);
}
