package uk.co.intelitrack.intelizz;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public interface GroupsClickListenerSettings {
    void onCompanyItemClick(String id);

    void onGroupItemClick(String groupId);

    void onGroupChildItemClick(String vehicleId);
}
